package tcpWork;

import java.io.Serializable;

public class ShowCardInfoOperation extends CardOperation implements Serializable {
    private String serNum;

    public ShowCardInfoOperation(String serNum) {
        this.serNum = serNum;
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }
}

