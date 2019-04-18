import Sensor from './sensor';

export default class Luminosidad implements Sensor {
	private luminosidad: number;
	
	constructor(luminosidad: number) {
		this.luminosidad = luminosidad;
	}
	
	actualizarEstado(estado: number): void {
		this.luminosidad = estado;
	}
	
	getEstado(): number {
		return this.luminosidad;
	}
}
