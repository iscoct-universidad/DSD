export default interface Sensor {
	actualizarEstado(estado: any): void;
	getEstado(): any;
}
