package server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import client.ConferenceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import rmi.Participant;

public class ConferenceServer implements ConferenceManager {
    private List<Participant> participants = new ArrayList<>();
    private String xmlFilePath = "participants.xml"; // Шлях до XML файлу

    public ConferenceServer() {
        loadDataFromXML(xmlFilePath); // Завантажуємо дані при старті
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveDataToXML(xmlFilePath); // Зберігаємо дані при завершенні роботи сервера
        }));
    }

    public static void main(String[] args) {
        try {
            ConferenceServer server = new ConferenceServer();
            ConferenceManager stub = (ConferenceManager) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(1077);
            registry.rebind("ConferenceManager", stub);
            System.out.println("Conference server is ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void registerParticipant(Participant participant) {
        participants.add(participant);
    }

    @Override
    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    @Override
    public void saveDataToXML(String filename) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("Conference");
            doc.appendChild(rootElement);

            for (Participant participant : participants) {
                Element participantElement = doc.createElement("Participant");
                participantElement.setAttribute("Name", participant.getName());
                participantElement.setAttribute("Family", participant.getFamily());
                participantElement.setAttribute("Organization", participant.getOrganization());
                participantElement.setAttribute("Report", participant.getReport());
                participantElement.setAttribute("Email", participant.getEmail());
                rootElement.appendChild(participantElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
            System.out.println("Data successfully saved to " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadDataFromXML(String filename) {
        try {
            File xmlFile = new File(filename);
            if (!xmlFile.exists()) {
                return; // Якщо файл не існує, повертаємося без завантаження
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Participant");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Element element = (Element) nList.item(temp);
                String name = element.getAttribute("Name");
                String family = element.getAttribute("Family");
                String organization = element.getAttribute("Organization");
                String report = element.getAttribute("Report");
                String email = element.getAttribute("Email");
                Participant participant = new Participant(name, family, organization, report, email);
                participants.add(participant);
            }
            System.out.println("Data successfully loaded from " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
