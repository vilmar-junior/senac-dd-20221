INSERT INTO ENDERECO (RUA, CIDADE, UF, NUMERO, CEP) VALUES ('Felipe Schimdt', 'Florianópolis', 'SC', '1', '88005444');
INSERT INTO ENDERECO (RUA, CIDADE, UF, NUMERO, CEP) VALUES ('Max Schramm', 'Florianópolis', 'SC', '3', '88005222');
INSERT INTO ENDERECO (RUA, CIDADE, UF, NUMERO, CEP) VALUES ('Presidente Kennedy', 'São José', 'SC', '2', '88222444');
INSERT INTO ENDERECO (RUA, CIDADE, UF, NUMERO, CEP) VALUES ('Nereu Ramos', 'Lages', 'SC', '105', '88555444');

INSERT INTO telefonia.CLIENTE (id,nome, cpf, id_endereco) VALUES (1,'Edson Arantes','11133322211', 2);
INSERT INTO telefonia.CLIENTE (id,nome, cpf, id_endereco) VALUES (2,'Artur Antunes','22233322211', 4);
INSERT INTO telefonia.CLIENTE (id,nome, cpf, id_endereco) VALUES (3,'Marcos André','11133322255', 6);

INSERT INTO telefonia.TELEFONE (id, ddd, numero, tipo, ativo) VALUES (1,'48','32323232', 0, 1);
INSERT INTO telefonia.TELEFONE (id, ddd, numero, tipo, ativo) VALUES (2,'48','984213535', 1, 0);
INSERT INTO telefonia.TELEFONE (id, ddd, numero, tipo, ativo) VALUES (3,'11','77323232', 0, 1);
INSERT INTO telefonia.TELEFONE (id, ddd, numero, tipo, ativo) VALUES (4,'21','774213535', 1, 0);
INSERT INTO telefonia.TELEFONE (id, ddd, numero, tipo, ativo) VALUES (5,'51','32623232', 0, 1);
INSERT INTO telefonia.TELEFONE (id, ddd, numero, tipo, ativo) VALUES (6,'61','985513535', 1, 0);
INSERT INTO telefonia.TELEFONE (id, ddd, numero, tipo, ativo) VALUES (7,'48','32443232', 0, 1);
INSERT INTO telefonia.TELEFONE (id, ddd, numero, tipo, ativo) VALUES (8,'48','894213535', 1, 0);

-- Cadastro de linhas telefônicas

-- Telefone 1 foi do Pelé de 01/01/2020 a 10/05/2021
INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)
VALUES(1, 1, '2020-01-01', '2021-05-10');

-- Telefone 1 é atualmente do Vampeta
INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)
VALUES(3, 1, '2021-08-15', NULL);

-- Telefones 2 e 3 estão com o Zico
INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)
VALUES(2, 2, '2018-04-01', null);

INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)
VALUES(2, 3, '2018-04-01', null);

-- Telefones 4 a 8 estão sem dono
INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)
VALUES(null, 4, null, null);

INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)
VALUES(null, 5, null, null);

INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)
VALUES(null, 6, null, null);

INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)
VALUES(null, 7, null, null);

INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)
VALUES(null, 8, null, null);

-- Desativa os telefones de 4 a 8
UPDATE TELEFONE T
SET ATIVO = 0
WHERE ID IN (4,5,6,7,8);





