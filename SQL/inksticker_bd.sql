DROP DATABASE IF EXISTS inksticker_db;

CREATE DATABASE inksticker_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE inksticker_db;

-- ==========================================================
-- 1. ADMINISTRADORES, ROLES Y BITACORA (HU-02, HU-04)
-- ==========================================================
CREATE TABLE administradores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE administrador_roles (
    administrador_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (administrador_id, rol_id),
    CONSTRAINT fk_admin_roles_admin FOREIGN KEY (administrador_id) REFERENCES administradores(id) ON DELETE CASCADE,
    CONSTRAINT fk_admin_roles_rol FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE bitacora (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    administrador_id BIGINT NOT NULL,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    accion TEXT NOT NULL,
    CONSTRAINT fk_bitacora_admin FOREIGN KEY (administrador_id) REFERENCES administradores(id) ON DELETE CASCADE
);

-- ==========================================================
-- 2. CATALOGO: CATEGORIAS Y PRODUCTOS (HU-05..08, HU-16)
-- ==========================================================
CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(500),
    imagen VARCHAR(255),
    activa BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    imagen VARCHAR(255),
    material VARCHAR(100),
    medidas VARCHAR(100),
    etiqueta VARCHAR(100),
    durabilidad VARCHAR(100),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    categoria_id BIGINT NOT NULL,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

-- ==========================================================
-- 3. CLIENTES Y ARCHIVOS SUBIDOS (HU-01, HU-09, HU-18)
-- ==========================================================
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    telefono VARCHAR(30),
    provincia VARCHAR(60),
    canton VARCHAR(80),
    distrito VARCHAR(80),
    direccion VARCHAR(500)
);

CREATE TABLE archivos_subidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    nombre_original VARCHAR(255),
    tipo_contenido VARCHAR(100),
    tamano_bytes BIGINT,
    path VARCHAR(500) NOT NULL,
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT,
    CONSTRAINT fk_archivo_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- ==========================================================
-- 4. CARRITOS Y PEDIDOS (HU-13, HU-14, HU-15, HU-17, HU-18)
-- ==========================================================
CREATE TABLE carritos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    CONSTRAINT fk_carrito_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE item_carritos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    carrito_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    medidas VARCHAR(100),
    acabado VARCHAR(50),
    archivo_id BIGINT,
    CONSTRAINT fk_item_carrito_carrito FOREIGN KEY (carrito_id) REFERENCES carritos(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_carrito_producto FOREIGN KEY (producto_id) REFERENCES productos(id),
    CONSTRAINT fk_item_carrito_archivo FOREIGN KEY (archivo_id) REFERENCES archivos_subidos(id) ON DELETE SET NULL
);

CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_orden VARCHAR(30) NOT NULL UNIQUE,
    usuario_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(50) NOT NULL DEFAULT 'Pendiente',
    nombre_receptor VARCHAR(120) NOT NULL,
    telefono_contacto VARCHAR(30) NOT NULL,
    provincia VARCHAR(60),
    canton VARCHAR(80),
    distrito VARCHAR(80),
    direccion_entrega VARCHAR(500) NOT NULL,
    metodo_pago VARCHAR(60) NOT NULL,
    costo_envio DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    total DECIMAL(10, 2) NOT NULL,
    notas VARCHAR(500),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE item_pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    medidas VARCHAR(100),
    acabado VARCHAR(50),
    archivo_id BIGINT,
    CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_pedido_producto FOREIGN KEY (producto_id) REFERENCES productos(id),
    CONSTRAINT fk_item_pedido_archivo FOREIGN KEY (archivo_id) REFERENCES archivos_subidos(id) ON DELETE SET NULL
);

-- ==========================================================
-- 5. CONFIGURACION DEL ESTUDIO Y BANNERS (HU-11, HU-12, HU-19, HU-20)
-- ==========================================================
CREATE TABLE configuracion_estudio (
    id BIGINT PRIMARY KEY,
    nombre_estudio VARCHAR(120) NOT NULL,
    whatsapp VARCHAR(30) NOT NULL,
    mensaje_whatsapp VARCHAR(300),
    correo_contacto VARCHAR(150),
    direccion_fisica VARCHAR(300),
    mapa_embed_url TEXT,
    horario_atencion VARCHAR(255),
    instagram_url VARCHAR(255),
    facebook_url VARCHAR(255),
    tiktok_url VARCHAR(255)
);

CREATE TABLE banners (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(30) NOT NULL DEFAULT 'HERO',
    titulo VARCHAR(150) NOT NULL,
    subtitulo VARCHAR(300),
    texto_boton VARCHAR(60),
    url_boton VARCHAR(255),
    imagen VARCHAR(255) NOT NULL,
    orden INT NOT NULL DEFAULT 1,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- ==========================================================
-- 6. Otorgar permisos de administrador a una cuneta creada como usuario
-- ==========================================================
UPDATE usuarios
SET rol = 'ADMIN'
WHERE correo = 'correo del usuario aqui';