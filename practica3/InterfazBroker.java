import java.rmi.RemoteException;

/*
	Un Broker es un servidor que sirve como proxy cuya única finalidad es el balanceo
	de la carga entre los distintos servidores que tenga en su haber.
	
	Su funcionalidad concretando un poco más es la de insertar un cliente en el servidor
	en su haber que tenga menos cliente registrados, y si se quiere, hacer de 
	intermediario entre el cliente y el servidor pudiendo hacer todas las operaciones
	en el Broker. Este ya se encarga de buscar el servidor donde se encuentre el cliente
	y realizar la operación que se ha solicitado (en el caso de que existiera dicho cliente).
	
	Las operaciones son iguales que las de un servidor excepto que se pueden añadir
	y eliminar servidores en su interior (ya sean otros proxys para descentralizar
	aún más la carga, u otros servidores (que harán el papel de hojas, ya que son
	los únicos que pueden contener los clientes)
*/

public interface InterfazBroker extends InterfazServidor {
	// Añadir un servidor al Broker actual
	
	public void addServidor (InterfazServidor servidor) throws RemoteException;
	
	// Eliminar un servidor al Broker actual
	
	public void removeServidor (InterfazServidor servidor) throws RemoteException;
	
	/*
		Devuelve el servidor en el que se encuentre un cliente
		
		pre:	El cliente ya debe estar registrado en algún servidor que cuelgue
				de este Broker
	*/
	
	public InterfazServidor getServidor(InterfazCliente cliente) throws ExcepNoRegistrado, RemoteException;
}
