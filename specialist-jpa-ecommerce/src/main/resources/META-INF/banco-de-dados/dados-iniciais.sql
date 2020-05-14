insert into produto (id, versao, tenant, nome_produto, preco, data_criacao, ativo, descricao) values (1, 0, 'loja', 'Kindle', 599.00, date_sub(sysdate(), interval 4 day), 'SIM', 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, versao, tenant, nome_produto, preco, data_criacao, ativo, descricao) values (2, 0, 'loja', 'Câmera GoPro Hero 7', 1500.00, date_sub(sysdate(), interval 3 day), 'SIM', 'Desempenho 2x melhor.');
insert into produto (id, versao, tenant, nome_produto, preco, data_criacao, ativo, descricao) values (3, 0, 'loja', 'Teclado Microsoft', 10.00, date_sub(sysdate(), interval 2 day), 'SIM', 'Mais estiloso que um MAC.');
insert into produto (id, versao, tenant, nome_produto, preco, data_criacao, ativo, descricao) values (4, 0, 'loja', 'Câmera Canon 80D', 3500.0, sysdate(), 'NAO', 'O melhor ajuste de foco.');
insert into produto (id, versao, tenant, nome_produto, preco, data_criacao, ativo, descricao) values (5, 0, 'loja', 'Microfone de Lapela', 123.0, sysdate(), 'SIM', 'O melhor audio de lapela do Brasil.');

insert into cliente (id, tenant, versao, nome_cliente, cpf) values (1, 'loja', 0, 'Fernando Medeiros',"333");
insert into cliente (id, tenant, versao, nome_cliente, cpf) values (2, 'loja', 0, 'Marcos Mariano',"222");
insert into cliente (id, tenant, versao, nome_cliente, cpf) values (3, 'loja', 0, 'Marcelo Prado', "321");
insert into cliente (id, tenant, versao, nome_cliente, cpf) values (4, 'loja', 0, 'Marcelo Prado', "000");

insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (1, 'MASCULINO', date_sub(sysdate(), interval 27 year));
insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (2, 'MASCULINO', date_sub(sysdate(), interval 30 year))
insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (3, 'MASCULINO', date_sub(sysdate(), interval 42 year));


insert into pedido (id, tenant, versao, cliente_id, data_criacao, total, status) values (1, 'loja', 0, 1, date_sub(sysdate(), interval 32 day), 2398.0, 'AGUARDANDO');
insert into pedido (id, tenant, versao, cliente_id, data_criacao, total, status) values (2, 'loja', 0, 1, date_sub(sysdate(), interval 5 day), 499.0, 'AGUARDANDO');
insert into pedido (id, tenant, versao, cliente_id, data_criacao, total, status) values (3, 'loja', 0, 2, date_sub(sysdate(), interval 4 day), 3500.0, 'PAGO');
insert into pedido (id, tenant, versao, cliente_id, data_criacao, total, status) values (4, 'loja', 0, 3, date_sub(sysdate(), interval 2 day), 499.0, 'PAGO');
insert into pedido (id, tenant, versao, cliente_id, data_criacao, total, status) values (5, 'loja', 0, 1, date_sub(sysdate(), interval 1 day), 599.0, 'PAGO');
insert into pedido (id, tenant, versao, cliente_id, data_criacao, total, status) values (6, 'loja', 0, 2, sysdate(), 599.0, 'AGUARDANDO');

insert into item_pedido (pedido_id, produto_id, versao, preco_produto, quantidade) values (1, 1, 0, 499, 2);
insert into item_pedido (pedido_id, produto_id, versao, preco_produto, quantidade) values (1, 2, 0, 1400, 1);
insert into item_pedido (pedido_id, produto_id, versao, preco_produto, quantidade) values (2, 1, 0, 499, 1);
insert into item_pedido (pedido_id, produto_id, versao, preco_produto, quantidade) values (3, 4, 0, 3500, 1);
insert into item_pedido (pedido_id, produto_id, versao, preco_produto, quantidade) values (4, 1, 0, 499, 1);
insert into item_pedido (pedido_id, produto_id, versao, preco_produto, quantidade) values (5, 1, 0, 599, 1)
insert into item_pedido (pedido_id, produto_id, versao, preco_produto, quantidade) values (6, 1, 0, 599, 1)


insert into pagamento (pedido_id, tenant, versao, status, numero_cartao, codigo_barra, tipo_pagamento) values (1, 'loja',0, 'PROCESSANDO', '1234-56987-7777-9999', null, 'cartao');
insert into pagamento (pedido_id, tenant, versao, status, numero_cartao, codigo_barra, tipo_pagamento, data_vencimento) values (2, 'loja',0, 'RECEBIDO', null, '1234FACE43231-XXX222', 'boleto', date_sub(sysdate(), interval 3 day));
insert into pagamento (pedido_id, tenant, versao, status, numero_cartao, codigo_barra, tipo_pagamento, data_vencimento) values (3, 'loja',0, 'RECEBIDO', null, '8910', 'boleto', date_sub(sysdate(), interval 2 day));
insert into pagamento (pedido_id, tenant, versao, status, numero_cartao, codigo_barra, tipo_pagamento) values (4, 'loja',0, 'PROCESSANDO',  '1234-56987-4444-7777', null, 'cartao');
insert into pagamento (pedido_id, tenant, versao, status, numero_cartao, codigo_barra, tipo_pagamento, data_vencimento) values (6, 'loja', 0, 'PROCESSANDO', null, '8910', 'boleto', date_add(sysdate(), interval 2 day));



insert into nota_fiscal (pedido_id, tenant, xml, data_emissao) values (2, 'loja', '<xml />', sysdate());

insert into categoria (id, tenant, versao, nome_categoria) values (1, 'loja', 0, 'Eletrodomésticos');
insert into categoria (id, tenant, versao, nome_categoria) values (2, 'loja', 0, 'Livros');
insert into categoria (id, tenant, versao, nome_categoria) values (3, 'loja', 0, 'Esportes');
insert into categoria (id, tenant, versao, nome_categoria) values (4, 'loja', 0, 'Futebol');
insert into categoria (id, tenant, versao, nome_categoria) values (5, 'loja', 0, 'Natação');
insert into categoria (id, tenant, versao, nome_categoria) values (6, 'loja', 0, 'Notebooks');
insert into categoria (id, tenant, versao, nome_categoria) values (7, 'loja', 0, 'Smartphones');
insert into categoria (id, tenant, versao, nome_categoria) values (8, 'loja', 0, 'Câmeras');

insert into produto_categoria (produto_id, categoria_id) values (1, 2);
insert into produto_categoria (produto_id, categoria_id) values (3, 8);
insert into produto_categoria (produto_id, categoria_id) values (4, 8);

insert into produto_loja (id, nome, preco, data_criacao, descricao) values (101, 'Kindle', 799.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto_loja (id, nome, preco, data_criacao, descricao) values (103, 'Câmera GoPro Hero 7', 1500.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.');
insert into produto_loja (id, nome, preco, data_criacao, descricao) values (104, 'Câmera Canon 80D', 3500.0, sysdate(), 'O melhor ajuste de foco.');
insert into produto_loja (id, nome, preco, data_criacao, descricao) values (105, 'Microfone de Lapela', 50.0, sysdate(), 'Produto massa');

insert into ecm_produto (prd_id, prd_nome, prd_preco, prd_data_criacao, prd_descricao) values (201, 'Kindle', 799.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into ecm_produto (prd_id, prd_nome, prd_preco, prd_data_criacao, prd_descricao) values (203, 'Câmera GoPro Hero 7', 1500.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.');
insert into ecm_produto (prd_id, prd_nome, prd_preco, prd_data_criacao, prd_descricao) values (204, 'Câmera Canon 80D', 3500.0, sysdate(), 'O melhor ajuste de foco.');
insert into ecm_produto (prd_id, prd_nome, prd_preco, prd_data_criacao, prd_descricao) values (205, 'Microfone de Lapela', 50.0, sysdate(), 'Produto massa');

insert into erp_produto (id, nome, preco, descricao) values (301, 'Kindle', 799.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into erp_produto (id, nome, preco, descricao) values (303, 'Câmera GoPro Hero 7', 1500.0, 'Desempenho 2x melhor.');
insert into erp_produto (id, nome, preco, descricao) values (304, 'Câmera Canon 80D', 3500.0, 'O melhor ajuste de foco.');
insert into erp_produto (id, nome, preco, descricao) values (305, 'Microfone de Lapela', 50.0, 'Produto massa');

insert into ecm_categoria (cat_id, cat_nome) values (201, 'Eletrodomésticos');
insert into ecm_categoria (cat_id, cat_nome) values (202, 'Livros');
insert into ecm_categoria (cat_id, cat_nome) values (203, 'Esportes');
insert into ecm_categoria (cat_id, cat_nome) values (204, 'Futebol');
insert into ecm_categoria (cat_id, cat_nome) values (205, 'Natação');
insert into ecm_categoria (cat_id, cat_nome) values (206, 'Notebooks');
insert into ecm_categoria (cat_id, cat_nome) values (207, 'Smartphones');
insert into ecm_categoria (cat_id, cat_nome) values (208, 'Câmeras');

