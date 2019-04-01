import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.rmi.RemoteException;

public class Broker implements InterfazBroker {
	private int numClientes;
	private float totalDon;
	private ArrayList<InterfazServidor> servidores;

	Broker() {
		this.numClientes = 0;
		this.totalDon = 0;
		servidores = new ArrayList<InterfazServidor>();
	}
	
	public float consultaDonaciones(InterfazCliente cliente) {
		return this.totalDon;
	}
	
	public int clienteYaRegistrado(String nombreUsuario) throws RemoteException {
		int i = 0;
		int tamServidores = this.sizeServ();
		boolean encontrado = false;
		
		System.out.println("Entramos en clienteYaRegistrado con nombreUsuario");
		System.out.println("Nombre enviado: " + nombreUsuario);
		
		while(! encontrado && i < tamServidores) {
			if(servidores.get(i).clienteYaRegistrado(nombreUsuario) != -1)
				encontrado = true;
			else
				++i;
		}
		
		if(! encontrado)
			i = -1;
			
		System.out.println("Posición devuelta en la búsqueda del cliente: " + i + 
			"\n");
		
		return i;
	}
	
	int clienteYaRegistrado(InterfazCliente cliente) throws RemoteException {
		return clienteYaRegistrado(cliente.getNombreCuenta());
	}
	
	public InterfazCliente registrarse(String nombreUsuario) throws ExcepYaRegistrado, RemoteException {
		System.out.println("Nombre a registrar: " + nombreUsuario);
		
		// Miramos si no está ya registrado en algún servidor
		
		int posServidor = this.clienteYaRegistrado(nombreUsuario);
		
		System.out.println("posServidor: " + posServidor);
		
		if(posServidor != -1)
			new ExcepYaRegistrado();
		
		System.out.println("Hasta aquí hemos llegado");
		
		// Buscar el servidor con el mínimo de clientes en su haber
		
		int minClientes = this.buscarServMinClientes();
		
		System.out.println("Servidor con el menor número de clientes: " + minClientes);
		
		// Registrarlo en ese servidor
		
		InterfazCliente cliente = this.servidores.get(minClientes).registrarse(nombreUsuario);
		
		this.numClientes++;
		
		System.out.println("Cliente registrado en el servidor: " + minClientes + "\n");
		
		return cliente;
	}
	
	public InterfazCliente autenticarse(String nombreUsuario) throws ExcepNoRegistrado, RemoteException {
		int pos = this.clienteYaRegistrado(nombreUsuario);
		
		return this.servidores.get(pos).autenticarse(nombreUsuario);
	}
	
	public void donar(InterfazCliente cliente, float cuantia) throws ExcepNoRegistrado, RemoteException {
		int posServidor = this.clienteYaRegistrado(cliente);
		
		System.out.println("Cantidad a donar: " + cuantia);
		System.out.println("Nombre del usuario que donará: " + cliente.getNombreCuenta());
		System.out.println("Número del servidor en donde se encuentra el cliente: " + posServidor + "\n");
		
		this.servidores.get(posServidor).donar(cliente, cuantia);
		this.totalDon += cuantia;
	}
	
	public void addServidor(InterfazServidor servidor) {
		this.servidores.add(servidor);
	}
	
	public void removeServidor(InterfazServidor servidor) {
		this.servidores.remove(servidor);
	}
	
	public int numClientes() {
		return this.numClientes();
	}
	
	private int sizeServ() {
		return this.servidores.size();
	}
	
	private int buscarServMinClientes() throws RemoteException {
		int posMin = 0;
		int min = this.servidores.get(0).numClientes();
		int i = 1;
		int tamServ = this.servidores.size();
		
		System.out.println("Mínimo actual: " + min);
		System.out.println("Tamaño del array de servidores: " + tamServ);
		
		while(i < tamServ) {
			int clientes = this.servidores.get(i).numClientes();
			
			System.out.println("Mínimo actual: " + min);
			System.out.println("Cliente en el servidor actual: " + clientes);
			
			if(min > clientes) {
				min = clientes;
				posMin = i;
			}
			
			++i;
		}
		
		System.out.println("posMin devuelto: " + posMin + "\n");
		
		return posMin;
	}
}
