# InkSticker

## Descripción

InkSticker es una aplicación web desarrollada con Java y Spring Boot. Este repositorio contiene la versión base del proyecto para que los integrantes del equipo puedan descargarlo, configurarlo y continuar su desarrollo de forma colaborativa.

## Tecnologías

- Java (utilizar la versión compatible indicada en `pom.xml`).
- Spring Boot.
- Maven.
- MySQL.
- Thymeleaf.
- Apache NetBeans.

## Requisitos previos

Antes de ejecutar el proyecto, cada integrante debe tener instalado:

- Un JDK compatible con la versión configurada en `pom.xml`.
- Apache NetBeans (o un IDE compatible con Maven).
- **MySQL Server**, para ejecutar el motor de base de datos.
- MySQL Workbench (opcional, como herramienta gráfica para administrar MySQL).

> Importante: MySQL Workbench por sí solo no sustituye a MySQL Server. Workbench es una herramienta para administrar el servidor; el servicio MySQL Server debe estar instalado y en ejecución.

## Obtener el proyecto

Clonar el repositorio:

```bash
git clone https://github.com/JosePabl0/Proyecto-Viernes.git
```

Entrar en la carpeta descargada:

```bash
cd Proyecto-Viernes
```

También se puede descargar el repositorio desde GitHub mediante **Code > Download ZIP** y extraer el archivo.

## Configurar MySQL

1. Iniciar MySQL Server.
2. Crear en MySQL la base de datos que utiliza el proyecto, si todavía no existe.
3. Abrir el archivo `src/main/resources/application.properties`.
4. Configurar la URL de conexión, el nombre de usuario y la contraseña correspondientes a la instalación local de cada integrante.

Ejemplo de configuración (los valores son ilustrativos; conservar las demás propiedades que ya tenga el proyecto):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/NOMBRE_BASE_DATOS
spring.datasource.username=USUARIO_MYSQL
spring.datasource.password=CONTRASENA_MYSQL
```

Cada integrante debe sustituir los valores de ejemplo por los datos de su propio entorno. **No se deben subir contraseñas reales ni otras credenciales al repositorio.**

## Abrir y ejecutar en Apache NetBeans

1. Abrir Apache NetBeans.
2. Seleccionar **File > Open Project**.
3. Buscar y seleccionar la carpeta descargada del proyecto.
4. Esperar a que Maven resuelva y descargue las dependencias.
5. Ejecutar la aplicación desde la clase principal `com.inksticker.InkStickerApplication`.

También se puede ejecutar desde una terminal abierta en la carpeta del proyecto:

```bash
mvn spring-boot:run
```

Cuando la aplicación haya iniciado, abrir en el navegador:

`http://localhost:8080/`

Si el puerto está configurado de otra manera en `application.properties`, utilizar el puerto indicado allí.

## Trabajo colaborativo con Git

Se recomienda trabajar en ramas individuales y evitar realizar cambios directamente sobre `main`.

Para crear una rama nueva:

```bash
git switch -c nombre-de-tu-rama
```

Después de realizar cambios, se pueden guardar y publicar con:

```bash
git add .
git commit -m "Descripción breve de los cambios"
git push -u origin nombre-de-tu-rama
```

Los cambios se pueden proponer al equipo mediante un Pull Request en GitHub para su revisión e integración.

## Notas

- El archivo `pom.xml` contiene las dependencias y la configuración Maven compartida por el equipo. No debe modificarse sin coordinarlo con los demás integrantes.
- La carpeta `target/` se genera automáticamente al compilar o ejecutar el proyecto y no es necesario subirla al repositorio.
- Cada integrante debe configurar su propia conexión local a MySQL.
- No se deben publicar contraseñas, tokens ni credenciales privadas.
