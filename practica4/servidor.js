"use strict"

/*
	Se usa el framework express de nodejs para que sea más legible
	el código a la hora de recibir las peticiones al servidor
*/

var app = require('express')();
var fs = require('fs');
var http = require('http').Server(app);
var io = require('socket.io')(http);
var MongoClient = require('mongodb').MongoClient;
var MongoServer = require('mongodb').Server;
const bodyParser = require('body-parser');

/*
	Para tomar que los valores que lleguen desde una petición HTTP
	con método POST se pueda acceder de manera, <respuesta>.body.<parametroPost>
*/

app.use(bodyParser.urlencoded({ extended: false }));

/*
	Función que devuelve el fichero actualizarEstado.html
*/

function devolverActualizarEstado(req, res) {
	fs.readFile('./actualizarEstado.html', (err, data) => {
		if(err)
			throw err;
		
		// Aquí se produce la modificación de la cabecera y el resultado de la petición
		
		res.setHeader('Content-Type', 'text/html');
		res.end(data);
	});
}

/*
	Función que devuelve el fichero index.html
*/

function devolverIndex(req, res) {
	fs.readFile('./index.html', (err, data) => {
		if(err)
			throw err;
			
		// Igual que la función anterior
		
		res.setHeader('Content-Type', 'text/html');
		res.end(data);
	});
}

/*
	1. Creo la base de datos de MongoServer
	2. Creo el cliente
	3. Conecto con la base de datos
	4. Creo la colección
*/

// Creo el cliente y servidor de mongo

let mongoClient = new MongoClient(new MongoServer('localhost', 27017));
let url = 'mongodb://localhost:27017';
let dbName = 'myproject';
let nombreColeccion = 'eventos';
let collection = null;

// Me conecto al conjunto de bases de datos

mongoClient.connect((err, db) => {
	if(err)
		throw err;
		
	// Me conecto a la base de datos myproject -- si no existe la crea
	
	let dataBase = db.db(dbName);
	
	// Creamos la colección de la base de datos -- si ya existe no se hace nada
	
	dataBase.createCollection(nombreColeccion, (err, res) => {
		if(err)
			throw err;
		
		// Recojemos la referencia de la colección para tomarla fuera de la función
		
		collection = res;
		
		/*
			Si ya existía la base de datos myproject elimino todo su contenido
				la quiero nueva
		*/
		
		res.deleteMany({}, (err, obj) => { });
		
		console.log("Collection created!");
	});
});

/*
	app es el middleware que tomará las peticiones de los métodos 
	http (método .get por ejemplo) concreto	para las rutas concretas 
	pasadas como primer parámetro ('/' por ejemplo)
	
	Los argumentos req y res es igual que sino utilizaramos express
	
	'/' ruta que devuelve la página index.html
*/

app.get('/', (req, res) => {
	console.log("Se ha recibido una petición a /\n\n");
	
	devolverIndex(req, res);
});

/*
	'/actualizarEstado': Ruta que devuelve la página html para actualizar el estado
	de la luminosidad y la temperatura (actualizarEstado.html)
*/

app.get('/actualizarEstado', (req, res) => {
	console.log("Se ha recibido una petición a /actualizarEstado - get\n\n");
	
	devolverActualizarEstado(req, res);
});

/*
	'/actualizarEstado' con el método POST: modifica la temperatura y la luminosidad
	que tienen los objetos creados en el servidor y registra el evento en la colección
	creada en mongoDB
	
	Los argumentos con el método POST se pueden acceder con res.body.<argumento>
	
	Se reciben luminosidad y temperatura
*/

app.post('/actualizarEstado', (req, res) => {
	console.log("Se ha recibido una petición a /actualizarEstado - post\n\n");
	console.log("Luminosidad: " + req.body.luminosidad);
	console.log("Temperatura: " + req.body.temperatura);
	
	/*
		Registramos la fecha en la base de datos de mongo con el formato:
			<dia>-<mes>-<año>
	*/
	
	let fecha = new Date();
	let formatoFecha = fecha.getDay() + "-" + fecha.getMonth() + "-" + fecha.getFullYear();
	
	/*
		Si en el argumento de luminosidad se ha introducido algún valor
			Actualizamos el estado del objeto luminosidad que tiene el servidor
			Insertamos en la colección creada el evento ocasionado con el formato
			
				{
					evento: {
						luminosidad: x
						fecha: y
					}
				}
					
	*/
	
	if(req.body.luminosidad != "") {
		luminosidad.actualizarEstado(req.body.luminosidad);
	
		collection.insertOne({ evento: {
				luminosidad: luminosidad.getEstado().toString(),
				fecha: formatoFecha
			}
		});
		
		agente.realizarAcciones(luminosidad);
	}
	
	/*
		Si en el argumento de la temperatura se ha introducido algún valor
			Actualizamos el estado del objeto temperatura que tiene el servidor
			Insertamos en la colección creada el evento ocasionado con el formato
			
				{
					evento: {
						temperatura: x
						fecha: y
					}
				}
	*/
	
	if(req.body.temperatura != "") {
		temperatura.actualizarEstado(req.body.temperatura);
	
		collection.insertOne({ evento: {
			temperatura: temperatura.getEstado().toString(),
			fecha: formatoFecha
		}}, (err, res) => {
			if(err)
				throw err;
			
			console.log("Insertada la tupla evento con éxito");
		});
		
		agente.realizarAcciones(temperatura);
	}
	
	/*
		Se les notifica a todos los clientes que los valores de la luminosidad
		y la temperatura han cambiado
	*/
	
	let persianaAbierta = (motorPersiana.realizandoAccion()) ? "abierta" : "cerrada";
	let aireEncendido = (aireAcondicionado.realizandoAccion()) ? "encendido" :
		"apagado";
		
	console.log("Se recibe el mensaje de info");
	io.emit('info', luminosidad.getEstado(), temperatura.getEstado(),
		persianaAbierta, aireEncendido);
		
	devolverActualizarEstado(req, res);
});

/*
	Ruta que devuelve todos los eventos registramos en el servidor de modificaciones
	de temperaturas o luminosidad
*/

app.get('/getEventos', (req, res) => {
	collection.find({}).toArray((err, docs) => {
		let respuesta = '<!DOCTYPE html><html><head><meta charset=\"utf-8\"></head> \
			<body><a href="./">Volver</a></br></br>';
			
		for(const tupla of docs) {
			let valido = false;
			let temperatura = tupla["evento"].temperatura;
			let luminosidad = tupla["evento"].luminosidad;
			
			if(temperatura != undefined && temperatura != null) {
				respuesta += `Modificación de temperatura: ${temperatura}` + "</br>";
				valido = true;
			} else if(luminosidad != undefined && luminosidad != null) {
				respuesta += `Modificación de luminosidad: ${luminosidad}` + "</br>";
				valido = true;
			}
			
			if(valido)
				respuesta += `Fecha de la modificación: ${tupla["evento"].fecha}` + "</br></br>";
		}
		
		respuesta += "</br></body></html>";
		
		res.end(respuesta);
	});
});

/*
	Modifica manualmente los valores de los actuadores
*/

app.post('/setActuadores', (req, res) => {
	console.log("Se ha recibido una petición a /setActuadores - post\n\n");
	console.log("Persiana: " + req.body.persiana);
	console.log("Aire acondicionado: " + req.body.aireAcondicionado);
	
	let abierta = (req.body.persiana == "abierta") ? true : false;
	let encendido = (req.body.aireAcondicionado == "encendido") ? true : false;
	
	motorPersiana.setAccion(abierta);
	aireAcondicionado.setAccion(encendido);
	
	devolverIndex(req, res);
});

/*
	Ruta que devuelve cualquier fichero que se pide, esto se hace para los .css
*/

app.get('/*', (req, res) => {
	console.log("Se ha recibido una petición a " + req.path + "\n\n");
	
	fs.readFile("." + req.path, (err, data) => {
		console.log("Path escogido: " + req.path);
		
		res.end(data);
	});
});

/*
	Abrimos el servidor en el puerto 8080
*/

http.listen(8080, () => {
	console.log("Se ha iniciado el servidor en el puerto 8080\n\n");
});

/*
	Creación de los sensores, los actuadores y el agente
*/

const Luminosidad = require('./luminosidad').default;
const Temperatura = require('./temperatura').default;
const MotorPersiana = require('./motorPersiana').default;
const AireAcondicionado = require('./aireAcondicionado').default;
const Agente = require('./agente').default;

var luminosidad = new Luminosidad(20);
var temperatura = new Temperatura(20);
var motorPersiana = new MotorPersiana();
var aireAcondicionado = new AireAcondicionado();
var agente = new Agente();

agente.addMotorPersiana(motorPersiana);
agente.addAireAcondicionado(aireAcondicionado);

/*
	Creación de la comunicación con los WebSockets
	
	Cada vez que una persona se conecta se mira el valor de los actuadores
	
	Si se recibe un mensaje tipo 'infoActuadores'
		Le devolvemos un mensaje del mismo pero con los argumentos de los valores
		de la persiana y del aire acondicionado
		
	Si se recibe un mensaje tipo 'info'
		Le devolvemos un mensaje del mismo tipo pero con los argumentos de los valores
		de la luminosidad, temperatura, persiana y aire acondicionado
*/

io.sockets.on('connection', (socket) => {
	console.log("Se ha inscrito un nuevo cliente");
	
	socket.on('infoActuadores', () => {
		let abierta = (motorPersiana.realizandoAccion()) ? "abierta" : "cerrada";
		let encendido = (aireAcondicionado.realizandoAccion()) ? "encendido" : "apagado";
		
		io.emit('infoActuadores', abierta, encendido);
	});
	
	socket.on('info', () => {
		let persianaAbierta = (motorPersiana.realizandoAccion()) ? "abierta" : "cerrada";
		let aireEncendido = (aireAcondicionado.realizandoAccion()) ? "encendido" :
			"apagado";
			
		console.log("Se recibe el mensaje de info");
		io.emit('info', luminosidad.getEstado(), temperatura.getEstado(),
			persianaAbierta, aireEncendido);
	});
});
