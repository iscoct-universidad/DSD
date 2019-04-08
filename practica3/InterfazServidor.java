import java.rmi.Remote;
import java.rmi.RemoteException;

/*
	Servidor que controla los clientes que tiene y las operaciones que pueden 
	realizarse sobre él al público
*/

public interface InterfazServidor extends Remote {
	/*
		Registra al cliente con el nombre de cuenta introducido
	*/
	
	public InterfazCliente registrarse (String nombreUsuario) throws RemoteException, ExcepYaRegistrado;
	
	/*
		Autentica al cliente según el nombre de cuenta introducido
	*/
	
	public InterfazCliente autenticarse(String nombreUsuario) throws RemoteException, ExcepNoRegistrado;
	
	/*
		Consulta las donaciones de un cliente concreto a partir de su Interfaz
	*/
	
	public float consultaDonaciones (InterfazCliente cliente) throws RemoteException, ExcepNoRegistrado, ExcepNoDonado;
	
	/*
		Dona una cuantía determinada al cliente
	*/
	
	public void donar (InterfazCliente cliente, float cuantia) throws RemoteException, ExcepNoRegistrado;
	
	/*
		Comprueba si un cliente se encuentra o no registrado en el sistema
	*/
	
	public int clienteYaRegistrado(String nombreUsuario) throws RemoteException;
	
	/*
		Devuelve el nombre del servidor en el que se encuentra registrado un usuario
	*/
	
	public String getNombreServidor(InterfazCliente cliente) throws RemoteException, ExcepNoRegistrado;
	
	/*
		Devuelve el número de clientes que tiene en el servidor
	*/
	
	public int numClientes() throws RemoteException;
}
