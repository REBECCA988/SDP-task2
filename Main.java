import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Specify the XML file name
        String fileName = "data.xml";

        // Get user input for the desired field
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the field name to extract: ");
        String fieldName = scanner.nextLine().trim();

        // Ensure the field name is not empty
        if (fieldName.isEmpty()) {
            System.out.println("Field name cannot be empty. Please provide a valid field name.");
            scanner.close();
            return;
        }

        scanner.close();

        // Parse the XML file and extract the desired fields
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserFieldHandler handler = new UserFieldHandler(fieldName);

            saxParser.parse(new File(fileName), handler);

            // Check if any records were found
            if (handler.getJsonArray().length() > 0) {
                // Print the JSONArray in JSON format
                System.out.println(handler.getJsonArray().toString(4)); // Indent with 4 spaces for readability
            } else {
                System.out.println("No records found for the field: " + fieldName);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the file: " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("An error occurred while parsing the XML file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class UserFieldHandler extends DefaultHandler {
    private String fieldName;
    private JSONArray jsonArray;
    private JSONObject currentRecord;
    private StringBuilder currentValue;
    private boolean isTargetField;
    private int fieldCount;

    public UserFieldHandler(String fieldName) {
        this.fieldName = fieldName;
        this.jsonArray = new JSONArray();
        this.currentValue = new StringBuilder();
        this.isTargetField = false;
        this.fieldCount = 0;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentValue.setLength(0);
        if (qName.equals("record")) {
            currentRecord = new JSONObject();
            fieldCount = 0;
        } else if (qName.equals(fieldName)) {
            isTargetField = true;
            fieldCount++;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isTargetField) {
            currentValue.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(fieldName) && isTargetField) {
            currentRecord.put(fieldName + fieldCount, currentValue.toString().trim());
            isTargetField = false;
        } else if (qName.equals("record")) {
            if (currentRecord.length() > 0) {
                jsonArray.put(currentRecord);
            }
        }
    }
}
