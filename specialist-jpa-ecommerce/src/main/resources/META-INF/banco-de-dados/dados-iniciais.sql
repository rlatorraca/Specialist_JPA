insert into produto (id, nome_produto, preco, data_criacao, descricao) values (1, 'Kindle', 599.00, date_sub(sysdate(), interval 4 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, nome_produto, preco, data_criacao, descricao) values (2, 'Câmera GoPro Hero 7', 1400.00, date_sub(sysdate(), interval 3 day), 'Desempenho 2x melhor.');
insert into produto (id, nome_produto, preco, data_criacao, descricao) values (3, 'Teclado Microsoft', 10.00, date_sub(sysdate(), interval 2 day), 'Mais estiloso que um MAC.');
insert into produto (id, nome_produto, preco, data_criacao, descricao) values (4, 'Câmera Canon 80D', 3500.0, sysdate(), 'O melhor ajuste de foco.');

insert into cliente (id, nome_cliente, cpf) values (1, 'Fernando Medeiros',"333");
insert into cliente (id, nome_cliente, cpf) values (2, 'Marcos Mariano',"222");
insert into cliente (id, nome_cliente, cpf) values (3, 'Marcelo Prado', "000");

insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (1, 'MASCULINO', date_sub(sysdate(), interval 27 year));
insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (2, 'MASCULINO', date_sub(sysdate(), interval 30 year))
insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (3, 'MASCULINO', date_sub(sysdate(), interval 42 year));


insert into pedido (id, cliente_id, data_criacao, total, status) values (1, 1, date_sub(sysdate(), interval 32 day), 2398.0, 'AGUARDANDO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (2, 1, date_sub(sysdate(), interval 5 day), 499.0, 'AGUARDANDO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (3, 2, date_sub(sysdate(), interval 4 day), 3500.0, 'PAGO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (4, 3, date_sub(sysdate(), interval 2 day), 499.0, 'PAGO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (5, 1, date_sub(sysdate(), interval 1 day), 599.0, 'PAGO');

insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499, 2);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 3, 1400, 1);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499, 1);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (3, 4, 3500, 1);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (4, 1, 499, 1);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (5, 1, 599, 1)


insert into pagamento (pedido_id, status, numero_cartao, codigo_barra, tipo_pagamento) values (1, 'PROCESSANDO', '1234-56987-7777-9999', null, 'cartao');
insert into pagamento (pedido_id, status, numero_cartao, codigo_barra, tipo_pagamento) values (2, 'RECEBIDO', null, '1234FACE43231-XXX222', 'boleto');
insert into pagamento (pedido_id, status, numero_cartao, codigo_barra, tipo_pagamento) values (3, 'RECEBIDO', null, '8910', 'boleto');
insert into pagamento (pedido_id, status, numero_cartao, codigo_barra, tipo_pagamento) values (4, 'PROCESSANDO',  '1234-56987-4444-7777', null, 'cartao');



insert into nota_fiscal (pedido_id, xml, data_emissao) values (2, '<xml />', sysdate());

insert into categoria (nome_categoria) values ('Eletrônicos');
insert into categoria (nome_categoria) values ('Livros');
insert into categoria (nome_categoria) values ('Esportes');
insert into categoria (nome_categoria) values ('Casa');
insert into categoria (nome_categoria) values ('Jardim');
insert into categoria (nome_categoria) values ('Banheiro');
insert into categoria (nome_categoria) values ('Piscina');
insert into categoria (nome_categoria) values ('Churrasqueira');
insert into categoria (nome_categoria) values ('Verao');
insert into categoria (nome_categoria) values ('Outono');
insert into categoria (nome_categoria) values ('Lacticinios');
insert into categoria (nome_categoria) values ('Fitness');
insert into categoria (nome_categoria) values ('Verduras & Frutas');
insert into categoria (nome_categoria) values ('Mesa & Banho');


insert into produto_categoria (produto_id, categoria_id) values (1, 2);
insert into produto_categoria (produto_id, categoria_id) values (3, 8);
insert into produto_categoria (produto_id, categoria_id) values (4, 8);

