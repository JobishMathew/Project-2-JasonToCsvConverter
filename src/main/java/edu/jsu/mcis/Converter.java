package edu.jsu.mcis;

import au.com.bytecode.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.util.*;

public class Converter {

    /*
        Consider a CSV file like the following:
        
        ID,Total,Assignment 1,Assignment 2,Exam 1
        111278,611,146,128,337
        111352,867,227,228,412
        111373,461,96,90,275
        111305,835,220,217,398
        111399,898,226,229,443
        111160,454,77,125,252
        111276,579,130,111,338
        111241,973,236,237,500
        
        The corresponding JSON file would be as follows (note the curly braces):
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }  
    */

    @SuppressWarnings("unchecked")
    //method for converting csv data to json format
   public static String csvToJson(String csvString) {
        
        /* Create StringBuilder for JSON data */
        
        StringBuilder json = new StringBuilder();
        
        try{
            
            /* Create CSVReader and iterator */
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> csv = reader.readAll();
            Iterator<String[]> iterator = csv.iterator();
            
            JSONArray rHeaders = new JSONArray();
            JSONArray cHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            String[] rows;

            /* Get first String[] array from CSV data (the column headers); add elements to "cHeaders" */
            
            cHeaders.addAll(Arrays.asList(iterator.next()));

            /* Iterate through remaining CSV rows */

            while (iterator.hasNext()){
                
                /* Create container for next row */
                
                JSONArray row = new JSONArray();
                
                /* Get next String[] array from CSV data */
                
                rows = iterator.next();
                
                /* Get first element (the row header); add element to "rHeaders" */
                
                rHeaders.add(rows[0]);
                
                /* Add remaining elements to "data" */
                
                for (int i = 1; i < rows.length; i++){
                    row.add(rows[i]);
                }
                
                /* Add row to "data" */
                
                data.add(row);

            }

            /* Construct JSON string (remember, this must be an *exact* match for the example!) */
            
            /* Add column and row headers */

            json.append("{\n    \"colHeaders\":").append(cHeaders.toString());
            json.append(",\n    \"rowHeaders\":").append(rHeaders.toString()).append(",\n");
            
            /* Split "data" rows */
            
            rows = data.toString().split("],");
            
            /* Add data */

            json.append("    \"data\":");

            for (int i = 0; i < rows.length; ++i){
                
                String s = rows[i];         /* Get next data row */

                s = s.replace("\"","");     /* Delete double-quotes */
                s = s.replace("]]","]");    /* Fix terminating square brackets */
                
                json.append(s);             /* Append row */
                
                 /* If this is not the last data row, close the row and begin a new one */
                
                if ((i % rows.length) != (rows.length - 1))
                    json.append("],\n            ");
                
            }
            
            /* Close JSON string */
            
            json.append("\n    ]\n}");
            
        }
        
        catch(IOException e) {
            System.err.println(e.toString());
        }
        
        /* Return JSON String */
        
        return json.toString();
        
    }
        
    @SuppressWarnings("unchecked")
    //method for converting json data to csv format
    public static String jsonToCsv(String jsonString) {
        //initializes a new JSONObject
        JSONObject jsonData = null;  

        //creates parser and parses the string passed through
        try { 
            JSONParser parser = new JSONParser();  
            jsonData = (JSONObject)parser.parse(jsonString);  
        }
            catch(Exception e) {}

        //initializes arrays of JSONObject data pertaining to their type (column, row, data)
        JSONArray colHeaders = (JSONArray) jsonData.get("colHeaders");  
        JSONArray rowHeaders = (JSONArray)jsonData.get("rowHeaders");
        JSONArray data = (JSONArray)jsonData.get("data");
        
        //creates the string to be converted and adds the column headers to the top line in csv format
        String csvResult = Converter.<String>joinArray((JSONArray) colHeaders) + "\n";  

        //adds the row headers and their corresponding line of data to the converted string in csv format
        for(int i = 0; i < rowHeaders.size(); i++) { 
            csvResult  = (csvResult + "\"" + (String)rowHeaders.get(i) + "\"," + Converter.<Long>joinArray((JSONArray) data.get(i)) + "\n");  
        }

        return csvResult;

    }
    
    @SuppressWarnings("unchecked")
    //method for joining JSONObject data from an array into a string in csv format
    private static <T> String joinArray(JSONArray array) {
        String line = "";
        for(int i = 0; i < array.size(); i++) {
            line = (line + "\"" + ((T) array.get(i)) + "\"");
            if(i < array.size() - 1) {
                line = line + ",";            
            }
        }
        return line;
    }
    
}
