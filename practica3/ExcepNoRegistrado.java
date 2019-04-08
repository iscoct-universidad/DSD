import java.lang.Exception;

/*
	Excepción que se lanzará cuando un usuario pretenda realizar una operación
	en la que haya que estar ya registrado en el sistema y todavía no lo esté.
*/

public class ExcepNoRegistrado extends Exception {
	public ExcepNoRegistrado () {
		super ("El usuario no está registrado en el sistema");
	}
	
	public ExcepNoRegistrado (String msg) {
		super (msg);
	}
}
