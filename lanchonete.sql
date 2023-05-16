-- -----------------------------------------------------
-- Table estados
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS estados (
  id SERIAL,
  uf CHAR(2) NOT NULL,
  descricao VARCHAR(45) NOT NULL,
  PRIMARY KEY (id))
;


-- -----------------------------------------------------
-- Table cidades
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS cidades (
  id SERIAL,
  descricao VARCHAR(45) NOT NULL,
  estado_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_cidade_estado
    FOREIGN KEY (estado_id)
    REFERENCES estados (id)
    )
;


-- -----------------------------------------------------
-- Table clientes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS clientes (
  id SERIAL,
  nome VARCHAR(255) NOT NULL,
  cpf CHAR(14) NULL,
  logradouro VARCHAR(255) NOT NULL,
  bairro VARCHAR(255) NOT NULL,
  numero VARCHAR(5) NOT NULL,
  cep CHAR(9) NOT NULL,
  email VARCHAR(255) NOT NULL,
  celular CHAR(13) NOT NULL,
  modoentregapreferido VARCHAR(45) NOT NULL,
  temwhatsapp BOOLEAN NOT NULL,
  datacadastro DATE NOT NULL,
  cidade_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_clientes_cidade1
    FOREIGN KEY (cidade_id)
    REFERENCES cidades (id)
    )
;


-- -----------------------------------------------------
-- Table colaboradores
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS colaboradores (
  id SERIAL,
  nome VARCHAR(255) NOT NULL,
  cpf CHAR(14) NULL,
  logradouro VARCHAR(255) NOT NULL,
  bairro VARCHAR(255) NOT NULL,
  numero VARCHAR(5) NOT NULL,
  cep CHAR(9) NOT NULL,
  email VARCHAR(255) NOT NULL,
  celular CHAR(13) NOT NULL,
  cargo VARCHAR(45) NOT NULL,
  setor VARCHAR(45) NULL,
  salario DECIMAL(10,2) NOT NULL,
  datainicio DATE NOT NULL,
  cidade_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_clientes_cidade10
    FOREIGN KEY (cidade_id)
    REFERENCES cidades (id)
    )
;


-- -----------------------------------------------------
-- Table fornecedores
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS fornecedores (
  id SERIAL,
  nomefantasia VARCHAR(255) NOT NULL,
  razaosocial VARCHAR(45) NOT NULL,
  cnpj CHAR(18) NOT NULL,
  logradouro VARCHAR(255) NOT NULL,
  bairro VARCHAR(255) NOT NULL,
  numero VARCHAR(5) NOT NULL,
  cep CHAR(9) NOT NULL,
  email VARCHAR(255) NOT NULL,
  telefone CHAR(13) NOT NULL,
  dataultimacompra DATE NOT NULL,
  cidade_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_clientes_cidade11
    FOREIGN KEY (cidade_id)
    REFERENCES cidades (id)
    )
;


-- -----------------------------------------------------
-- Table produtos
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS produtos (
  id SERIAL,
  descricao VARCHAR(255) NOT NULL,
  codigobarras VARCHAR(15) NULL,
  qtdestoque DECIMAL(10,2) NOT NULL,
  preco DECIMAL(10,2) NOT NULL,
  datacadastro DATE NOT NULL,
  estoqueminimo DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id))
;


-- -----------------------------------------------------
-- Table vendas
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS vendas (
  id SERIAL,
  data DATE NOT NULL,
  clientes_id INT NOT NULL,
  colaboradores_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_vendas_clientes1
    FOREIGN KEY (clientes_id)
    REFERENCES clientes (id)
    ,
  CONSTRAINT fk_vendas_colaboradores1
    FOREIGN KEY (colaboradores_id)
    REFERENCES colaboradores (id)
    )
;


-- -----------------------------------------------------
-- Table venda_produto
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS venda_produto (
  vendas_id INT NOT NULL,
  produtos_id INT NOT NULL,
  quantidade DECIMAL(10,2) NOT NULL,
  preco DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (vendas_id, produtos_id),
  CONSTRAINT fk_vendas_has_produtos_vendas1
    FOREIGN KEY (vendas_id)
    REFERENCES vendas (id)
    ,
  CONSTRAINT fk_vendas_has_produtos_produtos1
    FOREIGN KEY (produtos_id)
    REFERENCES produtos (id)
    )
;


-- -----------------------------------------------------
-- Table compras
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS compras (
  id SERIAL,
  data DATE NOT NULL,
  colaboradores_id INT NOT NULL,
  fornecedores_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_compras_colaboradores1
    FOREIGN KEY (colaboradores_id)
    REFERENCES colaboradores (id)
    ,
  CONSTRAINT fk_compras_fornecedores1
    FOREIGN KEY (fornecedores_id)
    REFERENCES fornecedores (id)
    )
;


-- -----------------------------------------------------
-- Table compra_produto
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS compra_produto (
  produtos_id INT NOT NULL,
  compras_id INT NOT NULL,
  quantidade DECIMAL(10,2) NOT NULL,
  preco DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (produtos_id, compras_id),
  CONSTRAINT fk_produtos_has_compras_produtos1
    FOREIGN KEY (produtos_id)
    REFERENCES produtos (id)
    ,
  CONSTRAINT fk_produtos_has_compras_compras1
    FOREIGN KEY (compras_id)
    REFERENCES compras (id)
    )
;


-- -----------------------------------------------------
-- Table usuarios
-- -----------------------------------------------------
CREATE TABLE usuarios (
	id SERIAL,
	nome VARCHAR (255) NOT NULL,
	senha VARCHAR (255) NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO usuarios (nome, senha) VALUES ('mateus', md5('teste'));

ALTER TABLE usuarios ADD COLUMN situacao CHAR(1) DEFAULT 'A';