package csv;

import java.io.File;
import java.util.List;

public interface CsvFileInterface {
	
    File getFile();

    List<String[] > getData();

}
