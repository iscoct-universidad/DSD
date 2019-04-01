import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazServidor extends Remote {
	public InterfazCliente registrarse (String nombreUsuario) throws RemoteException, ExcepYaRegistrado;
	public InterfazCliente autenticarse(String nombreUsuario) throws RemoteException, ExcepNoRegistrado;
	public float consultaDonaciones (InterfazCliente cliente) throws RemoteException, ExcepNoRegistrado, ExcepNoDonado;
	public void donar (InterfazCliente cliente, float cuantia) throws RemoteException, ExcepNoRegistrado;
	public int clienteYaRegistrado(String nombreUsuario) throws RemoteException;
	public int numClientes() throws RemoteException;
}
