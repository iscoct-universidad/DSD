El ProgramaCliente que es el main del cliente permite cerrar la ventana que interactúa
con el programa en cualquier momento.

También permite registrar a un usuario, abrir en otra terminal otro programa Cliente,
autenticar al cliente donar y que se actualice el estado en el primer 
programa (tras hacer alguna operación).

Respeta la funcionalidad pedida en la solución del problema pero se ha optado
por que haya un servidor (Broker) que se encargue del balanceo de los usuarios
entre los distintos servidores.

No obstante, un usuario puede hacer todas las operaciones correspondientes tratanto
con un servidor cualquiera (habría que ejecutar: ./javac.sh ProgramaCliente localhost 1099 Servidor1 después de haber arrancado los servidores).

Hay 2 interfaces de usuarios, esto es porque hay una pública, que se utilizará
tanto en el Broker como por el ProgramaCliente, para obligar así a que todas
las operaciones se realicen llamando a los métodos del Servidor.
Esto es así porque sino podríamos una vez tenido la interfaz del usuario,
donar y que no se actualizara la cantidad total donada en el servidor en el que se 
encuentra el cliente (mejora la seguridad del programa entero).
La privada sólo es para que se utilice por la clase Servidor, que es la que contiene
todos los clientes (es una relación de composición por lo que si no hay servidores,
no hay clientes).

Se añade también la operación getNombreServidor para poder saber el nombre del servidor
en el que se encuentra el cliente y saber que se está realizando el balanceo de la carga
en condiciones.

Se adjunta una imagen con el diagrama de la solución realizada.

IMPORTANTE: En el diagrama no se incluye ciertas excepciones que he creado para 
tener mayor seguridad en el programa. Las excepciones son:

ExcepNoRegistrado: Se lanza cuando intentamos autenticarnos y no nos encontramos
	 registrados en el sistema.
ExcepNoDonado: Se lanza cuando intentamos ver la cuantía donada en el sistema entero
	y todavía no hemos donado nada en el sistema.
ExcepYaRegistrado: Se lanza cuando intentamos registrarnos y ya se encuentra el usuario
	registrado en el sistema.

El registry aunque se puede acceder y crear en cualquier registry y en cualquier pc
se utiliza localhost y el puerto 1099 (mirar el script ./servidores.sh para ver que 
se necesitan pasar los argumentos por consola para que el programa funcione
declarando que pc y puerto tenemos que escoger).

Pasos para ejecutar el programa:

1. Ejecutar: ./servidores.sh -> Es un fichero para arrancar el registry y los servidores

Arranca 3 servidores, Servidor1 y Servidor2 que tendrán los clientes en su haber
y Broker que es un tercer servidor que hace de servidor Proxy cuya única función
es hacer el balance de la carga entre los servidores que cuelguen de él.

Cabe destacar que un Broker al ser un servidor puede contener otros Brokers en su haber.


2. Ejecutar: ./javac.sh ProgramaCliente localhost 1099 Broker

El primer argumento del script es el nombre del programa que se debe ejecutar en Java,
el segundo argumentos es la ip (en este caso localhost) donde se encuentra el objeto,
el tercero es el puerto donde se encuentra el objeto y
el cuarto es el nombre del objeto.
