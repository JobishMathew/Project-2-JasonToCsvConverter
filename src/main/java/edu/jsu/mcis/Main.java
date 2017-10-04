package edu.jsu.mcis;

import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        StringBuilder csvContents = new StringBuilder();
		
        try(BufferedReader reader = new BufferedReader(new FileReader("grades.csv"))) {
			
            String line;
			
            while((line = reader.readLine()) != null) {
                csvContents.append(line).append('\n');
            }
			
        }
		
        catch(IOException e) {}
		
        String testCsv = csvContents.toString();
        
        StringBuilder jsonContents = new StringBuilder();
		
        try(BufferedReader reader = new BufferedReader(new FileReader("grades.json"))) {
			
            String line;
			
            while((line = reader.readLine()) != null) {
                jsonContents.append(line).append('\n');
            }
			
        }
		
        catch(IOException e) { e.printStackTrace(); }
		
        String testJson = jsonContents.toString();
        
        //System.out.println("TestCSV" + testCsv);
        
        String json = Converter.csvToJson(testCsv);
        
        System.out.println(json);
		
        //System.out.println(testJson);
        System.out.println("\n----------------\n");
        String csv = Converter.jsonToCsv(testJson);
        System.out.println(csv);
    }
    
}
