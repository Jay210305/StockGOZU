-- Crear la base de datos si no existe
-- SOURCE c:\Users\User\Documents\GitHub\StockGOZU\inventario\src\test\java\datos.sql
DROP DATABASE IF EXISTS inventario_db;
CREATE DATABASE inventario_db;
USE inventario_db;
-- CREATE DATABASE IF NOT EXISTS inventario_db;
-- USE inventario_db;

-- Crear tabla `sucursal`
CREATE TABLE IF NOT EXISTS sucursal (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  ubicacion VARCHAR(255) DEFAULT NULL,
  creado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Crear tabla `producto`
CREATE TABLE IF NOT EXISTS producto (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(150) NOT NULL,
  descripcion TEXT,
  codigo VARCHAR(50) NOT NULL,
  categoria VARCHAR(100) DEFAULT NULL,
  unidad_medida ENUM('kg','litro','unidad','metro','caja','otro') NOT NULL DEFAULT 'unidad',
  precio_compra DECIMAL(10,2) NOT NULL,
  precio_venta DECIMAL(10,2) NOT NULL,
  creado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Crear tabla `inventario`
CREATE TABLE IF NOT EXISTS inventario (
  sucursal_id INT NOT NULL,
  producto_id BIGINT NOT NULL,
  cantidad INT NOT NULL DEFAULT 0,
  stock_minimo INT NOT NULL DEFAULT 0,
  actualizado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (sucursal_id, producto_id),
  KEY producto_id (producto_id),
  CONSTRAINT fk_inventario_sucursal FOREIGN KEY (sucursal_id) REFERENCES sucursal (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_inventario_producto FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Crear tabla `usuario`
CREATE TABLE IF NOT EXISTS usuario (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  username VARCHAR(50) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  rol ENUM('ADMIN','OPERADOR') NOT NULL DEFAULT 'OPERADOR',
  creado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Crear tabla `movimiento_inventario`
CREATE TABLE IF NOT EXISTS movimiento_inventario (
  id BIGINT NOT NULL AUTO_INCREMENT,
  sucursal_id INT NOT NULL,
  producto_id BIGINT NOT NULL,
  usuario_id INT NOT NULL,
  tipo ENUM('INGRESO','SALIDA') NOT NULL,
  cantidad INT NOT NULL,
  precio_compra DECIMAL(10,2) NOT NULL,
  precio_venta DECIMAL(10,2) NOT NULL,
  fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  comentario VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY producto_id (producto_id),
  KEY usuario_id (usuario_id),
  CONSTRAINT fk_movimiento_sucursal FOREIGN KEY (sucursal_id) REFERENCES sucursal (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_movimiento_producto FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_movimiento_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar datos en `sucursal`
INSERT INTO sucursal (id, nombre, ubicacion) VALUES (1, 'Sucursal Central', 'Ciudad Principal');
INSERT INTO sucursal (id, nombre, ubicacion) VALUES (2, 'Sucursal Norte', 'Ciudad Norte');
INSERT INTO sucursal (id, nombre, ubicacion) VALUES (3, 'Sucursal Sur', 'Ciudad Sur');
INSERT INTO sucursal (id, nombre, ubicacion) VALUES (4, 'Sucursal Este', 'Ciudad Este');
INSERT INTO sucursal (id, nombre, ubicacion) VALUES (5, 'Sucursal Oeste', 'Ciudad Oeste');

-- Insertar datos en `producto`
INSERT INTO producto (id, nombre, descripcion, codigo, categoria, unidad_medida, precio_compra, precio_venta) 
VALUES 
(1, 'Producto A', 'Descripción A', 'PROD001', 'Categoría 1', 'unidad', 10.0, 15.0),
(2, 'Producto B', 'Descripción B', 'PROD002', 'Categoría 2', 'kg', 20.0, 30.0),
(3, 'Producto C', 'Descripción C', 'PROD003', 'Categoría 3', 'litro', 5.0, 8.0),
(4, 'Producto D', 'Descripción D', 'PROD004', 'Categoría 1', 'metro', 12.0, 18.0),
(5, 'Producto E', 'Descripción E', 'PROD005', 'Categoría 2', 'caja', 25.0, 40.0),
(6, 'Producto F', 'Descripción F', 'PROD006', 'Categoría 3', 'otro', 8.5, 13.5),
(7, 'Producto G', 'Descripción G', 'PROD007', 'Categoría 1', 'unidad', 3.0, 5.0),
(8, 'Producto H', 'Descripción H', 'PROD008', 'Categoría 2', 'kg', 18.0, 27.0);

-- Insertar datos en `inventario`
INSERT INTO inventario (sucursal_id, producto_id, cantidad, stock_minimo) 
VALUES 
(1, 1, 100, 10),
(2, 2, 50, 5),
(1, 3, 60, 5),
(2, 4, 40, 10),
(3, 5, 70, 15),
(4, 6, 20, 5),
(5, 7, 90, 10),
(1, 8, 55, 7);

-- Insertar datos en `usuario`
INSERT INTO usuario (id, nombre, username, password_hash, rol) 
VALUES 
(1, 'Admin', 'admin', 'hashed_password', 'ADMIN'),
(2, 'Operador', 'operador', 'hashed_password', 'OPERADOR'),
(3, 'Carlos Pérez', 'cperez', 'hashed_password', 'OPERADOR'),
(4, 'Laura Gómez', 'lgomez', 'hashed_password', 'OPERADOR'),
(5, 'Marta Ruiz', 'mruiz', 'hashed_password', 'ADMIN');

-- Insertar datos en `movimiento_inventario`
INSERT INTO movimiento_inventario (sucursal_id, producto_id, usuario_id, tipo, cantidad, precio_compra, precio_venta, comentario) 
VALUES (1, 1, 1, 'INGRESO', 50, 10.0, 15.0, 'Ingreso inicial'),
(2, 2, 2, 'SALIDA', 10, 20.0, 30.0, 'Venta inicial'),
(3, 5, 3, 'INGRESO', 70, 25.0, 40.0, 'Nuevo ingreso producto E'),
(4, 6, 4, 'INGRESO', 20, 8.5, 13.5, 'Reabastecimiento producto F'),
(5, 7, 5, 'INGRESO', 90, 3.0, 5.0, 'Ingreso masivo producto G'),
(1, 8, 1, 'SALIDA', 10, 18.0, 27.0, 'Venta producto H'),
(2, 3, 2, 'SALIDA', 15, 5.0, 8.0, 'Consumo interno producto C'),
(3, 4, 3, 'SALIDA', 5, 12.0, 18.0, 'Devolución producto D');