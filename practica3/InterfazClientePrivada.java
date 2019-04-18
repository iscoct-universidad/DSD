import java.rmi.RemoteException;

/*
	Interfaz de cliente privada que sólo será accesible desde los servidores
	y no desde los clientes.
	
	Esto obliga a que las operaciones de donación se hagan desde los servidores
	y se pueda seguir accediendo a sus datos sin comprometer a la seguridad
	del sistema
*/

public interface InterfazClientePrivada extends InterfazCliente {
	/*
		Suma a la cantidad donada la cuantía introducida como parámetro
		
		pre: cuantia Debe ser > 0
	*/
	
	public void donar(float cuantia) throws RemoteException;
	
	/*
		Compara el nombre de cuenta de usuario del cliente implícito 
		con el nombre de cuenta introducido como argumento
	*/
	
	public boolean equals(String nombreCuenta) throws RemoteException;
}
