/*
	Interfaz que heredarán actuadores específicos para realizar las acciones
	ya sean de manera automática (realizarAccion -- siempre pasar de verdadero a falso
	y viceversa) o de manera manual (setAccion)
*/

export default interface Actuador {
	realizarAccion(): void;
	realizandoAccion(): boolean;
	setAccion(accion: boolean): void;
}
