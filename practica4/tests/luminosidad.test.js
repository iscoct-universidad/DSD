const Luminosidad = require('../luminosidad').default;

test('luminosidad constructor', () => {
	const luz = new Luminosidad(5);
	
	expect(luz.getEstado()).toBe(5);
});

test('actualizarEstado', () => {
	let luz = new Luminosidad(-1);
	
	expect(luz.getEstado()).toBe(-1);
	
	luz.actualizarEstado(5);
	
	expect(luz.getEstado()).toBe(5);
});
