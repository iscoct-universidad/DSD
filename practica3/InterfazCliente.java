import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazCliente extends Remote {
	public float getCuantiaDon() throws RemoteException;
	public String getNombreCuenta() throws RemoteException;
	public void donar(float cuantia) throws RemoteException;
	public int getEstado() throws RemoteException;
	public void setEstado(int estado) throws RemoteException;
	public boolean equals(Cliente cliente) throws RemoteException;
	public boolean equals(String nombreCuenta) throws RemoteException;
}
