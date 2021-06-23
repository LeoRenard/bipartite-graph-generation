/**
 * @author Arnaud Soulet
 */
package lod;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.log4j.Logger;


public abstract class SparqlQuerier {

	protected int limit = 10000;
	protected int offset = 0;
	protected String query;
	protected Triplestore triplestore;
	protected String timeout = null;
	protected SparqlStatus status = SparqlStatus.WAIT;
	protected QueryExecution qexec = null;
	
	protected static int readTimeout = 10 * 60 * 1000;
	protected static int connectTimeout = 10 * 60 * 1000;

	public abstract void begin();
	public abstract void end();	
	public abstract boolean fact(QuerySolution qs) throws InterruptedException;
	
	private static long lastTime = 0;
	
	public static long DELAY = 15;
	
	
	public enum SparqlStatus {
		WAIT,
		BEGIN,
		SEND,
		RECEIVE,
		END
	};
	
	public SparqlQuerier(Triplestore triplestore, String query) {
		this.query = query;
		this.triplestore = triplestore;
	}
	
	public SparqlQuerier(Triplestore triplestore) {
		this.triplestore = triplestore;
	}
	
	public synchronized static void checkTime() {
		long currentTime = 0;
		if ((currentTime = System.currentTimeMillis()) - lastTime < DELAY) {
			try {
				Thread.sleep(DELAY - (currentTime - lastTime));
			} catch (InterruptedException e) {
				//logger.error(e);
				System.out.println("Error : "+e);
			}
		}
		lastTime = currentTime;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public boolean execute() throws InterruptedException {
		String queryStr = "";
		try {
		status = SparqlStatus.BEGIN;
		begin();
		offset = 0;
		int k = 0;
		do {
			k = 0;
			queryStr = "" 
					+ query
					+ " LIMIT " + limit
 					+ " OFFSET " + (limit * offset);
			status = SparqlStatus.SEND;
			Query query = QueryFactory.create(queryStr);
			checkTime();
			qexec = QueryExecutionFactory.sparqlService(triplestore.getEndpoint(), query, "<http://dbpedia.org>");
			qexec.setTimeout(readTimeout, connectTimeout);
			if (timeout != null)
				((QueryEngineHTTP) qexec).addParam("timeout", timeout);
			if (qexec != null) {
				ResultSet resultSet = qexec.execSelect();
				status = SparqlStatus.RECEIVE;
				if (resultSet != null) {
					while (resultSet.hasNext()) {
						QuerySolution qs = resultSet.nextSolution();
						if (qs != null) {
							if (fact(qs))
								;
							k++;
						}
					}
				}
				else
					return false;
			}
			else
				return false;
			close();
			offset++;
		} while (k >= limit);
		status = SparqlStatus.END;
		end();
		status = SparqlStatus.WAIT;
		} catch (Exception e) {
			close();
			
			System.out.println("WARN : "+e);
			return false;
		}
		return true;
	}
	
	public void close() {
		if (qexec != null && !qexec.isClosed()) {
			qexec.abort();
			qexec.close();
			qexec = null;
		}
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
	public SparqlStatus getStatus() {
		return status;
	}
	
	public void safeExecute(long millis) throws InterruptedException {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
					try {
						execute();
					} catch (InterruptedException e) {
						close();
						//logger.info(e);
						System.out.println("INFO : "+e);
					}
			}
			
		});
		t.start();
		t.join(millis);
		if (getStatus() != SparqlStatus.SEND) {
			t.join();
		}
		else {
			close();
			t.interrupt();
		}
	}
	
}
