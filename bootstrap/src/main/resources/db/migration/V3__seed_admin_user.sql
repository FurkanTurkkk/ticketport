-- İlk admin (yalnızca geliştirme / bootstrap). Üretimde şifreyi değiştirin veya bu migration'ı devre dışı bırakın.
--
-- Email   : admin@ticketport.local  (Email value object kurallarına uygun)
-- Parola  : Admin1!Pass             (Password value object: >=8, A-Z, a-z, rakam, özel: !)
-- Rol     : ROLE_ADMIN              (Role enum)
--
-- Parola BCrypt (cost 10), Spring Security org.springframework.security.crypto.bcrypt.BCrypt ile üretildi.

INSERT INTO users (id, email, password, role, created_at, updated_at)
VALUES (
    'a0000001-0000-4000-8000-000000000001',
    'admin@ticketport.local',
    '$2a$10$a/Zh3hOmTZHEL5lFlfE71e4EmNrQN9XcW05h/.ONn15vj5FNWezyi',
    'ROLE_ADMIN',
    NOW(),
    NOW()
);
