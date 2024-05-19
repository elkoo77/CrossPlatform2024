//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import java.util.HashSet;
//import java.util.Set;
//
//class MyHandler extends DefaultHandler {
//    private Set<String> tags = new HashSet<>();
//
//    @Override
//    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        tags.add(qName);
//    }
//
//    public void printTags() {
//        System.out.println("Tags in the XML document:");
//        for (String tag : tags) {
//            System.out.println(tag);
//        }
//    }
//
//
//
//    private Set<String> nationalGroups = new HashSet<>();
//    private boolean bEthnicity = false;
//
//    public Set<String> getNationalGroups() {
//        return nationalGroups;
//    }
//
////    @Override
////    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
////        if (qName.equalsIgnoreCase("ethnicity")) {
////            bEthnicity = true;
////        }
////    }
//
//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//        if (qName.equalsIgnoreCase("ethnicity")) {
//            bEthnicity = false;
//        }
//    }
//
//    @Override
//    public void characters(char[] ch, int start, int length) throws SAXException {
//        if (bEthnicity) {
//            String ethnicity = new String(ch, start, length).trim();
//            nationalGroups.add(ethnicity);
//        }
//    }
//
//    // Цей код також буде доданий у клас MyHandler
//
//    public void processNames(int topN, String targetEthnicity) {
//        // Логіка для вибору найпопулярніших імен та створення нового XML файлу
//    }
//
//}
