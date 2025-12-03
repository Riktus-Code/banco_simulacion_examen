
-- Datos de ejemplo para cuentas de origen
INSERT INTO cuenta_origen (alias_cuenta, iban) VALUES
                                                   ('Cuenta Nómina', 'ES12 3456 7890 1111 2222 3333'),
                                                   ('Cuenta Ahorro', 'ES98 7654 3210 4444 5555 6666'),
                                                   ('Cuenta Empresa', 'ES21 1234 5678 0000 1111 2222');


-- ============================================================================
-- Ejemplos de transferencias programadas
-- ============================================================================

-- Escenario: Alquiler mensual (Cuenta Nómina -> Juan Pérez)
INSERT INTO transferencia_programada
(cuenta_origen_id, nombre_beneficiario, iban_destino,
 importe, concepto, fecha_programada, estado)
VALUES
    (1, 'Juan Pérez', 'ES30 0012 3456 7800 0001 2345',
     650.00, 'Alquiler abril', '2025-04-15', 'PROGRAMADA');

-- Escenario: Factura de suministros (Cuenta Nómina -> Suministros IberLuz)
INSERT INTO transferencia_programada
(cuenta_origen_id, nombre_beneficiario, iban_destino,
 importe, concepto, fecha_programada, estado)
VALUES
    (1, 'Suministros IberLuz', 'ES45 2100 1234 5600 0002 3456',
     82.35, 'Factura luz marzo', '2025-04-10', 'PROGRAMADA');

-- Escenario: Curso de inglés (Cuenta Ahorro -> English Academy SL)
INSERT INTO transferencia_programada
(cuenta_origen_id, nombre_beneficiario, iban_destino,
 importe, concepto, fecha_programada, estado)
VALUES
    (2, 'English Academy SL', 'ES88 1465 9876 5400 0003 4567',
     120.00, 'Matrícula curso B2', '2025-04-20', 'PROGRAMADA');

-- Escenario: Devolución amiga (Cuenta Nómina -> Laura Gómez)
INSERT INTO transferencia_programada
(cuenta_origen_id, nombre_beneficiario, iban_destino,
 importe, concepto, fecha_programada, estado)
VALUES
    (1, 'Laura Gómez', 'ES60 0049 1500 7600 0004 5678',
     50.00, 'Devolución cena', '2025-04-05', 'PROGRAMADA');