import Sensor from './sensor';

export default class Temperatura implements Sensor {
	private temperatura: float;
	
	constructor(temperatura: float) {
		this.temperatura = temperatura;
	}
	
	actualizarEstado(estado: float): void {
		this.temperatura = estado;
	}
	
	getEstado(): float {
		return this.temperatura;
	}
}
