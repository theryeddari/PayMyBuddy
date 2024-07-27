-- Sélectionner la base de données `mydb`
USE mydb;

-- Réinitialiser et vider les tables (les clés étrangères posent problème avec TRUNCATE, donc besoin de désactiver)
SET FOREIGN_KEY_CHECKS = 0;

-- Vider les tables avant d'insérer les données et réinitialiser les AUTO_INCREMENT
TRUNCATE TABLE ClientRelationships;
TRUNCATE TABLE Transaction;
TRUNCATE TABLE Client;

-- Réactiver les clés étrangères
SET FOREIGN_KEY_CHECKS = 1;

-- Insertion des clients (données réalistes)
INSERT INTO Client (username, email, password, role, saving)
VALUES ('alice', 'alice@example.com', '$2a$10$Le3U7eRFQO7.a5sZa3m3quHL5GjFcfpQKhrkK88/Zk85I1SgKGft6', 'CLIENT', 100),
       ('bob', 'bob@example.com', '$2a$10$n7rJTNPJUCGWw3x1VLETau6mjupLLgZ7hXxjy9MszwHr/qgENULdC', 'CLIENT', 100),
       ('carol', 'carol@example.com', '$2a$10$kL3V3cHES8wL5qK9G6GaC.L2F.akGi5ioddtp4K55lz3d0EF8yF1K', 'CLIENT', 100),
       ('dave', 'dave@example.com', '$2a$10$UTteVOhomraNTrcovsPpc.GC4v3VdCgEzNdhz8jqXrEfvs71Wend2', 'CLIENT', 100);

-- Insertion des connexions d'amis
INSERT INTO ClientRelationships (client_id, friend_id)
VALUES (2, 1),
       (1, 3),
       (2, 4);

-- Insertion des transactions
INSERT INTO Transaction (description, amount, sender, receiver)
VALUES ('Payment for services', 20.00, 2, 1),
       ('Birthday', 30.00, 2, 4),
       ('Reimbursement', 20.00, 1, 3);