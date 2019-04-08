import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.rmi.RemoteException;

public class Broker implements InterfazBroker {
	private int numClientes;
	private ArrayList<InterfazServidor> servidores;
	private String nombreServidor;
	
	Broker(String nombreServidor) {
		this.nombreServidor = nombreServidor;
		this.numClientes = 0;
		servidores = new ArrayList<InterfazServidor>();
	}
	
	/*
		Si el cliente ya ha donado
			se le devuelve la suma de la cuantía donada en todos
			los servidores que cuelgan del broker
		Sino
			Lanza excepción ExcepNoDonado
	*/
	
	public float consultaDonaciones(InterfazCliente cliente) throws ExcepNoRegistrado, ExcepNoDonado, RemoteException {
		if(cliente.getDonado()) {
			float totalDon = 0;
			
			for(InterfazServidor serv: this.servidores)
				totalDon += serv.consultaDonaciones(cliente);
			
			return totalDon;
		} else
			throw new ExcepNoDonado();
	}
	
	/*
		Comprueba si el nombre de cuenta se corresponde con algún cliente que se
		encuentre en alguno de los servidores que cuelgan del Broker
		
		Devuelve el servidor donde se encuentra dicho cliente
		Se devuelve -1 en caso de no encontrarlo
	*/
	
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
	
	/*
		Igual que el anterior pero con una referencia al cliente
	*/
	
	int clienteYaRegistrado(InterfazCliente cliente) throws RemoteException {
		return clienteYaRegistrado(cliente.getNombreCuenta());
	}
	
	/*
		Registra un cliente en el servidor que menos número de clientes tenga
	*/
	
	public InterfazCliente registrarse(String nombreUsuario) throws ExcepYaRegistrado, RemoteException {
		System.out.println("Nombre a registrar: " + nombreUsuario);
		
		// Miramos si no está ya registrado en algún servidor
		
		int posServidor = this.clienteYaRegistrado(nombreUsuario);
		
		System.out.println("posServidor: " + posServidor);
		
		if(posServidor != -1)
			throw new ExcepYaRegistrado();
		
		// Buscar el servidor con el mínimo de clientes en su haber
		
		int minClientes = this.buscarServMinClientes();
		
		System.out.println("Servidor con el menor número de clientes: " + minClientes);
		
		// Registrarlo en ese servidor
		
		InterfazCliente cliente = this.servidores.get(minClientes).registrarse(nombreUsuario);
		
		this.numClientes++;
		
		System.out.println("Cliente registrado en el servidor: " + minClientes + "\n");
		
		return cliente;
	}
	
	/*
		Busca si el cliente ya está registrado en el sistema
		
		Devuelve la Interfaz del cliente encontrado si existe
		o lanza una excepción tipo ExcepNoRegistrado sino existe
	*/
	
	public InterfazCliente autenticarse(String nombreUsuario) throws ExcepNoRegistrado, RemoteException {
		int pos = this.clienteYaRegistrado(nombreUsuario);
		
		return this.servidores.get(pos).autenticarse(nombreUsuario);
	}
	
	/*
		Si el cliente ya está registrado, dona la cuantía indicada por cuantia
		en el servidor donde se encuentra y aumenta la cuantía del Broker
	*/
	
	public void donar(InterfazCliente cliente, float cuantia) throws ExcepNoRegistrado, RemoteException {
		int posServidor = this.clienteYaRegistrado(cliente);
		
		System.out.println("Cantidad a donar: " + cuantia);
		System.out.println("Nombre del usuario que donará: " + cliente.getNombreCuenta());
		System.out.println("Número del servidor en donde se encuentra el cliente: " + posServidor + "\n");
		
		this.servidores.get(posServidor).donar(cliente, cuantia);
	}
	
	// Añade un servidor al Broker
	
	public void addServidor(InterfazServidor servidor) {
		this.servidores.add(servidor);
	}
	
	// Elimina un servidor del Broker
	
	public void removeServidor(InterfazServidor servidor) {
		this.servidores.remove(servidor);
	}
	
	// Devuelve la Interfaz la Interfaz del Servidor en la que se encuentra el cliente
	
	public InterfazServidor getServidor(InterfazCliente cliente) throws ExcepNoRegistrado, RemoteException {
		int pos = this.clienteYaRegistrado(cliente);
		
		return this.servidores.get(pos);
	}
	
	// Devuelve el nombre del servidor en el que se encuentra el usuario
	
	public String getNombreServidor(InterfazCliente cliente) throws ExcepNoRegistrado, RemoteException {
		int pos = this.clienteYaRegistrado(cliente);
		if(pos != -1)
			return this.servidores.get(pos).getNombreServidor(cliente);
		else
			throw new ExcepNoRegistrado();
	}
	
	/*
		Devuelve el número de clientes del Broker (es la suma de todos los servidores
		que cuelgan de él
	*/
	
	public int numClientes() {
		return this.numClientes();
	}
	
	// Devuelve el número de servidores que cuelgan del Broker
	
	private int sizeServ() {
		return this.servidores.size();
	}
	
	/*
		Busca el servidor con el mínimo número de clientes en su haber
	*/
	
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
