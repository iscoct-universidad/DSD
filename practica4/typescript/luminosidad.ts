import Sensor from './sensor';

export default class Luminosidad implements Sensor {
	private luminosidad: float;
	
	constructor(luminosidad: float) {
		this.luminosidad = luminosidad;
	}
	
	actualizarEstado(estado: float): void {
		this.luminosidad = estado;
	}
	
	getEstado(): float {
		return this.luminosidad;
	}
}
