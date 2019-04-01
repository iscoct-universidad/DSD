import java.rmi.RemoteException;

public interface InterfazBroker extends InterfazServidor {
	public void addServidor (InterfazServidor servidor) throws RemoteException;
	public void removeServidor (InterfazServidor servidor) throws RemoteException;
}
