package analyze;

import java.util.Arrays;
import org.apache.log4j.Logger;

/**
 * PowerLawAnalyzer estimates the power law from a sample using maximum likelihood. More precisely, it implements the method for discrete data proposed by Clauset et al., 2009:
 * Clauset, Aaron, Cosma Rohilla Shalizi, and Mark EJ Newman. "Power-law distributions in empirical data." SIAM review 51.4 (2009): 661-703.
 * 
 * How it works?
 * PowerLawAnalyzer pla = new PowerLawAnalyzer(sample); // create a new PowerLawAnalyer with a double [] array
 * pla.analyze(); // analyze
 * logger.info(pla.getAlpha() + " " + pla.getMinX()); // gets the parameters of the first power law compliant with the sample 
 * 
 * @author soulet
 *
 */
public class PowerLawAnalyzer {
	
	private static Logger logger = Logger.getLogger(PowerLawAnalyzer.class);
	private double alpha = 0;
	private double minX = 0;
	private int rankedNb;
	private double difference;
	protected double [] distribution;
	private double nextMinX = 0;

	/**
	 *
	 * @param sample
	 */
	public PowerLawAnalyzer(double [] sample) {
		this.distribution = sample;
	}
	
	/**
	 * Analyze the sample for finding the best power law (alpha, minX)
	 * @return true if there exists at least one compliant power law
	 */
	public boolean analyze() {
		Arrays.sort(distribution);
		for (int i = 0; i < distribution.length / 2; i++) {
			double tmp = distribution[i];
			distribution[i] = distribution[distribution.length - i -1];
			distribution[distribution.length - i -1] = tmp;
		}
			
		minX = 0;
		do {
			estimatePowerLaw();
			if (rankedNb >= 2) {
				estimateKS();
				logger.trace(minX + " " + alpha + " " + (difference - getCriticalValue()) + " " +  rankedNb);
				if (difference <= getCriticalValue())
					return true;
			}
			//minX = Math.max(minX + 1, nextMinX);
			minX = minX + 1;
		} while (rankedNb >= 2);
		return false;
	}

	/**
	 * Compute the critical value for a sample containing rankedNb values. The critical value is the maximum difference for compliance.
	 * @return thr critical value
	 */
	public double getCriticalValue() {
		//return -0.5 * Math.log(0.05) / Math.sqrt(rankedNb);
		return 1.36 / Math.sqrt(rankedNb);
	}

	/**
	 * Estimate the exponent alpha and the number of values rankedNb
	 */
	private void estimatePowerLaw() {
		double s = 0;
		int k = 0;
		rankedNb = 0;
		for (int i = 0; i < distribution.length; i++) {
			double number = distribution[i];
			if (number >= minX) {
				if (number > minX)
					nextMinX = number;
				s += Math.log(number / (minX - 0.5));
				k++;
				rankedNb++;
			}
		}
		alpha = (1 + k / s);
	}
	
	/**
	 * Approximate the dzeta function
	 * @param x the point
	 * @param alpha the exponent
	 * @param n the number of iterations for the approximation
	 * @return the value
	 */
	public double dzeta(double x, double alpha, int n) {
		double s = 0;
		for (int i = 0; i < n; i++)
			s += Math.pow(i + x, -alpha);
		return s;
	}

	/**
	 * Compute the Kolmogorov–Smirnov estimation (difference and critical value)
	 */
	private void estimateKS() {
		double D = dzeta(minX, alpha, 10000);
		double critical = getCriticalValue();
		difference = 0;
		if (rankedNb < 2) {
			difference = Float.MAX_VALUE;
		}
		else {
		int i = 0;
			while (i < distribution.length && distribution[i] >= minX) {
				double score = distribution[i];
				while (i < distribution.length && distribution[i] == score)
					i++;
				if (score >= minX) {
					double the = dzeta(((double) score), alpha, 10000) / D;
					double obs = ((double) i) / rankedNb;
					difference = Double.max(difference, Math.abs(the - obs));
					if (difference > critical) {
						//return ; // optimization but prevent the exact calculation of the difference
					}
				}
			}
		}
	}

	/**
	 * Get the exponent alpha
	 * @return alpha
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Get the minimum threshold for the sample 
	 * @return minX
	 */
	public double getMinX() {
		return minX;
	}

	/**
	 * Get the number of values in the sample (above minX)
	 * @return rankedNb
	 */
	public int getRankedNb() {
		return rankedNb;
	}

	/**
	 * Get the KS difference (d-statistics)
	 * @return the difference
	 */
	public double getDifference() {
		return difference;
	}

	/**
	 * Check the compliance of the sample with the power law (alpha, minX)
	 * @return true if the distribution is compliant
	 */
	public boolean isCompliant() {
		return difference < getCriticalValue();
	}
	
	/**
	 * Get the sample
	 * @return the sample
	 */
	public double[] getSample() {
		return distribution;
	}

}