import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DataTableBean extends JTable {
    private DefaultTableModel tableModel;

    public DataTableBean() {
        tableModel = new DefaultTableModel();
        setModel(tableModel);
    }

    public void setData(List<Object[]> data, Object[] columnNames) {
        clear();
        for (Object[] rowData : data) {
            tableModel.addRow(rowData);
        }
        tableModel.setColumnIdentifiers(columnNames);
    }

    public List<Object[]> getData() {
        int rowCount = tableModel.getRowCount();
        int colCount = tableModel.getColumnCount();
        Object[][] data = new Object[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i][j] = tableModel.getValueAt(i, j);
            }
        }
        //return List.of(data);
        return Arrays.asList(data);
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void removeRow(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

    public void clear() {
        tableModel.setRowCount(0);
    }

    public void saveToXML(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);

            DefaultTableModel model = (DefaultTableModel) getModel();
            int rowCount = model.getRowCount();
            int columnCount = model.getColumnCount();

            for (int i = 0; i < rowCount; i++) {
                Element rowElement = doc.createElement("row");
                rootElement.appendChild(rowElement);

                for (int j = 0; j < columnCount; j++) {
                    String columnName = model.getColumnName(j);
                    Object cellValue = model.getValueAt(i, j);

                    Element cellElement = doc.createElement(columnName);
                    cellElement.appendChild(doc.createTextNode(cellValue.toString()));
                    rowElement.appendChild(cellElement);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            JOptionPane.showMessageDialog(this, "Дані успішно збережено");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Помилка збереження даних: " + e.getMessage());
        }
    }

    public void loadFromXML(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList rowList = doc.getElementsByTagName("row");
            DefaultTableModel model = (DefaultTableModel) getModel();

            for (int i = 0; i < rowList.getLength(); i++) {
                Node rowNode = rowList.item(i);
                if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element rowElement = (Element) rowNode;
                    Object[] rowData = new Object[model.getColumnCount()];

                    for (int j = 0; j < model.getColumnCount(); j++) {
                        String columnName = model.getColumnName(j);
                        NodeList cellList = rowElement.getElementsByTagName(columnName);
                        if (cellList.getLength() > 0) {
                            Element cellElement = (Element) cellList.item(0);
                            rowData[j] = cellElement.getTextContent();
                        }
                    }

                    model.addRow(rowData);
                }
            }

            JOptionPane.showMessageDialog(this, "Дані успішно відкрито");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Помилка відкриття даних: " + e.getMessage());
        }
    }



    // Додаймо метод для додавання колонки з датою
    public void addDateColumn() {
        tableModel.addColumn("Date");
    }
}
