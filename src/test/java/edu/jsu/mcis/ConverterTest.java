package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConverterTest {
    
    private String csvString;
    private String jsonString;

    @Before
    public void setUp() {
        
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        
        StringBuffer csv = new StringBuffer();
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("grades.csv")))) {
            
            String line;
            while((line = reader.readLine()) != null) {
                csv.append(line + '\n');
            }
            
        }
        
        catch(IOException e) { e.printStackTrace(); }
        
        csvString = csv.toString();
        
        StringBuffer json = new StringBuffer();
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("grades.json")))) {
            
            String line;
            while((line = reader.readLine()) != null) {
                json.append(line + '\n');
            }
            
        }
        
        catch(IOException e) { e.printStackTrace(); }
        
        json.deleteCharAt(json.length()-1);
        
        jsonString = json.toString();
        
    }
    
    @Test
    public void testConvertCSVtoJSON() {
        assertEquals(jsonString, Converter.csvToJson(csvString));
    }

    @Test
    public void testConvertJSONtoCSV() {
        assertEquals(csvString, Converter.jsonToCsv(jsonString));
    }
	
    @Test
    public void testConvertJSONtoCSVtoJSON(){
        String csv = Converter.jsonToCsv(jsonString);
        String json = Converter.csvToJson(csv);
        assertEquals(jsonString, json);
    }
	
    @Test
    public void testConvertCSVtoJSONtoCSV(){
        String json = Converter.csvToJson(csvString);
        String csv = Converter.jsonToCsv(json);
        assertEquals(csvString, csv);
    }
    
}







