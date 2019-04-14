const Agente = require('../agente').default;
const MotorPersiana = require('../motorPersiana').default;
const AireAcondicionado = require('../aireAcondicionado').default;

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
	
	beforeAll(() => {
		agente = new Agente();
		motores.push(new MotorPersiana());
		aires.push(new AireAcondicionado());
	});
	
	test('agente añadir aire, tomarlo, ver que se ha añadido, eliminarlo y ver que \
			se ha eliminado con éxito', () => {
		let i = 0;
		
		for(let motor in motores) {
			agente.addMotorPersiana(motor);
			++i;
			expect(agente.getMotoresPersiana().length).toBe(i);
		}
		
		expect(agente.getMotoresPersiana().length).toBe(motores.length);
		
		for(let motor in motores) {
			agente.removeMotorPersiana(motor);
			--i;
			expect(agente.getMotoresPersiana().length).toBe(i);
		}
		
		expect(agente.getMotoresPersiana().length).toBe(0);
	});
	
	test('añade un aireAcondicionado, ve que se ha añadido, se elimina y vemos que se ha eliminado con éxito', () => {
		let i = 0;
		
		for(let aire in aires) {
			agente.addAireAcondicionado(aire);
			++i;
			expect(agente.getAiresAcondicionados().length).toBe(i);
		}
		
		expect(agente.getAiresAcondicionados().length).toBe(aires.length);
		
		for(let aire in aires) {
			agente.removeAireAcondicionado(aire);
			--i;
			expect(agente.getAiresAcondicionados().length).toBe(i);
		}
		
		expect(agente.getAiresAcondicionados().length).toBe(0);
	});
});
