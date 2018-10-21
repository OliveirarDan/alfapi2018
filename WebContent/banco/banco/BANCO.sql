-- MySQL Script generated by MySQL Workbench
-- 08/26/18 15:09:23
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema alfapidb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `alfapidb` ;

-- -----------------------------------------------------
-- Schema alfapidb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `alfapidb` DEFAULT CHARACTER SET utf8 ;
USE `alfapidb` ;

-- -----------------------------------------------------
-- Table `alfapidb`.`Pessoa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `alfapidb`.`Pessoa` ;

CREATE TABLE IF NOT EXISTS `alfapidb`.`Pessoa` (
  `idPessoa` INT NOT NULL,
  `nome` VARCHAR(100) NULL,
  `cpf` INT NULL,
  `rg` VARCHAR(15) NULL,
  `email` VARCHAR(45) NULL,
  `cep` VARCHAR(10) NULL,
  `telCelular` VARCHAR(15) NULL,
  `codAzure` VARCHAR(80) NULL,
  PRIMARY KEY (`idPessoa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `alfapidb`.`Pais`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `alfapidb`.`Pais` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Pais` (
  `idPais` INT NOT NULL,
  `nomePais` VARCHAR(45) NULL,
  PRIMARY KEY (`idPais`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Estado`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Estado` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Estado` (
  `idEstado` INT NOT NULL,
  `nomeEstado` VARCHAR(45) NULL,
  `Pais_idPais` INT NOT NULL,
  PRIMARY KEY (`idEstado`),
  INDEX `fk_Estado_Pais1_idx` (`Pais_idPais` ASC),
  CONSTRAINT `fk_Estado_Pais1`
    FOREIGN KEY (`Pais_idPais`)
    REFERENCES `mydb`.`Pais` (`idPais`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Cidade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Cidade` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Cidade` (
  `idCidade` INT NOT NULL,
  `nomeCidade` VARCHAR(45) NULL,
  `Estado_idEstado` INT NOT NULL,
  PRIMARY KEY (`idCidade`),
  INDEX `fk_Cidade_Estado1_idx` (`Estado_idEstado` ASC),
  CONSTRAINT `fk_Cidade_Estado1`
    FOREIGN KEY (`Estado_idEstado`)
    REFERENCES `mydb`.`Estado` (`idEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Endereco`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Endereco` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Endereco` (
  `idEndereco` INT NOT NULL,
  `cep` VARCHAR(45) NULL,
  `tipodelogradouro` VARCHAR(45) NULL,
  `endereco` VARCHAR(70) NULL,
  `numero` VARCHAR(45) NULL,
  `bairro` VARCHAR(45) NULL,
  `Cidade_idCidade` INT NOT NULL,
  `Pessoa_idPessoa` INT NOT NULL,
  PRIMARY KEY (`idEndereco`),
  INDEX `fk_Endereco_Cidade1_idx` (`Cidade_idCidade` ASC),
  INDEX `fk_Endereco_Pessoa1_idx` (`Pessoa_idPessoa` ASC),
  CONSTRAINT `fk_Endereco_Cidade1`
    FOREIGN KEY (`Cidade_idCidade`)
    REFERENCES `mydb`.`Cidade` (`idCidade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Endereco_Pessoa1`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `mydb`.`Pessoa` (`idPessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
