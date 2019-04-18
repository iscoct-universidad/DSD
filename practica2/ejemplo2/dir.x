const MAX= 255;
typedef string nametype<MAX>;
typedef struct namenode *namelist;

struct namenode {
    nametype name;
    namelist next;
}

union readdir_res switch (int errno) {
    case 0:
        namelist list;
    default:
        void;
};

program DIRPROG {
    version DRIVER {
        readdir_res READDIR(nametype) = 1;
    } = 1;
} = 0x20000155;