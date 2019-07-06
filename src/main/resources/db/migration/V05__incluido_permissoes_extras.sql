-- novas permissoes

INSERT INTO permissao (codigo, descricao) values (9, 'ROLE_EDITAR_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) values (10, 'ROLE_EDITAR_PESSOA');
INSERT INTO permissao (codigo, descricao) values (11, 'ROLE_EDITAR_CATEGORIA');

-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 9);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 10);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 11);