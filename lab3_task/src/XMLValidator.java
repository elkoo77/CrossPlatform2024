//import javax.xml.XMLConstants;
//import javax.xml.transform.stream.StreamSource;
//import javax.xml.validation.Schema;
//import javax.xml.validation.SchemaFactory;
//import javax.xml.validation.Validator;
//import java.io.File;
//
//public class XMLValidator {
//
//    public static void main(String[] args) {
//        try {
//            File schemaFile = new File("Popular_Baby_Names_NY.xsd");
//            File xmlFile = new File("Popular_Baby_Names_NY.xml");
//
//            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema schema = schemaFactory.newSchema(schemaFile);
//            Validator validator = schema.newValidator();
//            validator.validate(new StreamSource(xmlFile));
//
//            System.out.println("XML is valid.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("XML is not valid.");
//        }
//    }
//}
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XMLValidator {

    public static void main(String[] args) {
        try {
            File schemaFile = new File("Popular_Baby_Names_NY.xsd");
            File xmlFile = new File("Popular_Baby_Names_NY.xml");

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));

            System.out.println("XML is valid.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("XML is not valid.");
        }
    }
}
