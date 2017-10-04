package keywords;

import edu.jsu.mcis.*;
import org.json.simple.parser.*;

public class ConverterKeywords {
    
    public String convertToJson(String csv) {
		
        return Converter.csvToJson(csv);
		
    }
    
    public String convertToCsv(String json) {
		
        return Converter.jsonToCsv(json);
		
    }
    
    public boolean jsonStringsAreEqual(String s, String t) {
		
		JSONParser parser = new JSONParser();
		
		try {
			
			Object sObj = parser.parse(s);
			Object tObj = parser.parse(t);
			return sObj.equals(tObj);
			
		}
		
		catch(ParseException e) {
			return false;
		}
		
	}
	
}