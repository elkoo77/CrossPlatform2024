//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//import java.io.File;
//import java.util.HashSet;
//import java.util.Set;
//
//public class XMLStructureAnalyzer {
//
//    public static void main(String[] args) {
//        try {
//            File inputFile = new File("Popular_Baby_Names_NY.xml");
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser saxParser = factory.newSAXParser();
//            SAXHandler handler = new SAXHandler();
//            saxParser.parse(inputFile, handler);
//            handler.printTags();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class SAXHandler extends DefaultHandler {
//    private Set<String> tags = new HashSet<>();
//    private StringBuilder data = new StringBuilder();
//
//    @Override
//    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        tags.add(qName);
//        data.setLength(0);
//    }
//
//    @Override
//    public void characters(char[] ch, int start, int length) throws SAXException {
//        data.append(new String(ch, start, length));
//    }
//
//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//        // Виведення частини даних для вивчення структури
//        if (data.length() > 0) {
//            System.out.println(qName + ": " + data.toString().trim());
//        }
//    }
//
//    public void printTags() {
//        System.out.println("Tags in the document:");
//        for (String tag : tags) {
//            System.out.println(tag);
//        }
//    }
//}
//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//import java.io.File;
//
//public class XMLStructureAnalyzer {
//    public static void main(String[] args) {
//        try {
//            File inputFile = new File("Popular_Baby_Names_NY.xml");
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser saxParser = factory.newSAXParser();
//            UserHandler userHandler = new UserHandler();
//            saxParser.parse(inputFile, userHandler);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class UserHandler extends DefaultHandler {
//    boolean bName = false;
//    boolean bGender = false;
//    boolean bCount = false;
//    boolean bRank = false;
//    boolean bEthnicity = false;
//    boolean bBirthYear = false;
//
//    private String name;
//    private String gender;
//    private String count;
//    private String rank;
//    private String ethnicity;
//    private String birthYear;
//
//    @Override
//    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        if (qName.equalsIgnoreCase("row")) {
//            // Reset variables for a new row
//            name = "";
//            gender = "";
//            count = "";
//            rank = "";
//            ethnicity = "";
//            birthYear = "";
//        } else if (qName.equalsIgnoreCase("nm")) {
//            bName = true;
//        } else if (qName.equalsIgnoreCase("gndr")) {
//            bGender = true;
//        } else if (qName.equalsIgnoreCase("cnt")) {
//            bCount = true;
//        } else if (qName.equalsIgnoreCase("rnk")) {
//            bRank = true;
//        } else if (qName.equalsIgnoreCase("ethcty")) {
//            bEthnicity = true;
//        } else if (qName.equalsIgnoreCase("brth_yr")) {
//            bBirthYear = true;
//        }
//    }
//
//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//        if (qName.equalsIgnoreCase("row")) {
//            // Print out the collected data for this row
//            System.out.println("brth_yr: " + birthYear);
//            System.out.println("gndr: " + gender);
//            System.out.println("ethcty: " + ethnicity);
//            System.out.println("nm: " + name);
//            System.out.println("cnt: " + count);
//            System.out.println("rnk: " + rank);
//            System.out.println();
//        }
//    }
//
//    @Override
//    public void characters(char ch[], int start, int length) throws SAXException {
//        String content = new String(ch, start, length).trim();
//        if (bName) {
//            name = content;
//            bName = false;
//        } else if (bGender) {
//            gender = content;
//            bGender = false;
//        } else if (bCount) {
//            count = content;
//            bCount = false;
//        } else if (bRank) {
//            rank = content;
//            bRank = false;
//        } else if (bEthnicity) {
//            ethnicity = content;
//            bEthnicity = false;
//        } else if (bBirthYear) {
//            birthYear = content;
//            bBirthYear = false;
//        }
//    }
//}
//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//import java.io.File;
//
//public class XMLStructureAnalyzer {
//    public static void main(String[] args) {
//        try {
//            File inputFile = new File("Popular_Baby_Names_NY.xml");
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser saxParser = factory.newSAXParser();
//            UserHandler userHandler = new UserHandler();
//            saxParser.parse(inputFile, userHandler);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class UserHandler extends DefaultHandler {
//    boolean bName = false;
//    boolean bGender = false;
//    boolean bCount = false;
//    boolean bRank = false;
//    boolean bEthnicity = false;
//    boolean bBirthYear = false;
//
//    private String name;
//    private String gender;
//    private String count;
//    private String rank;
//    private String ethnicity;
//    private String birthYear;
//
//    private String lastEthnicity = "";
//
//    @Override
//    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        if (qName.equalsIgnoreCase("row")) {
//            // Reset variables for a new row
//            name = "";
//            gender = "";
//            count = "";
//            rank = "";
//            ethnicity = "";
//            birthYear = "";
//        } else if (qName.equalsIgnoreCase("nm")) {
//            bName = true;
//        } else if (qName.equalsIgnoreCase("gndr")) {
//            bGender = true;
//        } else if (qName.equalsIgnoreCase("cnt")) {
//            bCount = true;
//        } else if (qName.equalsIgnoreCase("rnk")) {
//            bRank = true;
//        } else if (qName.equalsIgnoreCase("ethcty")) {
//            bEthnicity = true;
//        } else if (qName.equalsIgnoreCase("brth_yr")) {
//            bBirthYear = true;
//        }
//    }
//
//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//        if (qName.equalsIgnoreCase("row")) {
//            if (!ethnicity.equalsIgnoreCase(lastEthnicity)) {
//                if (!lastEthnicity.isEmpty()) {
//                    System.out.println();
//                }
//                lastEthnicity = ethnicity;
//                System.out.println(ethnicity);
//            }
//            System.out.println();
//            System.out.println("nm: " + name);
//            System.out.println("cnt: " + count);
//            System.out.println("rnk: " + rank);
//            System.out.println();
//            System.out.println("brth_yr: " + birthYear);
//            System.out.println("gndr: " + gender);
//            System.out.println("ethcty: " + ethnicity);
//        }
//    }
//
//    @Override
//    public void characters(char ch[], int start, int length) throws SAXException {
//        String content = new String(ch, start, length).trim();
//        if (bName) {
//            name = content;
//            bName = false;
//        } else if (bGender) {
//            gender = content;
//            bGender = false;
//        } else if (bCount) {
//            count = content;
//            bCount = false;
//        } else if (bRank) {
//            rank = content;
//            bRank = false;
//        } else if (bEthnicity) {
//            ethnicity = content;
//            bEthnicity = false;
//        } else if (bBirthYear) {
//            birthYear = content;
//            bBirthYear = false;
//        }
//    }
//}
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class XMLStructureAnalyzer {
    public static void main(String[] args) {
        try {
            File inputFile = new File("Popular_Baby_Names_NY.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userHandler = new UserHandler();
            saxParser.parse(inputFile, userHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserHandler extends DefaultHandler {
    private boolean bName = false;
    private boolean bGender = false;
    private boolean bCount = false;
    private boolean bRank = false;
    private boolean bEthnicity = false;
    private boolean bBirthYear = false;

    private String name;
    private String gender;
    private String count;
    private String rank;
    private String ethnicity;
    private String birthYear;

    private String lastEthnicity = "";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("row")) {
            // Reset variables for a new row
            name = "";
            gender = "";
            count = "";
            rank = "";
            ethnicity = "";
            birthYear = "";
        } else if (qName.equalsIgnoreCase("nm")) {
            bName = true;
        } else if (qName.equalsIgnoreCase("gndr")) {
            bGender = true;
        } else if (qName.equalsIgnoreCase("cnt")) {
            bCount = true;
        } else if (qName.equalsIgnoreCase("rnk")) {
            bRank = true;
        } else if (qName.equalsIgnoreCase("ethcty")) {
            bEthnicity = true;
        } else if (qName.equalsIgnoreCase("brth_yr")) {
            bBirthYear = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("row")) {
            if (!ethnicity.equalsIgnoreCase(lastEthnicity)) {
                if (!lastEthnicity.isEmpty()) {
                    System.out.println();
                }
                lastEthnicity = ethnicity;
                System.out.println(ethnicity);
            }
            System.out.println();
            System.out.println("nm: " + name);
            System.out.println("cnt: " + count);
            System.out.println("rnk: " + rank);
            System.out.println();
            System.out.println("brth_yr: " + birthYear);
            System.out.println("gndr: " + gender);
            System.out.println("ethcty: " + ethnicity);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String content = new String(ch, start, length).trim();
        if (bName) {
            name = content;
            bName = false;
        } else if (bGender) {
            gender = content;
            bGender = false;
        } else if (bCount) {
            count = content;
            bCount = false;
        } else if (bRank) {
            rank = content;
            bRank = false;
        } else if (bEthnicity) {
            ethnicity = content;
            bEthnicity = false;
        } else if (bBirthYear) {
            birthYear = content;
            bBirthYear = false;
        }
    }
}
