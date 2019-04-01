import java.lang.Exception;

public class ExcepYaRegistrado extends Exception {
	public ExcepYaRegistrado () {
		super ("El usuario ya está registrado en el sistema");
	}
	
	public ExcepYaRegistrado (String msg) {
		super (msg);
	}
}
