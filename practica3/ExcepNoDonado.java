import java.lang.Exception;

/*
	Excepción que se lanzará cuando un usuario intente ver la cuantía donada
	en el sistema entero y todavía no haya donado.
*/

public class ExcepNoDonado extends Exception {
	public ExcepNoDonado () {
		super ("El usuario está registrado pero no ha donado aún");
	}
	
	public ExcepNoDonado (String msg) {
		super (msg);
	}
}
