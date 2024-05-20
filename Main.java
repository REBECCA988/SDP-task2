import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
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

            // Normalize the document
            document.getDocumentElement().normalize();

            // Get the root element
            Element root = document.getDocumentElement();

            // Get a list of all elements with the specified tag name
            NodeList nodeList = root.getElementsByTagName(fieldName);

            // Iterate over the elements and print their values
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println(node.getTextContent());
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}


