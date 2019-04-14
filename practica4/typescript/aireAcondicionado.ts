import Actuador from './actuador';

export default class AireAcondicionado implements Actuador {
	public encendido: boolean;
	
	constructor(public persianaencendido: boolean = false) {
		this.encendido = persianaencendido;
	}
	
	realizarAccion(): void {
		this.encendido = ! this.encendido;
	}
	
	setAccion(accion: boolean): void {
		this.encendido = accion;
	}
	
	realizandoAccion(): boolean {
		return this.encendido;
	}
}
