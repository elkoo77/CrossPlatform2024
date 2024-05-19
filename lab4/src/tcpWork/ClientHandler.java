package tcpWork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private boolean work = true;
    private MetroCardBank bnk = null;
    private Socket s = null;

    public ClientHandler(MetroCardBank bnk, Socket s) {
        this.bnk = bnk;
        this.s = s;
        this.work = true;
        try {
            this.is = new ObjectInputStream(s.getInputStream());
            this.os = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void run() {
        synchronized (bnk) {
            System.out.println("Client Handler Started for: " + s);
            while (work) {
                try {
                    Object obj = is.readObject();
                    processOperation(obj);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error: " + e);
                    break;
                }
            }
            try {
                System.out.println("Client Handler Stopped for: " + s);
                s.close();
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
            }
        }
    }

    private void processOperation(Object obj) throws IOException, ClassNotFoundException {
        if (obj instanceof StopOperation) {
            finish();
        } else if (obj instanceof AddMetroCardOperation) {
            addCard(obj);
        } else if (obj instanceof AddMoneyOperation) {
            addMoney(obj);
        } else if (obj instanceof PayMoneyOperation) {
            payMoney(obj);
        } else if (obj instanceof RemoveCardOperation) {
            removeCard(obj);
        } else if (obj instanceof ShowBalanceOperation) {
            showBalance(obj);
        } else if (obj instanceof ShowCardInfoOperation) {
            showCardInfo(obj);
        } else if (obj instanceof ExportCardsToXMLOperation) {
            exportCardsToXML(obj);
        } else {
            error();
        }
    }


    private void addCard(Object obj) throws IOException, ClassNotFoundException {
        bnk.addCard(((AddMetroCardOperation) obj).getCrd());
        os.writeObject("Card Added");
        os.flush();
    }

    private void addMoney(Object obj) throws IOException, ClassNotFoundException {
        AddMoneyOperation op = (AddMoneyOperation) obj;
        boolean res = bnk.addMoney(op.getSerNum(), op.getMoney());
        if (res) {
            os.writeObject("Balance Added");
            os.flush();
        } else {
            os.writeObject("Cannot Balance Added");
            os.flush();
        }
    }

    private void payMoney(Object obj) throws IOException, ClassNotFoundException {
        PayMoneyOperation op = (PayMoneyOperation) obj;
        boolean res = bnk.getMoney(op.getSerNum(), op.getMoney());
        if (res) {
            os.writeObject("Money Paid");
            os.flush();
        } else {
            os.writeObject("Cannot Pay Money");
            os.flush();
        }
    }

    private void removeCard(Object obj) throws IOException, ClassNotFoundException {
        RemoveCardOperation op = (RemoveCardOperation) obj;
        boolean res = bnk.removeCard(op.getSerNum());
        if (res) {
            os.writeObject("Metro Card Successfully Removed: " + op.getSerNum());
            os.flush();
        } else {
            os.writeObject("Cannot Remove Metro Card: " + op.getSerNum());
            os.flush();
        }
    }

    private void showBalance(Object obj) throws IOException, ClassNotFoundException {
        ShowBalanceOperation op = (ShowBalanceOperation) obj;
        int index = bnk.findMetroCard(op.getSerNum());
        if (index >= 0) {
            os.writeObject("Card: " + op.getSerNum() + ", Balance: " + bnk.getStore().get(index).getBalance());
            os.flush();
        } else {
            os.writeObject("Cannot Show Balance for Card: " + op.getSerNum());
            os.flush();
        }
    }

    private void showCardInfo(Object obj) throws IOException, ClassNotFoundException {
        ShowCardInfoOperation op = (ShowCardInfoOperation) obj;
        int index = bnk.findMetroCard(op.getSerNum());
        if (index >= 0) {
            MetroCard card = bnk.getStore().get(index);
            os.writeObject("Metro Card Information:\n" + card.toString());
            os.flush();
        } else {
            os.writeObject("Cannot Show Information for Card: " + op.getSerNum());
            os.flush();
        }
    }

    private void exportCardsToXML(Object obj) throws IOException {
        ExportCardsToXMLOperation op = (ExportCardsToXMLOperation) obj;
        String filePath = op.getFilePath();
        boolean result = bnk.exportCardsToXML(filePath);
        if (result) {
            os.writeObject("Exported cards to " + filePath);
            os.flush();
        } else {
            os.writeObject("Failed to export cards to " + filePath);
            os.flush();
        }
    }

    private void finish() throws IOException {
        work = false;
        os.writeObject("Finish Work " + s);
        os.flush();
    }

    private void error() throws IOException {
        os.writeObject("Bad Operation");
        os.flush();
    }
}
