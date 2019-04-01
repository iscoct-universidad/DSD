import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ProgramaBroker {
	public static void main (String [] args) {
		if(args.length != 2) {
			System.err.println("java <programa> <puertoRegistry> <numeroExportado>");
		} else {
			if (System.getSecurityManager () == null)
				System.setSecurityManager (new SecurityManager ());
			
			try {
				String nombreServidor = "Broker";
				int numRegistry = Integer.parseInt(args[0]);
				
				Registry registry = LocateRegistry.getRegistry (numRegistry);
				InterfazBroker broker = new Broker();
				int numAExportar = Integer.parseInt(args[1]);
				
				System.out.println("Número que exportará: " + numAExportar);
				System.out.println("Exportamos objeto y tomamos registry");

				InterfazBroker stubServidor = (InterfazBroker) UnicastRemoteObject.exportObject (broker, 0);
				
				System.out.println("Le damos nombre al servidor");
				System.out.println("Nombre servidor: " + nombreServidor);
				System.out.println("Puerto rmiregistry: " + args[0]);
				registry.rebind (nombreServidor, stubServidor);
				
				System.out.println ("Ejecutando el programa servidor");
			} catch (Exception e) {
				System.err.println ("Fallo al ejecutar el programa servidor: ");
				e.printStackTrace ();
			}
		}
	}
}
