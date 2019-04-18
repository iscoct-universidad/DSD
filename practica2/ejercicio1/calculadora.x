const TAM_OP = 1;
typedef string operador<TAM_OP>;

struct operacionBinaria {
    int entero1;
    int entero2;
    operador op;
};

program CALCULADORA {
    version PRIMERA {
        int OPERACION (operacionBinaria) = 1;
    } = 1;
} = 0x20000001;
