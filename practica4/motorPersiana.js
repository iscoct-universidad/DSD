"use strict";
exports.__esModule = true;
var MotorPersiana = /** @class */ (function () {
    function MotorPersiana(persianaAbierta) {
        if (persianaAbierta === void 0) { persianaAbierta = false; }
        this.persianaAbierta = persianaAbierta;
        this.abierta = persianaAbierta;
    }
    MotorPersiana.prototype.realizarAccion = function () {
        this.abierta = !this.abierta;
    };
    MotorPersiana.prototype.setAccion = function (accion) {
        this.abierta = accion;
    };
    MotorPersiana.prototype.realizandoAccion = function () {
        return this.abierta;
    };
    return MotorPersiana;
}());
exports["default"] = MotorPersiana;
