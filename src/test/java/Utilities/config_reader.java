package Utilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class config_reader {
	
	
	
	public static  String getPropValues(String property) throws IOException {
			
		  Properties prop = new Properties();
			
			File file = new File("config1.properties");
			FileReader fr = new FileReader(file);
	
			prop.load(fr);
			String value = prop.getProperty(property);
			if(value.contains("_"))
				value.replace("_"," ");
		return value;
	}

	
}
