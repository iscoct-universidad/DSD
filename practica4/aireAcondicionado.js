"use strict";
exports.__esModule = true;
var AireAcondicionado = /** @class */ (function () {
    function AireAcondicionado(persianaencendido) {
        if (persianaencendido === void 0) { persianaencendido = false; }
        this.persianaencendido = persianaencendido;
        this.encendido = persianaencendido;
    }
    AireAcondicionado.prototype.realizarAccion = function () {
        this.encendido = !this.encendido;
    };
    AireAcondicionado.prototype.setAccion = function (accion) {
        this.encendido = accion;
    };
    AireAcondicionado.prototype.realizandoAccion = function () {
        return this.encendido;
    };
    return AireAcondicionado;
}());
exports["default"] = AireAcondicionado;
