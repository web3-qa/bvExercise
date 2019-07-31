package com.bazaarvoice;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
       /*read schema.txt file, done
     construct a few documents based on the schema,

     verify implementation works correctly.
     Implementation should be as efficient as possible.

    For verification, call storeDocument() with different json documents create in
    memory on the fly and check if calling search() returns right documents.

    //create json manually
    //use storeDocument to read in the json object and store in memory into doc object
    */

    private static String happyPath = "src/main/java/json/happyPath.json";
    private static String textLowercase = "src/main/java/json/doc2.json";
    private static String doc3 = "src/main/java/json/doc3.json";
    private static String doc4 = "src/main/java/json/doc4.json";


    public static void main(String[] args) throws IOException {
        DocumentSearchImpl documentSearch = new DocumentSearchImpl();
        documentSearch.setSchema(readSchema());
        //Need documentSearch.storeDocument() to be read in as this code is duplicated
        documentSearch.storeDocument(readInDocument(happyPath));
//        documentSearch.storeDocument(readInDocument(textLowercase));
//        documentSearch.storeDocument(readInDocument(doc3));
//        documentSearch.storeDocument(readInDocument(doc4));

        documentSearch.search("description","another");
//        documentSearch.search("text","text");
//        documentSearch.search("name", " ");
//        documentSearch.search("word","sample");
//        documentSearch.search("name","");
//        documentSearch.search("id",102);

    }

    //read in file and create schema
    private static Schema readSchema() throws IOException {
        Schema schema = new Schema();
        String filepath = "src/main/java/com/bazaarvoice/schema.txt";
        String field = null;
        List<SchemaRow> schemaRows = new ArrayList<>();
        try {
            File file = new File(filepath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            while ((field = bufferedReader.readLine()) != null) {
                SchemaRow row = new SchemaRow();
                String[] txtFileRow = field.split("\\s+");
                row.setFieldName(txtFileRow[0]);
                row.setFieldType(txtFileRow[1]);
                if (txtFileRow.length > 2) {
                    row.setOptions(Arrays.asList(txtFileRow[2].split(",")));
                }
                schemaRows.add(row);
            }
        } catch (IOException e) {
            System.out.print("No file found");
        }
        schema.setRows(schemaRows);
        return schema;
    }

    private static Document readInDocument(String filepath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> mapJson = new HashMap<String, Object>();
        String identifier = "id";

        mapJson = objectMapper.readValue(new File(filepath), mapJson.getClass());
        if (mapJson.containsKey(identifier)) {
            Document doc = new Document();
            doc.setFields(mapJson);
            return doc;
        } else {
            System.out.println("This " + filepath + " requires an identifier");
        }
        return null;
    }
}


