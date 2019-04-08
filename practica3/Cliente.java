public class Cliente implements InterfazClientePrivada {
	private boolean donado;
	private float cuantiaDon;
	private final String nombreCuenta;

	Cliente (String nombreCuenta) {
		this.donado = false;
		this.cuantiaDon = 0;
		this.nombreCuenta = nombreCuenta;
	}
	
	public float getCuantiaDon () {
		return this.cuantiaDon;
	}
	
	public String getNombreCuenta () {
		return this.nombreCuenta;
	}
	
	synchronized public void donar (float cuantia) {
		cuantiaDon += cuantia;		// Cuantía donada por el cliente
		donado = true;
	}
	
	public boolean getDonado() {
		return this.donado;
	}
	
	// 2 clientes son iguales si tienen el mismo nombre de cuenta
	
	public boolean equals (Cliente cliente) {
		return cliente.getNombreCuenta ().equals (this.getNombreCuenta ());
	}
	
	public boolean equals (String nombreCuenta) {
		return this.getNombreCuenta().equals(nombreCuenta);
	}
}
