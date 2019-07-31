package com.bazaarvoice;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//implement this interface. you could choose to store the documents in memory or in a file.

public class DocumentSearchImpl implements DocumentSearch {
    private Schema schema;
    private List<Document> documents = new ArrayList<Document>();


    @Override
    public void setSchema(final Schema schema) {
        this.schema = schema;
    }

    @Override
    public void storeDocument(final Document document) {
        documents.add(document);
    }

    //fulltext - search by any word in the string, eg "what a beautiful day" is the input, search by word "what" or "day" should return this document.
//lowercase - search by lowercase version of the string . eg if there is a value "RECORD", searching for "record" should match this document.
    @Override
    public List<Document> search(final String field, final Object value) throws NullPointerException {
        List<Document> matchedDocuments = new ArrayList<Document>();

        Map<String, Object> fieldValues;

        for (Document document : documents) {
            try {
                fieldValues = document.getFields();
                for (String keyName : fieldValues.keySet()) {
                    String keyValue = fieldValues.get(keyName).toString();

                    if (keyName == field) {
                        if (keyValue.equals(null)) {
                            switch (field) {
                                case "description":
                                    String[] wordsdescription = keyValue.split("\\s+");
                                    for (String word : wordsdescription) {
                                        if (word.equals(value.toString())) {
                                            matchedDocuments.add(document);
                                            System.out.println("match description");
                                            break;
                                        }
                                    }
                                    break;
                                case "text":
                                    String[] wordsText = keyValue.split("\\s+");
                                    for (String word : wordsText) {
                                        if (word.toLowerCase().equals(value.toString().toLowerCase())) {
                                            if (word.equals(value.toString())) {
                                                System.out.println("match text");
                                                matchedDocuments.add(document);
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                case "name":
                                    if (keyValue.toLowerCase().equals(value.toString().toLowerCase())) {
                                        System.out.println("match name");
                                        matchedDocuments.add(document);
                                    }
                                    break;
                                case "word":
                                    if (keyValue.equals(value.toString())) {
                                        System.out.println("match word");
                                        matchedDocuments.add(document);
                                    }
                                    break;
                                case "id":
                                    if (tryParseInt(value.toString())) {
                                        if (Integer.parseInt(keyValue.toString()) == Integer.parseInt(value.toString())) {
                                            System.out.println("match id");
                                            matchedDocuments.add(document);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
                System.out.println("The value " + field + " you are looking for is blank in file " + document);
            } catch (NullPointerException e) {
                System.out.println("null value");
            }
        }
        return matchedDocuments;
    }

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
