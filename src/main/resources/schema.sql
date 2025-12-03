CREATE DATABASE IF NOT EXISTS banco_simulacion
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE banco_simulacion;

-- ============================================================================
-- Limpieza previa
-- ============================================================================

DROP TABLE IF EXISTS transferencia_programada;
DROP TABLE IF EXISTS cuenta_origen;

-- ============================================================================
-- Tabla de cuentas de origen
-- ============================================================================

CREATE TABLE cuenta_origen (
                               id             BIGINT NOT NULL AUTO_INCREMENT,
                               alias_cuenta   VARCHAR(100) NOT NULL,
                               iban           VARCHAR(34)  NOT NULL,
                               PRIMARY KEY (id),
                               UNIQUE KEY uq_cuenta_origen_iban (iban)
) ENGINE=InnoDB;


-- ============================================================================
-- Tabla de transferencias programadas
--    (incluye directamente nombre del beneficiario e IBAN de destino)
-- ============================================================================

CREATE TABLE transferencia_programada (
                                          id                    BIGINT NOT NULL AUTO_INCREMENT,
                                          cuenta_origen_id      BIGINT NOT NULL,
                                          nombre_beneficiario   VARCHAR(150) NOT NULL,
                                          iban_destino          VARCHAR(34)  NOT NULL,
                                          importe               DECIMAL(10,2) NOT NULL,
                                          concepto              VARCHAR(255)  NOT NULL,
                                          fecha_programada      DATE          NOT NULL,
                                          fecha_creacion        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          estado                VARCHAR(20)   NOT NULL DEFAULT 'PROGRAMADA',
    -- valores típicos para estado: PROGRAMADA, CANCELADA, EJECUTADA

                                          PRIMARY KEY (id),

                                          CONSTRAINT fk_transferencia_cuenta_origen
                                              FOREIGN KEY (cuenta_origen_id)
                                                  REFERENCES cuenta_origen (id)
                                                  ON UPDATE CASCADE
                                                  ON DELETE RESTRICT
) ENGINE=InnoDB;


