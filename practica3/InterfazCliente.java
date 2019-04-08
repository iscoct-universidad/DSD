import java.rmi.Remote;
import java.rmi.RemoteException;

/*
	Las operaciones públicas que se exportan de un cliente.
	
	Estas son las operaciones que podrá realizar un usuario con sus datos como cliente
*/

public interface InterfazCliente extends Remote {
	
	// Devuelve la cantidad donada del usuario
		
	public float getCuantiaDon() throws RemoteException;
	
	// Devuelve el nombre de la cuenta del usuario
	
	public String getNombreCuenta() throws RemoteException;
	
	// Devuelve si el usuario ha donado ya alguna cantidad en el sistema o no
	
	public boolean getDonado() throws RemoteException;
	
	// Compara el cliente implícito con el cliente que se envía como parámetro
	
	public boolean equals(Cliente cliente) throws RemoteException;
}
