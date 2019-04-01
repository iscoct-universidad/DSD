import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ProgramaServidor {
	public static void main (String [] args) {
		if(args.length != 2) {
			System.err.println("java <programa> <puertoRegistry> <nombreServidor>");
		} else {
			if (System.getSecurityManager () == null)
				System.setSecurityManager (new SecurityManager ());
			
			try {
				String nombreServidor = args[1];
				int numRegistry = Integer.parseInt(args[0]);
				Registry registry = LocateRegistry.getRegistry (numRegistry);
				InterfazServidor servidor = new Servidor(registry);
				
				System.out.println("Exportamos objeto y tomamos registry");
				
				InterfazServidor stubServidor = (InterfazServidor) UnicastRemoteObject.exportObject (servidor, 0);
				
				System.out.println("Le damos nombre al servidor");
				System.out.println("Nombre servidor: " + nombreServidor);
				System.out.println("Puerto rmiregistry: " + args[0]);
				registry.rebind (nombreServidor, stubServidor);
				
				String nombreBroker = "Broker";
				InterfazBroker broker = (InterfazBroker) registry.lookup(nombreBroker);
				
				broker.addServidor(servidor);
				
				System.out.println ("Ejecutando el programa servidor");
			} catch (Exception e) {
				System.err.println ("Fallo al ejecutar el programa servidor: ");
				e.printStackTrace ();
			}
		}
	}
}
