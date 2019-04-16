"use strict"

var app = require('express')();
var fs = require('fs');
var http = require('http').Server(app);
var io = require('socket.io')(http);
var MongoClient = require('mongodb').MongoClient;
var MongoServer = require('mongodb').Server;
const bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: false }));

function devolverActualizarEstado(req, res) {
	fs.readFile('./actualizarEstado.html', (err, data) => {
		if(err)
			throw err;
		
		res.setHeader('Content-Type', 'text/html');
		res.end(data);
	});
}

function devolverIndex(req, res) {
	fs.readFile('./index.html', (err, data) => {
		if(err)
			throw err;
			
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

let mongoClient = new MongoClient(new MongoServer('localhost', 27017));
let url = 'mongodb://localhost:27017';
let dbName = 'myproject';
let nombreColeccion = 'eventos';
let collection = null;

mongoClient.connect((err, db) => {
	if(err)
		throw err;
		
	let dataBase = db.db(dbName);
	
	dataBase.createCollection(nombreColeccion, (err, res) => {
		if(err)
			throw err;
		
		collection = res;
		res.deleteMany({}, (err, obj) => { });
		
		console.log("Collection created!");
	});
});

app.get('/', (req, res) => {
	console.log("Se ha recibido una petición a /\n\n");
	
	devolverIndex(req, res);
});

app.get('/actualizarEstado', (req, res) => {
	console.log("Se ha recibido una petición a /actualizarEstado - get\n\n");
	
	devolverActualizarEstado(req, res);
});

app.post('/actualizarEstado', (req, res) => {
	console.log("Se ha recibido una petición a /actualizarEstado - post\n\n");
	console.log("Luminosidad: " + req.body.luminosidad);
	console.log("Temperatura: " + req.body.temperatura);
	
	console.log("Luminosidad antes de modificarla: " + luminosidad.getEstado());
	luminosidad.actualizarEstado(req.body.luminosidad);
	console.log("Luminosidad después de modificarla: " + luminosidad.getEstado());
	console.log("Temperatura antes de modificarla: " + temperatura.getEstado());
	temperatura.actualizarEstado(req.body.temperatura);
	console.log("Temperatura después de modificarla: " + temperatura.getEstado());
	
	let fecha = new Date();
	let formatoFecha = fecha.getDay() + "-" + fecha.getMonth() + "-" + fecha.getFullYear();
	
	collection.insertOne({ evento: {
			luminosidad: luminosidad.getEstado().toString(),
			fecha: formatoFecha
		}
	});
	
	agente.realizarAcciones(luminosidad);
	
	collection.insertOne({ evento: {
		temperatura: temperatura.getEstado().toString(),
		fecha: formatoFecha
	}}, (err, res) => {
		if(err)
			throw err;
		
		console.log("Insertada la tupla evento con éxito");
	});
	
	agente.realizarAcciones(temperatura);
	
	devolverActualizarEstado(req, res);
});

app.get('/getEventos', (req, res) => {
	collection.find({}).toArray((err, docs) => {
		let respuesta = "";
		
		respuesta = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"></head> \
			<body>";
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

app.post('/setActuadores', (req, res) => {
	console.log("Se ha recibido una petición a /setActuadores - post\n\n");
	console.log("Persiana: " + req.body.persiana);
	console.log("Aire acondicionado: " + req.body.aireAcondicionado);
	
	let abierta = (req.body.persiana == "abierta") ? true : false;
	let encendido = (req.body.aireAcondicionado = "encendido") ? true : false;
	
	motorPersiana.setAccion(abierta);
	aireAcondicionado.setAccion(encendido);
	
	devolverIndex(req, res);
});

app.get('/*', (req, res) => {
	console.log("Se ha recibido una petición a " + req.path + "\n\n");
	
	fs.readFile("." + req.path, (err, data) => {
		console.log("Path escogido: " + req.path);
		
		res.end(data);
	});
});

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
*/

io.sockets.on('connection', (socket) => {
	let abierta = (motorPersiana.realizandoAccion()) ? "abierta" : "cerrada";
	let encendido = (aireAcondicionado.realizandoAccion()) ? "encendido" : "apagado";
	
	console.log("Se ha inscrito un nuevo cliente");
	
	socket.on('infoActuadores', () => {
		socket.emit('infoActuadores', abierta, encendido);
	});
	
	socket.on('info', () => {
		let persianaAbierta = (motorPersiana.realizandoAccion()) ? "abierta" : "cerrada";
		let aireEncendido = (aireAcondicionado.realizandoAccion()) ? "encendido" :
			"apagado";
			
		console.log("Se recibe el mensaje de info");
		socket.emit('info', luminosidad.getEstado(), temperatura.getEstado(),
			persianaAbierta, aireEncendido);
	});
});
