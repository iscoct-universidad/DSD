import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.rmi.RemoteException;

public class Servidor implements InterfazServidor {
	private float subTotalDon;
	private ArrayList<InterfazCliente> clientes;
	private Registry reg;
	
	Servidor (Registry reg) {
		this.reg = reg; 
		clientes = new ArrayList<InterfazCliente> ();
		subTotalDon = 0;
	}
	
	public float consultaDonaciones (InterfazCliente cliente) {
		return this.subTotalDon;
	}
	
	public int clienteYaRegistrado (String nombreUsuario) throws RemoteException {
		int i = 0;
		int tamClientes = clientes.size();
		boolean encontrado = false;
		
		System.out.println("Entramos en clienteYaRegistrado con nombreUsuario");
		System.out.println("Nombre enviado: " + nombreUsuario);
		System.out.println("Tamaño del array de clientes: " + this.clientes.size());
		
		while(! encontrado && i < tamClientes) {
			String nombre = clientes.get(i).getNombreCuenta();
			
			System.out.println("Nombre del usuario actual: " + nombre);
			
			if(this.clientes.get(i).equals(nombreUsuario)) {
				encontrado = true;
			} else
				++i;
		}
		
		if(! encontrado)
			i = -1;
			
		System.out.println("Posición devuelta en la búsqueda por el nombre: " + i + "\n");
		
		return i;
	}
	
	int clienteYaRegistrado (InterfazCliente cliente) throws RemoteException {
		return clienteYaRegistrado(cliente.getNombreCuenta());
	}
	
	public InterfazCliente registrarse (String nombreUsuario) throws ExcepYaRegistrado, RemoteException {
	 	InterfazCliente cliente = new Cliente (nombreUsuario);
	 	InterfazCliente stubCliente = (InterfazCliente) UnicastRemoteObject.exportObject(cliente, 0);
		
		reg.rebind("Cliente" + clientes.size(), stubCliente);
		
		System.out.println("Registrando al cliente: Cliente" + clientes.size());
		System.out.println("Nombre del cliente: " + nombreUsuario + "\n");
		
		if (this.clienteYaRegistrado (cliente) == -1) {
			clientes.add (cliente);
			
			return cliente;
		} else
			throw new ExcepYaRegistrado ();
	}
	
	public InterfazCliente autenticarse(String nombreUsuario) throws ExcepNoRegistrado, RemoteException {
		int pos = this.clienteYaRegistrado(nombreUsuario);
		
		System.out.println("Posición devuelta en la búsqueda del usuario, " + nombreUsuario + ": " + pos + "\n");
		
		if(pos != -1)
			return clientes.get(pos);
		else
			throw new ExcepNoRegistrado();
	}
	
	public void donar (InterfazCliente cliente, float cuantia) throws ExcepNoRegistrado, RemoteException {
		if (this.clienteYaRegistrado (cliente) != -1) {
			cliente.donar(cuantia);
			
			System.out.println("Estado del cliente antes de donar: " + cliente.getEstado());
			cliente.setEstado(3);
			System.out.println("Estado del cliente después de donar: " + cliente.getEstado() + "\n");
			
			subTotalDon += cuantia;
		} else
			throw new ExcepNoRegistrado ();
	}
	
	public int numClientes () {
		return clientes.size ();
	}
}
