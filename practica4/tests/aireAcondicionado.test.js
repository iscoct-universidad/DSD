const AireAcondicionado = require('../aireAcondicionado').default;

/*
	Debe haberse compilado primero aireAcondicionado.ts con tsc
		tsc aireAcondicionado.ts
*/

test('aireAcondicionado constructor', () => {
	const aire = new AireAcondicionado();
	
	expect(aire.realizandoAccion()).toBeFalsy();
});

test('aireAcondicionado constructor con argumento = true', () => {
	const aire = new AireAcondicionado(true);
	
	expect(aire.realizandoAccion()).toBeTruthy();
});

test('aireAcondicionado constructor con argumento = false', () => {
	const aire = new AireAcondicionado(false);
	
	expect(aire.realizandoAccion()).toBeFalsy();
});

test('aireAcondicionado cambio accion', () => {
	let aire = new AireAcondicionado();
	
	aire.realizarAccion();
	
	expect(aire.realizandoAccion()).toBeTruthy();
});

test('aireAcondicionado cambio manual accion', () => {
	let aire = new AireAcondicionado();
	
	aire.setAccion(true);
	
	expect(aire.realizandoAccion()).toBeTruthy();
});
