package client;

import rmi.Participant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ConferenceManager extends Remote {
    void registerParticipant(Participant participant) throws RemoteException;
    List<Participant> getParticipants() throws RemoteException;
    void saveDataToXML(String filename) throws RemoteException;
    void loadDataFromXML(String filename) throws RemoteException;
}

