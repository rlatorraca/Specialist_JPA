insert into produto (id, nome_produto, preco, data_criacao, descricao) values (1, 'Kindle', 499.00, date_sub(sysdate(), interval 4 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, nome_produto, preco, data_criacao, descricao) values (2, 'Câmera GoPro Hero 7', 1400.00, date_sub(sysdate(), interval 3 day), 'Desempenho 2x melhor.');
insert into produto (id, nome_produto, preco, data_criacao, descricao) values (3, 'Teclado Microsoft', 10.00, date_sub(sysdate(), interval 2 day), 'Mais estiloso que um MAC.');

insert into cliente (id, nome_cliente, cpf) values (1, 'Fernando Medeiros',"333");
insert into cliente (id, nome_cliente, cpf) values (2, 'Marcos Mariano',"222");
insert into cliente (id, nome_cliente, cpf) values (3, 'Marcelo Prado', "000");

insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (1, 'MASCULINO', date_sub(sysdate(), interval 27 year));
insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (2, 'MASCULINO', date_sub(sysdate(), interval 30 year))
insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (3, 'MASCULINO', date_sub(sysdate(), interval 42 year));


insert into pedido (id, cliente_id, data_criacao, total, status) values (1, 1, sysdate(), 998.00, 'AGUARDANDO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (2, 2, sysdate(), 499.00, 'AGUARDANDO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (3, 3, sysdate(), 1400.00, 'AGUARDANDO');

insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499.00, 2);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499.00, 1);


insert into pagamento (pedido_id, status, numero_cartao, codigo_barra, tipo_pagamento) values (1, 'PROCESSANDO', '1234-56987-7777-9999', null, 'cartao');
insert into pagamento (pedido_id, status, numero_cartao, codigo_barra, tipo_pagamento) values (2, 'RECEBIDO', null, '1234FACE43231-XXX222', 'boleto');


insert into categoria (id, nome_categoria) values (1, 'Eletrônicos');
