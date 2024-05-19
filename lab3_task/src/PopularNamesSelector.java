import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class PopularNamesSelector {

    public static void main(String[] args) {
        try {
            File inputFile = new File("Popular_Baby_Names_NY.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);

            NodeList nodeList = doc.getElementsByTagName("row");
            PopularNamesHandler handler = new PopularNamesHandler();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName("nm").item(0).getTextContent();
                    String gender = element.getElementsByTagName("gndr").item(0).getTextContent();
                    int count = Integer.parseInt(element.getElementsByTagName("cnt").item(0).getTextContent());
                    int rank = Integer.parseInt(element.getElementsByTagName("rnk").item(0).getTextContent());
                    handler.processName(name, gender, count, rank);
                }
            }

            List<PopularName> sortedNames = new ArrayList<>(handler.getSelectedNames().values());
            sortedNames.sort(Comparator.comparingInt(PopularName::getRank));

            Document resultDoc = builder.newDocument();
            Element rootElement = resultDoc.createElement("selected_names");
            resultDoc.appendChild(rootElement);

            // Виводимо тільки перші 20 найпопулярніших імен
            int count = 0;
            for (PopularName name : sortedNames) {
                if (count >= 30) {
                    break;
                }

                Element nameElement = resultDoc.createElement("name");

                Element nm = resultDoc.createElement("nm");
                nm.appendChild(resultDoc.createTextNode(name.getName()));
                nameElement.appendChild(nm);

                Element gender = resultDoc.createElement("gndr");
                gender.appendChild(resultDoc.createTextNode(name.getGender()));
                nameElement.appendChild(gender);

                Element countElement = resultDoc.createElement("cnt");
                countElement.appendChild(resultDoc.createTextNode(String.valueOf(name.getCount())));
                nameElement.appendChild(countElement);

                Element rankElement = resultDoc.createElement("rnk");
                rankElement.appendChild(resultDoc.createTextNode(String.valueOf(name.getRank())));
                nameElement.appendChild(rankElement);

                rootElement.appendChild(nameElement);
                count++;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(resultDoc);
            StreamResult result = new StreamResult(new File("Selected_Baby_Names.xml"));
            transformer.transform(source, result);

            System.out.println("Selected names saved to Selected_Baby_Names.xml");

            // Виведення імен у консоль
            System.out.println("Root element: " + resultDoc.getDocumentElement().getNodeName());
            NodeList selectedNames = resultDoc.getElementsByTagName("name");
            for (int i = 0; i < selectedNames.getLength(); i++) {
                Element nameElement = (Element) selectedNames.item(i);
                System.out.println("Name: " + nameElement.getElementsByTagName("nm").item(0).getTextContent());
                System.out.println("Gender: " + nameElement.getElementsByTagName("gndr").item(0).getTextContent());
                System.out.println("Count: " + nameElement.getElementsByTagName("cnt").item(0).getTextContent());
                System.out.println("Rank: " + nameElement.getElementsByTagName("rnk").item(0).getTextContent());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class PopularNamesHandler {
    private Map<String, PopularName> selectedNames = new HashMap<>();

    public Map<String, PopularName> getSelectedNames() {
        return selectedNames;
    }

    public void processName(String name, String gender, int count, int rank) {
        String key = name + "_" + gender;
        PopularName existingName = selectedNames.get(key);
        if (existingName == null || rank < existingName.getRank()) {
            selectedNames.put(key, new PopularName(name, gender, count, rank));
        }
    }
}

class PopularName {
    private String name;
    private String gender;
    private int count;
    private int rank;

    public PopularName(String name, String gender, int count, int rank) {
        this.name = name;
        this.gender = gender;
        this.count = count;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getCount() {
        return count;
    }

    public int getRank() {
        return rank;
    }
}
