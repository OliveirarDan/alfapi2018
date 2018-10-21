-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema alfapidb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema alfapidb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `alfapidb` DEFAULT CHARACTER SET utf8 ;
USE `alfapidb` ;

-- -----------------------------------------------------
-- Table `alfapidb`.`Endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alfapidb`.`Endereco` (
  `idEndereco` INT NOT NULL AUTO_INCREMENT,
  `cep` VARCHAR(45) NULL,
  `tipodelogradouro` VARCHAR(45) NULL,
  `endereco` VARCHAR(70) NULL,
  `numero` VARCHAR(45) NULL,
  `bairro` VARCHAR(45) NULL,
  `estado` VARCHAR(45) NULL,
  `cidade` VARCHAR(45) NULL,
  `pais` VARCHAR(45) NULL,
  PRIMARY KEY (`idEndereco`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `alfapidb`.`Pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alfapidb`.`Pessoa` (
  `idPessoa` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NULL,
  `sobrenome` VARCHAR(100) NULL,
  `cpf` VARCHAR(20) NULL,
  `registrosec` VARCHAR(30) NULL,
  `email` VARCHAR(100) NULL,
  `dataNascimento` VARCHAR(10) NULL,
  `genero` VARCHAR(30) NULL,
  `telResidencial` VARCHAR(45) NULL,
  `telSecundario` VARCHAR(15) NULL,
  `codAzure` VARCHAR(80) NULL,
  `Endereco_idEndereco` INT,
  PRIMARY KEY (`idPessoa`),
  UNIQUE INDEX `idPessoa_UNIQUE` (`idPessoa` ASC),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) ,
  UNIQUE INDEX `registrosec_UNIQUE` (`registrosec` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
