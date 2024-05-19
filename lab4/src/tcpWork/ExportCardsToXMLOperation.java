package tcpWork;

public class ExportCardsToXMLOperation extends CardOperation {
    private String filePath;

    public ExportCardsToXMLOperation(String filePath) {
        this.filePath = filePath;
    }

    public ExportCardsToXMLOperation() {
        this.filePath = "cards.xml";
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
