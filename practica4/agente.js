"use strict";
exports.__esModule = true;
var Agente = /** @class */ (function () {
    function Agente() {
        this.MAX_LUMINOSIDAD = 50;
        this.MIN_LUMINOSIDAD = 20;
        this.MAX_TEMPERATURA = 30;
        this.MIN_TEMPERATURA = 10;
        this.motoresPersiana = [];
        this.airesAcondicionados = [];
    }
    Agente.prototype.addMotorPersiana = function (motor) {
        this.getMotoresPersiana().push(motor);
    };
    Agente.prototype.addAireAcondicionado = function (aire) {
        this.getAiresAcondicionados().push(aire);
    };
    Agente.prototype.removeMotorPersiana = function (motor) {
        var pos = this.getMotoresPersiana().indexOf(motor);
        this.getMotoresPersiana().splice(pos, pos + 1);
    };
    Agente.prototype.removeAireAcondicionado = function (aire) {
        var pos = this.getAiresAcondicionados().indexOf(aire);
        this.getAiresAcondicionados().splice(pos, pos + 1);
    };
    Agente.prototype.getMotoresPersiana = function () {
        return this.motoresPersiana;
    };
    Agente.prototype.getAiresAcondicionados = function () {
        return this.airesAcondicionados;
    };
    Agente.prototype.realizarAcciones = function (sensor) {
        if (sensor instanceof Luminosidad) {
            for (var motor in this.getMotoresPersiana())
                if (sensor.getEstado() > this.getMaxLuminosidad())
                    motor.setAccion(true);
        }
        else if (sensor instanceof Temperatura) {
            for (var aire in this.getAiresAcondicionados())
                if (sensor.getEstado() > this.getMaxTemperatura())
                    motor.setAccion(true);
        }
    };
    Agente.prototype.getMaxLuminosidad = function () {
        return this.MAX_LUMINOSIDAD;
    };
    Agente.prototype.getMinLuminosidad = function () {
        return this.MIN_LUMINOSIDAD;
    };
    Agente.prototype.getMaxTemperatura = function () {
        return this.MAX_TEMPERATURA;
    };
    Agente.prototype.getMinTemperatura = function () {
        return this.MIN_TEMPERATURA;
    };
    return Agente;
}());
exports["default"] = Agente;
