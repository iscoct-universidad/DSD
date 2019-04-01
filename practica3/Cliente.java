public class Cliente implements InterfazCliente {
	private int estado;
	private float cuantiaDon;
	private final String nombreCuenta;

	Cliente (String nombreCuenta) {
		this.estado = 1;
		this.cuantiaDon = 0;
		this.nombreCuenta = nombreCuenta;
	}
	
	public float getCuantiaDon () {
		return this.cuantiaDon;
	}
	
	public String getNombreCuenta () {
		return this.nombreCuenta;
	}
	
	public void donar (float cuantia) {
		cuantiaDon += cuantia;		// Cuantía donada por el cliente
	}
	
	public int getEstado() {
		return this.estado;
	}
	
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	// 2 clientes son iguales si tienen el mismo nombre de cuenta
	
	public boolean equals (Cliente cliente) {
		return cliente.getNombreCuenta ().equals (this.getNombreCuenta ());
	}
	
	public boolean equals (String nombreCuenta) {
		return this.getNombreCuenta().equals(nombreCuenta);
	}
}
