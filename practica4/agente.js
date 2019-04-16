"use strict";
exports.__esModule = true;
var luminosidad_1 = require("./luminosidad");
var temperatura_1 = require("./temperatura");
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
        this.getMotoresPersiana().splice(pos, 1);
    };
    Agente.prototype.removeAireAcondicionado = function (aire) {
        var pos = this.getAiresAcondicionados().indexOf(aire);
        this.getAiresAcondicionados().splice(pos, 1);
    };
    Agente.prototype.getMotoresPersiana = function () {
        return this.motoresPersiana;
    };
    Agente.prototype.getAiresAcondicionados = function () {
        return this.airesAcondicionados;
    };
    Agente.prototype.realizarAcciones = function (sensor) {
        if (sensor instanceof luminosidad_1["default"]) {
            for (var _i = 0, _a = this.getMotoresPersiana(); _i < _a.length; _i++) {
                var motor = _a[_i];
                if (sensor.getEstado() > this.getMaxLuminosidad())
                    motor.setAccion(true);
            }
        }
        else if (sensor instanceof temperatura_1["default"]) {
            for (var _b = 0, _c = this.getAiresAcondicionados(); _b < _c.length; _b++) {
                var aire = _c[_b];
                if (sensor.getEstado() > this.getMaxTemperatura())
                    aire.setAccion(true);
            }
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
