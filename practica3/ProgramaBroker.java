import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ProgramaBroker {
	public static void main (String [] args) {
		if(args.length > 2) {
			if (System.getSecurityManager () == null)
				System.setSecurityManager (new SecurityManager ());
			
			try {
				String hostRegistry = args[0];
				int puertoRegistry = Integer.parseInt(args[1]);
				String nombreServidor = args[2];
				
				// Tomamos el registry con el host y puerto indicados
				
				Registry registry = LocateRegistry.getRegistry (hostRegistry, puertoRegistry);
				
				// Creamos el servidor Broker
				
				InterfazBroker broker = new Broker(nombreServidor);
				
				System.out.println("Exportamos objeto y tomamos registry");

				/*
					Exportamos el servidor Broker a un puerto que el SO
					asignará dinámicamente (por eso ponemos el 0)
				*/
				
				InterfazBroker stubServidor = (InterfazBroker) UnicastRemoteObject.exportObject (broker, 0);
				
				System.out.println("Le damos nombre al servidor");
				System.out.println("Nombre servidor: " + nombreServidor);

				/*
					Enlazamos el nombre del servidor que le hemos asignado
					al stub del servidor
				*/
				
				registry.rebind (nombreServidor, stubServidor);
				
				if(args.length == 6) {
					String hostRegistryRegistrarse = args[3];
					int puertoRegistryRegistrarse = Integer.parseInt(args[4]);
					String nombreServidorRegistrarse = args[5];
					
					System.out.println("Buscando al servidor " + nombreServidorRegistrarse + " para registrarse en él");
					
					Registry registryRegistrarse = LocateRegistry.getRegistry(hostRegistryRegistrarse, puertoRegistryRegistrarse);
					InterfazBroker brokerRegistrarse = (InterfazBroker) registryRegistrarse.lookup(nombreServidorRegistrarse);
					
					brokerRegistrarse.addServidor(broker);
				}
				
				System.out.println ("Ejecutando el programa servidor");
			} catch (Exception e) {
				System.err.println ("Fallo al ejecutar el programa servidor: ");
				e.printStackTrace ();
			}
		} else {
			System.err.println("java <programa> <hostRegistry> <puertoRegistry> <nombreServidor> [<nombreServidorASuscribirse>]");
		}
	}
}
