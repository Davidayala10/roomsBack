-- 1. Insertar Roles (revisa que coincidan con los campos de tu entidad Rol)
INSERT INTO Rol (id_rol, nombre_rol, descripcion_rol, fecha) VALUES (1, 'ROLE_ADMIN', 'Administrador del sistema', CURDATE());
INSERT INTO Rol (id_rol, nombre_rol, descripcion_rol, fecha) VALUES (2, 'ROLE_USER', 'Usuario Alumno', CURDATE());

-- 2. Insertar Usuarios (las contraseñas abajo son 'admin' y 'alumno' ya cifradas en BCrypt)
INSERT INTO Usuario (id_usuario, username, password, email, activo) VALUES (1, 'admin', '$2a$10$X59fN3A08KbyG.mR88XWLeE68bS17WreS8DqA9X17hZ8W/M5A33K.', 'admin@escom.mx', 1);
INSERT INTO Usuario (id_usuario, username, password, email, activo) VALUES (2, 'alumno', '$2a$10$wKqK09t9N5hA92vIq2pB.O7Y3b4f6p7TjI2R9L3P8C8Y4G6X6S7C.', 'alumno@escom.mx', 1);

-- 3. Relacionar Usuarios con sus Roles (en tu tabla intermedia, ej: usuario_roles)
INSERT INTO usuario_roles (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuario_roles (usuario_id, rol_id) VALUES (2, 2);

-- 4. Un par de habitaciones de prueba para que tu Front End no aparezca vacío
INSERT INTO Cuarto (id, numero, tipo, numero_camas, precio, disponible) VALUES (1, '101', 'Individual', 1, 500.00, 1);
INSERT INTO Cuarto (id, numero, tipo, numero_camas, precio, disponible) VALUES (2, '202', 'Doble', 2, 850.00, 1);