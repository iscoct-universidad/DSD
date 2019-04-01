import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProgramaCliente {
	public static void main (String [] args) {
		if(args.length != 2) {
			System.err.println("java <programa> <puertoRmiregistry> <nombreServidor>");
		} else {
			if (System.getSecurityManager () == null)
				System.setSecurityManager (new SecurityManager ());
			
			ArrayList<String> opciones = new ArrayList<>();
			
			opciones.add("Salir");
			opciones.add("Registrarse");
			opciones.add("Autenticarse");
			
			int opcion = JOptionPane.showOptionDialog(null, "¿Qué operación quiere realizar?",
				"Opciones", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones.toArray(), null);
				
			if(opcion != 0 && opcion != -1) {
				try {
					String nombreServidor = args[1];
					int numRegistry = Integer.parseInt(args[0]);
					
					System.out.println("Tomamos el registry");
					
					Registry registry = LocateRegistry.getRegistry(numRegistry);
					
					System.out.println("Buscamos el objeto remoto");
					
					InterfazServidor serv = (InterfazServidor) registry.lookup(nombreServidor);
					InterfazCliente cliente = null;
					boolean todoCorrecto = false;
					
					do {
						String nombre = JOptionPane.showInputDialog(null, "Introduzca su nombre: ");
						try {
							if(opcion == 1)
								cliente = serv.registrarse(nombre);
							else
								cliente = serv.autenticarse(nombre);
								
							todoCorrecto = true;
						} catch(Exception e) {
							JOptionPane.showMessageDialog(null, "El nombre y la operación escogida no son válidos");
						}
					} while(! todoCorrecto);
					
					do {
						int estadoCliente = cliente.getEstado();

						System.out.println("Estado del cliente: " + estadoCliente);
						
						opciones.clear();
						opciones.add("Salir");
						opciones.add("Donar");
						opciones.add("Consultar donado personalmente");
							
						if(estadoCliente == 3)
							opciones.add("Consultar donado total");
							
						opcion = JOptionPane.showOptionDialog(null, "¿Qué operación quiere realizar", "Opciones", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, opciones.toArray(), null);
							
						ProgramaCliente.ejecutarOpcion(serv, cliente, opcion);
					} while(opcion != 0);
					
				} catch (Exception e) {
					System.err.println ("Error en el programa cliente: ");
					e.printStackTrace ();
				}
			}
			
			JOptionPane.showMessageDialog(null, "Gracias por utilizar nuestra plataforma");
		}
	}
	
	private static void ejecutarOpcion(InterfazServidor serv, InterfazCliente cliente, int opcion) {
		try {
			switch(opcion) {
				case 1:
					float cantidadADonar = Float.parseFloat(JOptionPane.showInputDialog(null, "Cantidad a donar: "));
					
					serv.donar(cliente, cantidadADonar);
					
					System.out.println("Cantidad a donar: " + cantidadADonar);
					JOptionPane.showMessageDialog(null, "Cantidad donada actualmente por el cliente: " + cliente.getCuantiaDon());
					System.out.println("Cantidad donada actualmente por el cliente: " + cliente.getCuantiaDon());
				break;
				case 2:
					JOptionPane.showMessageDialog(null, "Cantidad donada: " + cliente.getCuantiaDon());
					
					System.out.println("Cantidad donada: " + cliente.getCuantiaDon());
				break;
				case 3:
					float cantidadDonada = serv.consultaDonaciones(cliente);
					
					JOptionPane.showMessageDialog(null, "Cantidad donada total: " + cantidadDonada);
					System.out.println("Cantidad donada total: " + cantidadDonada);
				break;
			}
		} catch(Exception e) {
			System.err.println("Hubo un error en ejecutarOpcion del ProgramaCliente");
		}
	}
}
