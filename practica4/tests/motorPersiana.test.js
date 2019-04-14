const MotorPersiana = require('../motorPersiana').default;

/*
	Debe haberse compilado primero motorPersiana.ts con tsc
		tsc motorPersiana.ts
*/

test('motorPersiana constructor', () => {
	const persiana = new MotorPersiana();
	
	expect(persiana.realizandoAccion()).toBeFalsy();
});

test('motorPersiana constructor con argumento = true', () => {
	const persiana = new MotorPersiana(true);
	
	expect(persiana.realizandoAccion()).toBeTruthy();
});

test('motorPersiana constructor con argumento = false', () => {
	const persiana = new MotorPersiana(false);
	
	expect(persiana.realizandoAccion()).toBeFalsy();
});

test('motorPersiana cambio accion', () => {
	let persiana = new MotorPersiana();
	
	persiana.realizarAccion();
	
	expect(persiana.realizandoAccion()).toBeTruthy();
});

test('motorPersiana cambio manual accion', () => {
	let persiana = new MotorPersiana();
	
	persiana.setAccion(true);
	
	expect(persiana.realizandoAccion()).toBeTruthy();
});
