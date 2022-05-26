CREATE TABLE `telefonia`.`produto` (
  `id` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `fabricante` VARCHAR(255) NOT NULL,
  `valor` DOUBLE NOT NULL,
  `peso` DOUBLE NOT NULL,
  PRIMARY KEY (`id`));
  
ALTER TABLE `telefonia`.`produto` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE telefonia.PRODUTO ADD COLUMN DATA_CADASTRO DATE;

INSERT INTO telefonia.produto (nome, fabricante, valor, peso, data_cadastro)
values ('Nescau', 'Nestlé', 5.7, 0.5, '2022-05-26');

INSERT INTO telefonia.produto (nome, fabricante, valor, peso, data_cadastro)
values ('Caneta', 'Bic', 2.1, 0.12, '2022-05-26');

INSERT INTO telefonia.produto (nome, fabricante, valor, peso, data_cadastro)
values ('Leite', 'Tirol', 4.5, 1.0, '2022-05-26');

INSERT INTO telefonia.produto (nome, fabricante, valor, peso, data_cadastro)
values ('Mouse', 'Logitech', 180.0, 0.3, '2022-05-26');