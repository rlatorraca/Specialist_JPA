insert into produto (id, nome, preco, descricao) values (1, 'Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, nome, preco, descricao) values (3, 'XPS Dell 15 polegadas', 3499.0, 'Conheça o novo XPS 15  agora com 64Gb de Memoria.');
insert into produto (id, nome, preco, descricao) values (4, 'Compa1-500-20121', 3899.44, 'Conheça o novo Compaq de ultima geracao.');


insert into cliente (id, nome) values (1, 'Fernando Medeiros');
insert into cliente (id, nome) values (2, 'Marcos Mariano');
insert into cliente (id, nome) values (3, 'Cristina Barralho');
insert into cliente (id, nome) values (4, 'Mirtes Pinto');

insert into pedido (id, cliente_id, data_pedido, total, status) values (1, 1, sysdate(), 100.0, 'AGUARDANDO');

insert into item_pedido (id, pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 1, 5.0, 2);

insert into categoria (id,nome) values (1, 'Eletrônicos');
