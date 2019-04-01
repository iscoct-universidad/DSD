import java.lang.Exception;

public class ExcepNoDonado extends Exception {
	public ExcepNoDonado () {
		super ("El usuario está registrado pero no ha donado aún");
	}
	
	public ExcepNoDonado (String msg) {
		super (msg);
	}
}
