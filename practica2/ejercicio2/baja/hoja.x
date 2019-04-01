const MAX_NOMBRE = 256;
const MAX_IP = 64;

struct registro {
    string nombre<MAX_NOMBRE>;
    string ip<MAX_IP>;
};

program HOJA {
    version PRIMERA {
        string baja(registro) = 2;
    } = 1;
} = 0x20000001;
