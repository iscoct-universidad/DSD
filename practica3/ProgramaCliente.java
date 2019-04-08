import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProgramaCliente {
	public static void main (String [] args) {
		if(args.length != 3) {
			System.err.println("java <programa> <hostServidor> <puertoServidor> <nombreServidorARegistrarse>");
		} else {
			if (System.getSecurityManager () == null)
				System.setSecurityManager (new SecurityManager ());
			
			ArrayList<String> opciones = new ArrayList<>();
			
			opciones.add("Salir");
			opciones.add("Registrarse");
			opciones.add("Autenticarse");
			
			/*
				Le damos a escoger al usuario opciones para registrarse o autenticarse en el sistema
				o salir del programa
			*/
			
			int opcion = JOptionPane.showOptionDialog(null, "¿Qué operación quiere realizar?",
				"Opciones", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones.toArray(), null);
			
			/*
				Si no se escoge salir del programa
			*/
			
			if(opcion != 0 && opcion != -1) {
				try {
					String hostServidor = args[0];
					int puertoServidor = Integer.parseInt(args[1]);
					String nombreServidor = args[2];
					
					System.out.println("Tomamos el registry");
					System.out.println("Host: " + hostServidor);
					System.out.println("Puerto: " + puertoServidor);
					
					// Tomamos el registry por defecto
					
					Registry registry = LocateRegistry.getRegistry(hostServidor, puertoServidor);
					
					System.out.println("Buscamos el objeto remoto");
					
					
					/*
						Buscamos el servidor en el que vamos a intentar registrarnos
						o autenticarnos.
					*/
					
					InterfazServidor serv = (InterfazServidor) registry.lookup(nombreServidor);
					InterfazCliente cliente = null;
					boolean todoCorrecto = false;
					boolean fin = false;
					
					/*
						Hasta que no se escoja la opción registrar y el usuario no esté ya regitrado 
						o hasta que no se escoja la opción autenticar y el usuario sí esté registrado en el sistema 
						o se escoja salir de la aplicación
							Se sigue preguntando por la opción y el nombre
					*/
					
					do {
						String nombre = JOptionPane.showInputDialog(null, "Introduzca su nombre: ");
						System.out.println("Nombre: " + nombre);
					
						// Si el nombre es nulo es que le hemos dado a cancelar o a la x
						
						if(nombre != null) {
							try {
								if(opcion == 1)
									cliente = serv.registrarse(nombre);
								else if(opcion == 2)
									cliente = serv.autenticarse(nombre);
									
								todoCorrecto = true;
							} catch(Exception e) {
								JOptionPane.showMessageDialog(null, "El nombre y la operación escogida no son válidos");
							}
						} else
							fin = true;
					} while(! todoCorrecto && ! fin);
					
					// Si no se ha escojido salir de la aplicación
					
					if(! fin) {
						/*
							Hasta que no se quiera salir de la aplicación
							
							Se consulta el estado del cliente por si se registra
							y concurrentemente se dona en otra instancia del programa
							ya que debería de actualizarse el estado del cliente aquí
						*/
						
						do {
							/*
								Tomamos el estado del cliente para saber si
								ya ha donado o no
							*/
							
							boolean donado = cliente.getDonado();

							System.out.println("¿Ha donado ya? " + donado);
							
							opciones.clear();
							opciones.add("Salir");
							opciones.add("Donar");
							opciones.add("Consultar donado personalmente");
							opciones.add("Nombre del servidor donde está registrado");
							
							if(donado)
								opciones.add("Consultar donado total");
							
							// Se le pregunta al usuario que acción quiere realizar
							
							opcion = JOptionPane.showOptionDialog(null, "¿Qué operación quiere realizar", "Opciones", JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, opciones.toArray(), null);
							
							// Ejecutamos la opción
							
							ProgramaCliente.ejecutarOpcion(serv, cliente, opcion);
						} while(opcion != 0 && opcion != -1);
					}
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
			/*
				Si opción == 1
					Entonces quiere donar
						Se le pregunta por la cuantía a donar
						Se le muestra la cantidad donada por él hasta este momento
			
				Si opción == 2
					Entonces quiere consultar su cuantía donada
						Se le muestra la cuantía donada hasta ahora
				
				Si opción == 3
					Entonces quiere saber el nombre del servidor en el que pertenece
						Se le muestra el nombre
						
				Si opción == 4
					Entonces quiere consultar la cuantía donada total del sistema
						Se le muestra la cuantía donada en el sistema
			*/
			
			System.out.println("Opción: " + opcion);
			
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
					String nombreServidor = serv.getNombreServidor(cliente);
					
					JOptionPane.showMessageDialog(null, "Nombre del servidor: " + nombreServidor);
					
					System.out.println("Nombre del servidor: " + nombreServidor);
				break;
				case 4:
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
