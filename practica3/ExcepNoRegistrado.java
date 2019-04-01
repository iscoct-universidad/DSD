import java.lang.Exception;

public class ExcepNoRegistrado extends Exception {
	public ExcepNoRegistrado () {
		super ("El usuario no está registrado en el sistema");
	}
	
	public ExcepNoRegistrado (String msg) {
		super (msg);
	}
}
