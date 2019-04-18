import Actuador from './actuador';

/*
	Clase que implementa el Actuador
*/

export default class AireAcondicionado implements Actuador {
	public encendido: boolean;
	
	constructor(public persianaEncendida: boolean = false) {
		this.encendido = persianaEncendida;
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
