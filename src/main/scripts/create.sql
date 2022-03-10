CREATE SCHEMA `telefonia`;

CREATE TABLE `telefonia`.`endereco` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rua` VARCHAR(255) NOT NULL,
  `uf` VARCHAR(2) NOT NULL,
  `cidade` VARCHAR(255) NOT NULL,
  `numero` VARCHAR(255) NOT NULL,
  `cep` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`id`));

 