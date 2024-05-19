//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.File;
//
//public class ReadSelectedNames {
//
//    public static void main(String[] args) {
//        try {
//            File inputFile = new File("selected_names.xml");
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(inputFile);
//            doc.getDocumentElement().normalize();
//
//            NodeList nameList = doc.getElementsByTagName("name");
//
//            System.out.println("Selected names:");
//            for (int i = 0; i < nameList.getLength(); i++) {
//                Element nameElement = (Element) nameList.item(i);
//                String name = nameElement.getTextContent();
//                System.out.println(name);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
