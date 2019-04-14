export default interface Actuador {
	realizarAccion(): void;
	realizandoAccion(): boolean;
	setAccion(accion: boolean): void;
}
