-- Создаем базу данных
CREATE DATABASE postgres;

-- Создаем пользователя и задаем пароль
CREATE USER postgres WITH PASSWORD 'password';

-- Назначаем привилегии
GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;