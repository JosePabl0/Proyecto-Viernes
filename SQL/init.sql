USE inksticker_db;

-- ==========================================================
-- 1. ROLES INICIALES
-- ==========================================================
INSERT IGNORE INTO roles (nombre) VALUES
    ('SuperAdmin'),
    ('Administrador de Empresa'),
    ('Cliente');

-- ==========================================================
-- 2. ADMINISTRADORES INICIALES (contraseña: password)
-- ==========================================================
INSERT IGNORE INTO administradores (nombre, correo, contrasena, activo) VALUES
    ('Admin1', 'admin1@example.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', TRUE),
    ('Admin Estudio', 'admin@inksticker.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', TRUE);

INSERT IGNORE INTO administrador_roles (administrador_id, rol_id)
SELECT a.id, r.id
FROM administradores a
JOIN roles r ON r.nombre = 'SuperAdmin'
WHERE a.correo = 'admin1@example.com';

INSERT IGNORE INTO administrador_roles (administrador_id, rol_id)
SELECT a.id, r.id
FROM administradores a
JOIN roles r ON r.nombre = 'Administrador de Empresa'
WHERE a.correo = 'admin@inksticker.com';

-- ==========================================================
-- 3. CONFIGURACION INICIAL DEL ESTUDIO (HU-19)
-- ==========================================================
INSERT IGNORE INTO configuracion_estudio (
    id,
    nombre_estudio,
    whatsapp,
    mensaje_whatsapp,
    correo_contacto,
    direccion_fisica,
    mapa_embed_url,
    horario_atencion,
    instagram_url,
    facebook_url,
    tiktok_url
) VALUES (
    1,
    'InkSticker Studio',
    '50688888888',
    'Hola InkSticker, me gustaría cotizar un diseño personalizado.',
    'contacto@inksticker.com',
    'San José, Costa Rica',
    'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3930.0!2d-84.0907!3d9.9281!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x0!2zOcKwNTUnNDEuMiJOIDg0wrAwNScyNi41Ilc!5e0!3m2!1ses!2scr!4v1700000000000',
    'Lunes a Viernes: 9:00 AM - 6:00 PM | Sábados: 10:00 AM - 3:00 PM',
    'https://www.instagram.com/',
    'https://www.facebook.com/',
    'https://www.tiktok.com/'
);

-- ==========================================================
-- 4. BANNERS INICIALES (HU-20)
-- ==========================================================
INSERT IGNORE INTO banners (id, tipo, titulo, subtitulo, texto_boton, url_boton, imagen, orden, activo) VALUES
    (1, 'HERO', 'Tatuajes Temporales y Stickers Personalizados', 'Sube tu propio arte o explora nuestras colecciones exclusivas con acabado profesional.', 'Explorar Tienda', '/tienda', 'https://images.unsplash.com/photo-1565058379802-bbe93b2f703a?auto=format&fit=crop&w=1400&q=85', 1, TRUE),
    (2, 'PROMO', 'Diseños a tu Medida', 'Adjunta tu archivo en PNG, JPG, PDF o SVG y recibe cotización directa.', 'Subir Diseño', '/upload', 'https://images.unsplash.com/photo-1513364776144-60967b0f800f?auto=format&fit=crop&w=900&q=85', 2, TRUE);