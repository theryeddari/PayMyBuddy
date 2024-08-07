-- MySQL Workbench Synchronization
-- Generated: 2024-06-28 15:39
-- Model: New Model
-- Version: 1.0
-- Project: PayMyBuddy
-- Author: thery

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb`;
CREATE SCHEMA `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Client` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `role` VARCHAR(20) NOT NULL,
  `saving` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_idx` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Transaction` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(100) NOT NULL,
  `amount` DOUBLE NOT NULL,
  `sender` BIGINT(20) NOT NULL,
  `receiver` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_sender_idx` USING BTREE (`sender`) INVISIBLE,
  INDEX `FK_receiver_idx` (`receiver` ASC) VISIBLE,
  CONSTRAINT `FK_sender`
    FOREIGN KEY (`sender`)
    REFERENCES `mydb`.`Client` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_receiver`
    FOREIGN KEY (`receiver`)
    REFERENCES `mydb`.`Client` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ClientRelationships`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ClientRelationships` (
  `client_id` BIGINT(20) NOT NULL,
  `friend_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`client_id`, `friend_id`),
  INDEX `FK_connection_id_idx` (`friend_id` ASC) INVISIBLE,
  CONSTRAINT `FK_user_id`
    FOREIGN KEY (`client_id`)
    REFERENCES `mydb`.`Client` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_connection_id`
    FOREIGN KEY (`friend_id`)
    REFERENCES `mydb`.`Client` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- initialize data of tables

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
VALUES ('Payment for service', 20.00, 2, 1),
       ('Birthday', 30.00, 2, 4),
       ('Reimbursement', 20.00, 1, 3);
-- create restricted profilConnection for Application 

-- Supprimer l'utilisateur s'il existe déjà
DROP USER IF EXISTS 'applicationAccess'@'localhost';

-- Créer l'utilisateur
CREATE USER 'applicationAccess'@'localhost' IDENTIFIED BY 'applicationAccess';

-- Révoquer tous les privilèges existants
REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'applicationAccess'@'localhost';

-- Révoquer tous les privilèges sur tous les fichiers
REVOKE FILE ON *.* FROM 'applicationAccess'@'localhost';

-- Accorder les privilèges spécifiques sur les tables
GRANT SELECT, INSERT, UPDATE, DELETE, DROP ON mydb.transaction TO 'applicationAccess'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, DROP ON mydb.client TO 'applicationAccess'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE, DROP ON mydb.clientrelationships TO 'applicationAccess'@'localhost';