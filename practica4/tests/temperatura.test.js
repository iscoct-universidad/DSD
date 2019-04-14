const Temperatura = require('../temperatura').default;

test('temperatura constructor', () => {
	const temperatura = new Temperatura(5);
	
	expect(temperatura.getEstado()).toBe(5);
});

test('temperatura actualizarEstado', () => {
	let temperatura = new Temperatura(-1);
	
	expect(temperatura.getEstado()).toBe(-1);
	
	temperatura.actualizarEstado(5);
	
	expect(temperatura.getEstado()).toBe(5);
});
