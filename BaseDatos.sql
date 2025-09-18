

CREATE DATABASE IF NOT EXISTS clientepersona;
USE clientepersona;


CREATE TABLE clientes (
                          cliente_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          genero VARCHAR(20) NOT NULL,
                          edad INT NOT NULL,
                          identificacion VARCHAR(50) NOT NULL UNIQUE,
                          direccion VARCHAR(255),
                          telefono VARCHAR(50),
                          password VARCHAR(255) NOT NULL,
                          estado BOOLEAN NOT NULL
);

CREATE DATABASE IF NOT EXISTS cuentamovimiento;
USE cuentamovimiento;

CREATE TABLE cuentas (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         numero_cuenta VARCHAR(50) NOT NULL UNIQUE,
                         tipo_cuenta VARCHAR(20) NOT NULL,
                         saldo_inicial DECIMAL(15,2) NOT NULL,
                         estado BOOLEAN NOT NULL,
                         cliente_id BIGINT NOT NULL

);

CREATE TABLE movimientos (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             fecha TIMESTAMP NOT NULL,
                             tipo_movimiento VARCHAR(50) NOT NULL,
                             valor DECIMAL(15,2) NOT NULL,
                             saldo DECIMAL(15,2) NOT NULL,
                             cuenta_id BIGINT NOT NULL,
                             CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (cuenta_id) REFERENCES cuentas(id)
);
