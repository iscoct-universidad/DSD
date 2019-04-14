"use strict";
exports.__esModule = true;
var Luminosidad = /** @class */ (function () {
    function Luminosidad(luminosidad) {
        this.luminosidad = luminosidad;
    }
    Luminosidad.prototype.actualizarEstado = function (estado) {
        this.luminosidad = estado;
    };
    Luminosidad.prototype.getEstado = function () {
        return this.luminosidad;
    };
    return Luminosidad;
}());
exports["default"] = Luminosidad;
