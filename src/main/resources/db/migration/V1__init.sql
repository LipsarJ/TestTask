-- Создание таблицы продуктов
CREATE TABLE products
(
    id    uuid PRIMARY KEY,
    name  TEXT      NOT NULL,
    price NUMERIC(15, 2) NOT NULL
);

-- Создание таблицы заказов
CREATE TABLE orders
(
    id            uuid PRIMARY KEY,
    costumer_name TEXT not null,
    total_price NUMERIC(15, 2)
);

-- Создание таблицы связей между заказами и продуктами
CREATE TABLE orders_products
(
    order_id uuid,
    product_id uuid NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);
