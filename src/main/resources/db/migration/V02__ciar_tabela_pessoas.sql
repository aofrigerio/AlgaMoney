CREATE TABLE pessoa (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL,
	ativo boolean,
	numero VARCHAR(25),
	complemento VARCHAR(50),
	bairro VARCHAR(50),
	cep VARCHAR(50),
	cidade VARCHAR(50),
	estado VARCHAR(2),
	logradouro VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;