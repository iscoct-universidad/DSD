"use strict";
exports.__esModule = true;
var Temperatura = /** @class */ (function () {
    function Temperatura(temperatura) {
        this.temperatura = temperatura;
    }
    Temperatura.prototype.actualizarEstado = function (estado) {
        this.temperatura = estado;
    };
    Temperatura.prototype.getEstado = function () {
        return this.temperatura;
    };
    return Temperatura;
}());
exports["default"] = Temperatura;
