import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Specify the XML file name
        String fileName = "data.xml";

        // Get user input for the desired field
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the field name to extract: ");
        String fieldName = scanner.nextLine();
        scanner.close();

        // Parse the XML file and extract the desired fields
        try {
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));

            // Normalize the XML structure
            document.getDocumentElement().normalize();

            // Get the list of all <record> elements
            NodeList recordList = document.getElementsByTagName("record");

            // Create a JSONArray to store the extracted records
            JSONArray jsonArray = new JSONArray();

            // Iterate over each <record> element
            for (int i = 0; i < recordList.getLength(); i++) {
                Node recordNode = recordList.item(i);

                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element recordElement = (Element) recordNode;

                    // Get the element by tag name
                    NodeList fieldNodes = recordElement.getElementsByTagName(fieldName);

                    // Add the field values to the JSONObject
                    if (fieldNodes.getLength() > 0) {
                        JSONObject jsonObject = new JSONObject();
                        for (int j = 0; j < fieldNodes.getLength(); j++) {
                            jsonObject.put(fieldName + (j + 1), fieldNodes.item(j).getTextContent());
                        }
                        jsonArray.put(jsonObject);
                    }
                }
            }

            // Print the JSONArray in JSON format
            System.out.println(jsonArray.toString(4)); // Indent with 4 spaces for readability
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
