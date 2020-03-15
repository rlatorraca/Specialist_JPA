insert into produto (id, nome_produto, preco, data_criacao, descricao) values (1, 'Kindle', 499.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, nome_produto, preco, data_criacao, descricao) values (2, 'Câmera GoPro Hero 7', 1400.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.');

insert into cliente (id, nome_cliente) values (1, 'Fernando Medeiros');
insert into cliente (id, nome_cliente) values (2, 'Marcos Mariano');
insert into cliente (id, nome_cliente) values (3, 'Marcelo Prado');

insert into pedido (id, cliente_id, data_criacao, total, status) values (1, 1, sysdate(), 998.0, 'AGUARDANDO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (2, 2, sysdate(), 499.0, 'AGUARDANDO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (3, 3, sysdate(), 1400.0, 'AGUARDANDO');

insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499, 2);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499, 1);


insert into pagamento (pedido_id, status, numero_cartao, tipo_pagamento) values (1, 'PROCESSANDO', '1234-56987-7777-9999', 'cartao');
insert into pagamento (pedido_id, status, codigo_barra, tipo_pagamento) values (2, 'RECEBIDO', '1234FACE43231-XXX222', 'boleto');


insert into categoria (id, nome_categoria) values (1, 'Eletrônicos');
