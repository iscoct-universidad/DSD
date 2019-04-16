import Luminosidad from './luminosidad';
import Temperatura from './temperatura';

export default class Agente {
	private MAX_LUMINOSIDAD: number;
	private MIN_LUMINOSIDAD: number;
	private MAX_TEMPERATURA: number;
	private MIN_TEMPERATURA: number;
	
	private motoresPersiana: Array<MotorPersiana>;
	private airesAcondicionados: Array<AireAcondicionado>;
	
	constructor() {
		this.MAX_LUMINOSIDAD = 50;
		this.MIN_LUMINOSIDAD = 20;
		this.MAX_TEMPERATURA = 30;
		this.MIN_TEMPERATURA = 10;
		this.motoresPersiana = [];
		this.airesAcondicionados = [];
	}
	
	public addMotorPersiana(motor: MotorPersiana): void {
		this.getMotoresPersiana().push(motor);
	}
	
	public addAireAcondicionado(aire: AireAcondicionado): void {
		this.getAiresAcondicionados().push(aire);
	}
	
	public removeMotorPersiana(motor: MotorPersiana): void {
		const pos: number = this.getMotoresPersiana().indexOf(motor);
		
		this.getMotoresPersiana().splice(pos, 1);
	}
	
	public removeAireAcondicionado(aire: AireAcondicionado): void {
		const pos: number = this.getAiresAcondicionados().indexOf(aire);
		
		this.getAiresAcondicionados().splice(pos, 1);
	}
	
	public getMotoresPersiana(): Array<MotorPersiana> {
		return this.motoresPersiana;
	}
	
	public getAiresAcondicionados(): Array<AireAcondicionado> {
		return this.airesAcondicionados;
	}
	
	public realizarAcciones(sensor: Sensor): void {
		if(sensor instanceof Luminosidad) {
			for(let motor of this.getMotoresPersiana())
				if(sensor.getEstado() > this.getMaxLuminosidad())
					motor.setAccion(true);
		} else if(sensor instanceof Temperatura) {
			for(let aire of this.getAiresAcondicionados())
				if(sensor.getEstado() > this.getMaxTemperatura())
					aire.setAccion(true);
		}
	}
	
	public getMaxLuminosidad() {
		return this.MAX_LUMINOSIDAD;
	}
	
	public getMinLuminosidad() {
		return this.MIN_LUMINOSIDAD;
	}
	
	public getMaxTemperatura() {
		return this.MAX_TEMPERATURA;
	}
	
	public getMinTemperatura() {
		return this.MIN_TEMPERATURA;
	}
}
