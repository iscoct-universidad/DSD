import Actuador from './actuador';

export default class MotorPersiana implements Actuador {
	public abierta: boolean;
	
	constructor(public persianaAbierta: boolean = false) {
		this.abierta = persianaAbierta;
	}
	
	realizarAccion(): void {
		this.abierta = ! this.abierta;
	}
	
	setAccion(accion: boolean): void {
		this.abierta = accion;
	}
	
	realizandoAccion(): boolean {
		return this.abierta;
	}
}
