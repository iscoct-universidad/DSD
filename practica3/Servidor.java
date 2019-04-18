import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.rmi.RemoteException;

public class Servidor implements InterfazServidor {
	private float subTotalDon;
	private ArrayList<InterfazClientePrivada> clientes;
	private Registry reg;
	private final String nombreServidor;
	
	/*
		El registry se necesita para crear en ese registry
		los clientes, ya que cuando los crea, les hace un rebind desde
		ese registry
	*/
	
	Servidor (String nombreServidor, Registry reg) {
		this.nombreServidor = nombreServidor;
		this.reg = reg; 
		clientes = new ArrayList<InterfazClientePrivada> ();
		subTotalDon = 0;
	}
	
	/*
		Devuelve la cuantía que se ha donado en este servidor si, y sólo si,
		el cliente ya ha donado alguna cuantía en el sistema
	*/
	
	public float consultaDonaciones (InterfazCliente cliente) throws ExcepNoDonado, ExcepNoDonado, RemoteException {
		if(cliente.getDonado())
			return this.subTotalDon;
		else
			throw new ExcepNoDonado();
	}
	
	/*
		Comprueba si el cliente ya se encuentra registrado en el sistema
		por su nombre de cuenta
		
		Se duelve -1 si no se ha encontrado el usuario en el servidor
		o la posición en la que se encuentra en el sistema
	*/
	
	public int clienteYaRegistrado (String nombreUsuario) throws RemoteException {
		int i = 0;
		int tamClientes = clientes.size();
		boolean encontrado = false;
		
		System.out.println("Entramos en clienteYaRegistrado con nombreUsuario");
		System.out.println("Nombre enviado: " + nombreUsuario);
		System.out.println("Tamaño del array de clientes: " + this.clientes.size());
		
		/*
			Mientras que no lo hayamos encontrado y no hayamos mirado en todos
			los servidores
		*/
		
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
	
	// Comprueba si se encuentra en el sistema el usuario a través de su referencia
	
	int clienteYaRegistrado (InterfazCliente cliente) throws RemoteException {
		return clienteYaRegistrado(cliente.getNombreCuenta());
	}
	
	/*
		Registra al usuario si no se encuentra en el sistema y con el nombre
		de cuenta introducido como argumento
		
		El nombre del usuario en la exportación será 
			<NombreServidor>Cliente<numCliente>
	*/
	
	public InterfazCliente registrarse (String nombreUsuario) throws ExcepYaRegistrado, RemoteException {
	 	InterfazClientePrivada cliente = new Cliente (nombreUsuario);
	 	InterfazCliente stubCliente = (InterfazCliente) UnicastRemoteObject.exportObject(cliente, 0);
		
		reg.rebind(this.nombreServidor + "Cliente" + clientes.size(), stubCliente);
		
		System.out.println("Registrando al cliente: " + this.nombreServidor + "Cliente" + clientes.size());
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
	
	/*
		Método que se publica a los usuarios que delega en el método siguiente
	*/
	
	public void donar (InterfazCliente cliente, float cuantia) throws ExcepNoRegistrado, RemoteException {
		donar((InterfazClientePrivada) cliente, cuantia);
	}
	
	/*
		Si el usuario se encuentra dentro de este servidor, dona
	*/
	
	public void donar (InterfazClientePrivada cliente, float cuantia) throws ExcepNoRegistrado, RemoteException {
		if (this.clienteYaRegistrado (cliente) != -1) {
			cliente.donar(cuantia);
			
			subTotalDon += cuantia;
		} else
			throw new ExcepNoRegistrado ();
	}
	
	public String getNombreServidor(InterfazCliente cliente) throws ExcepNoRegistrado, RemoteException {
		if(this.clienteYaRegistrado(cliente) != -1)
			return this.nombreServidor;
		else
			throw new ExcepNoRegistrado();
	}
	
	public int numClientes () {
		return clientes.size ();
	}
}
