package tcpWork;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MetroCardBank {
    private ArrayList<MetroCard> store;

    public MetroCardBank() {
        store = new ArrayList<>();
    }

    public int findMetroCard(String serNum) {
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getSerNum().equals(serNum)) {
                return i;
            }
        }
        return -1;
    }

    public int numCards() {
        return store.size();
    }

    public ArrayList<MetroCard> getStore() {
        return store;
    }

    public void addCard(MetroCard newCard) {
        store.add(newCard);
    }

    public boolean removeCard(String serNum) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            store.remove(index);
            return true;
        }
        return false;
    }

    public boolean addMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            MetroCard card = store.get(index);
            double currentBalance = card.getBalance();
            card.setBalance(currentBalance + money);
            return true;
        }
        return false;
    }

    public boolean getMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            MetroCard card = store.get(index);
            double currentBalance = card.getBalance();
            if (currentBalance >= money) {
                card.setBalance(currentBalance - money);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("List of MetroCards:");
        for (MetroCard c : store) {
            buf.append("\n\n").append(c);
        }
        return buf.toString();
    }

    public boolean exportCardsToXML(String filePath) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filePath)))) {
            encoder.writeObject(store);
            return true;
        } catch (IOException e) {
            System.out.println("Error exporting cards to XML: " + e.getMessage());
            return false;
        }
    }
}

