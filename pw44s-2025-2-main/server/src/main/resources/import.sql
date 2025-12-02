-- Users

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Joao Pedro', 'joao@email.com', 'Joao Pedro', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Mariana', 'mariana@email.com', 'Mariana', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Vinicius', 'vinicius.silva@email.com', 'Vinicius', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Gustavo', 'gustavo.mueller@email.de', 'Gustavo', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

INSERT INTO tb_user (username, email, display_name, password) VALUES ('Giseli', 'giseli.wei@email.cn', 'Giseli', '$2a$10$ECH0.py.MHfrpjCt0Yhx3u1Y7Rc10C8shMp59pDNz5zDdr8mrqUr.');

-- Addresses

INSERT INTO tb_address (user_id, country, division, postal_code, city, street, address_number, address_type) VALUES (1, 'United States', 'California', '94016', 'San Francisco', 'Market Street', '500', 'Residential');

INSERT INTO tb_address (user_id, country, division, postal_code, city, street, address_number, address_type) VALUES (2, 'Germany', 'Bavaria', '80331', 'Munich', 'Marienplatz', '1', 'Residential');

-- Categories

INSERT INTO tb_category (name) VALUES ('Sofas');

INSERT INTO tb_category (name) VALUES ('Chairs');

-- Products

-- Sofas


INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Caxias Sofa', 'A nature-inspired sofa made with all-natural materials, combining comfort and sustainability.',1799.00,'/assets/images/sofa1.png',1);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Fortaleza Sectional', 'A modular sectional that brings flexibility and comfort to your living space.', 1799.00, '/assets/images/sofa1.png', 1);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Erechim Sofa', 'Luxurious comfort meets sophisticated design with this velvet-upholstered piece.', 1799, '/assets/images/sofa1.png', 1);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('One Sofa', 'Luxurious comfort meets sophisticated design with this velvet-upholstered piece.', 1799, '/assets/images/sofa1.png', 1);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Two Sofa', 'Luxurious comfort meets sophisticated design with this velvet-upholstered piece.', 1799, '/assets/images/sofa1.png', 1);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Three Sofa', 'Luxurious comfort meets sophisticated design with this velvet-upholstered piece.', 1799, '/assets/images/sofa1.png', 1);


-- Chairs

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ( 'Araucaria Chair','Comfortable and stylish, made with sustainable rattan.',1799,'/assets/images/chair1.png',2);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Ipê Chair','Crafted from reclaimed wood and breathable fabric for natural beauty and comfort.',1799,'/assets/images/chair1.png' ,2);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Carnaúba Chair','An ergonomic and eco-friendly seating solution with a clean design.',1799,'/assets/images/chair1.png',2);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('One Chair','An ergonomic and eco-friendly seating solution with a clean design.',1799,'/assets/images/chair1.png',2);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Two Chair','An ergonomic and eco-friendly seating solution with a clean design.',1799,'/assets/images/chair1.png',2);

INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Three Chair','An ergonomic and eco-friendly seating solution with a clean design.',1799,'/assets/images/chair1.png',2);


-- Orders

INSERT INTO tb_order (date, payment_method, delivery_method, user_id, address_id, state) VALUES ('2025-09-28T10:00:00', 'CREDIT_CARD','DELIVERY', 1, 1, 'order');

INSERT INTO tb_order (date, payment_method, delivery_method, user_id, address_id, state) VALUES ('2025-09-28T10:00:00', 'CREDIT_CARD','PICKUP', 2, 2, 'order');

-- Order items

INSERT INTO tb_orderitem (order_id, product_id, unit_price, quantity) VALUES(1, 1, 20.00, 2);

INSERT INTO tb_orderitem (order_id, product_id, unit_price, quantity) VALUES(1, 2, 30.00, 10);

INSERT INTO tb_orderitem (order_id, product_id, unit_price, quantity) VALUES(2, 1, 15.00, 1);