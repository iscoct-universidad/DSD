import Sensor from './sensor';

export default class Temperatura implements Sensor {
	private temperatura: number;
	
	constructor(temperatura: number) {
		this.temperatura = temperatura;
	}
	
	actualizarEstado(estado: number): void {
		this.temperatura = estado;
	}
	
	getEstado(): number {
		return this.temperatura;
	}
}
