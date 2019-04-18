const Agente = require('../agente').default;
const MotorPersiana = require('../motorPersiana').default;
const AireAcondicionado = require('../aireAcondicionado').default;
const Luminosidad = require('../luminosidad').default;
const Temperatura = require('../temperatura').default;

test('agente constructor y rangos del agente', () => {
	const agente = new Agente();
	
	expect(agente.getMaxLuminosidad()).toBe(50);
	expect(agente.getMinLuminosidad()).toBe(20);
	expect(agente.getMaxTemperatura()).toBe(30);
	expect(agente.getMinTemperatura()).toBe(10);
});

describe('proceso de añadir Actuadores al agente y eliminarlos', () => {
	let motores = [];
	let aires = [];
	let agente;
	
	beforeEach(() => {
		agente = new Agente();
		motores.push(new MotorPersiana());
		aires.push(new AireAcondicionado());
	});
	
	afterEach(() => {
		agente = null;
		motores = [];
		aires = [];
	});
	
	test('añadimos un aire al agente, tomamos los aires, cogemos el primer y vemos \
			si es de tipo AireAcondicionado', () => {
		agente.addAireAcondicionado(aires[0]);
		let aire = agente.getAiresAcondicionados()[0];
		
		expect(aire).toBeInstanceOf(AireAcondicionado);
	});
	
	test('añadimos un motor al agente, tomamos los motores, cogemos el primero y vemos \
			si es de tipo MotorPersiana', () => {
		agente.addMotorPersiana(motores[0]);
		
		let motor = agente.getMotoresPersiana()[0];
	
		expect(motor).toBeInstanceOf(MotorPersiana);
	});
	
	test('agente añadir aire, tomarlo, ver que se ha añadido, eliminarlo y ver que \
			se ha eliminado con éxito', () => {
		let i = 0;
		
		for(let motor of motores) {
			agente.addMotorPersiana(motor);
			++i;
			expect(agente.getMotoresPersiana().length).toBe(i);
		}
		
		expect(agente.getMotoresPersiana().length).toBe(motores.length);
		
		for(let motor of motores) {
			agente.removeMotorPersiana(motor);
			--i;
			expect(agente.getMotoresPersiana().length).toBe(i);
		}
		
		expect(agente.getMotoresPersiana().length).toBe(0);
	});
	
	test('añade un aireAcondicionado, ve que se ha añadido, se elimina y vemos que se ha eliminado con éxito', () => {
		let i = 0;
		
		for(let aire of aires) {
			agente.addAireAcondicionado(aire);
			++i;
			expect(agente.getAiresAcondicionados().length).toBe(i);
		}
		
		expect(agente.getAiresAcondicionados().length).toBe(aires.length);
		
		for(let aire of aires) {
			agente.removeAireAcondicionado(aire);
			--i;
			expect(agente.getAiresAcondicionados().length).toBe(i);
		}
		
		expect(agente.getAiresAcondicionados().length).toBe(0);
	});
	
	test('añade un aireAcondicionado y un motorPersiana, crea un sensor Luminosidad con valor mayor al getMaxLuminosidad y vemos como cambia el actuador', () => {
		for(let aire of aires) {
			expect(aire).toBeInstanceOf(AireAcondicionado);
			agente.addAireAcondicionado(aire);
		}
		
		for(let motor of motores)
			agente.addMotorPersiana(motor);
			
		const temperatura = new Temperatura(agente.getMaxTemperatura() + 1);
		const luminosidad = new Luminosidad(agente.getMaxLuminosidad() - 1);
		let accionesAire = [];
		let accionesMotores = [];
		let airesAgente = agente.getAiresAcondicionados();
		let motoresAgente = agente.getMotoresPersiana();
		
		expect(airesAgente.length).toBe(1);
		expect(airesAgente[0]).toBeInstanceOf(AireAcondicionado);
		
		for(let aire of airesAgente)
			accionesAire.push(aire.realizandoAccion());

		for(let motor of motoresAgente)
			accionesMotores.push(motor.realizandoAccion());
			
		agente.realizarAcciones(luminosidad);
		agente.realizarAcciones(temperatura);
		
		let i = 0;
		
		for(let aire of agente.getAiresAcondicionados()) {
			expect(aire.realizandoAccion()).toBe(! accionesAire[i]);
			++i;
		}
		
		i = 0;
		
		for(let motor of agente.getMotoresPersiana()) {
			expect(motor.realizandoAccion()).toBe(accionesMotores[i]);
			++i;
		}
	});
});
