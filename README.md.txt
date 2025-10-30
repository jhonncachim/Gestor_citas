Sistema Gestor de Citas

Este proyecto es un sistema desarrollado en Java que permite gestionar citas entre clientes y profesionales. Su objetivo es facilitar el registro, programación y control de citas, además del manejo de servicios y la autenticación de usuarios. El sistema funciona desde consola y guarda la información en archivos para conservar los datos entre ejecuciones.

Objetivo del sistema

El propósito principal del sistema es administrar las citas de forma organizada, permitiendo registrar clientes, profesionales y servicios, así como crear, consultar y almacenar citas. También cuenta con un módulo de autenticación para controlar el acceso al sistema.

Clases principales

Persona:
Clase base que contiene los atributos comunes como id, nombre, apellido y teléfono. Es la superclase de Cliente y Profesional.

Cliente:
Representa a los usuarios que solicitan servicios. Hereda de Persona y agrega el atributo correo electrónico.

Profesional:
Representa a los trabajadores o especialistas que prestan los servicios. Hereda de Persona e incluye la especialidad del profesional.

Servicio:
Define los servicios disponibles en el sistema. Cada servicio tiene un identificador, un nombre y un precio.

Cita:
Asocia un cliente, un profesional y un servicio con una fecha y hora determinada. Permite visualizar la información completa de la cita.

Agenda:
Es la clase principal de gestión. Contiene listas de clientes, profesionales, servicios y citas. Desde esta clase se pueden agregar, buscar y mostrar los diferentes elementos. La relación principal es de agregación, ya que la Agenda reúne objetos de otras clases sin depender completamente de ellos.

Autenticacion:
Maneja el inicio de sesión de los usuarios. Permite validar si un cliente o profesional tiene acceso al sistema.

GestorArchivos:
Se encarga de guardar y cargar la información de las citas en archivos CSV. Facilita la persistencia de datos y evita que se pierdan al cerrar el programa.

Gestor_citas (Main):
Es la clase principal del programa. Contiene el menú desde el cual el usuario puede autenticarse, registrar nuevos clientes o profesionales, crear citas, mostrarlas, guardarlas o cargarlas desde archivos.

Funcionamiento general

Al ejecutar el programa se muestra un menú principal con las opciones disponibles.

El usuario puede autenticarse como cliente o profesional.

La Agenda permite registrar clientes, profesionales y servicios.

El usuario puede crear nuevas citas seleccionando cliente, profesional, servicio y fecha.

Las citas pueden guardarse en un archivo o cargarse posteriormente mediante el GestorArchivos.

Relaciones principales del sistema

Cliente y Profesional heredan de Persona.

Agenda tiene listas de Cliente, Profesional, Servicio y Cita (agregación).

Cita se asocia con un Cliente, un Profesional y un Servicio.

Gestor_citas utiliza Agenda, Autenticacion y GestorArchivos para la ejecución general del sistema.

Ejemplo básico de uso

Ejecutar el programa.

Iniciar sesión como cliente o profesional.

Crear o consultar citas desde la agenda.

Guardar las citas en un archivo para conservar los datos.

Cargar las citas nuevamente cuando se vuelva a abrir el sistema.

Hecho por :
Jhonatan Duvan Cachimbo Moreno
