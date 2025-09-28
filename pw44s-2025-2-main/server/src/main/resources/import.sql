-- Users

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Joao Pedro', 'joao@email.com', 'Joao Pedro', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Mariana', 'mariana@email.com', 'Mariana', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Vinicius', 'vinicius.silva@email.com', 'Vinicius', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Gustavo', 'gustavo.mueller@email.de', 'Gustavo', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Giseli', 'giseli.wei@email.cn', 'Giseli', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

-- Addresses

INSERT INTO tb_addresses (user_id, country, division, postal_code, city, street, address_number, address_type) VALUES (1, 'United States', 'California', '94016', 'San Francisco', 'Market Street', '500', 'Residential');

INSERT INTO tb_addresses (user_id, country, division, postal_code, city, street, address_number, address_type) VALUES (2, 'Germany', 'Bavaria', '80331', 'Munich', 'Marienplatz', '1', 'Residential');

-- Categories

INSERT INTO tb_category (name) VALUES ('Chairs');

INSERT INTO tb_category (name) VALUES ('Tables');

-- Products

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Office Chair', 'Cool Office Chair', 150.00,'1.png', 1);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Dining Table', 'Really Cool Table', 200.00,'2.png', 2);

-- Orders

INSERT INTO tb_order (user_id, date) VALUES (1, '2025-09-28T10:00:00');

INSERT INTO tb_order (user_id, date) VALUES (1, '2025-09-28T10:00:00');

INSERT INTO tb_order (user_id, date) VALUES (2, '2025-09-28T10:00:00');

-- Order items

INSERT INTO tb_orderitem (order_id, product_id, unit_price, quantity) VALUES (1, 1, 150.00, 2);

INSERT INTO tb_orderitem (order_id, product_id, unit_price, quantity) VALUES (1, 2, 150.00, 3);

INSERT INTO tb_orderitem (order_id, product_id, unit_price, quantity) VALUES (2, 2, 150.00, 1);