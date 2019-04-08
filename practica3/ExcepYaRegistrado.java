import java.lang.Exception;

/*
	Excepción que se lanzará cuando un usuario intente registrarse en el sistema
	y ya esté registrado en él
*/

public class ExcepYaRegistrado extends Exception {
	public ExcepYaRegistrado () {
		super ("El usuario ya está registrado en el sistema");
	}
	
	public ExcepYaRegistrado (String msg) {
		super (msg);
	}
}
