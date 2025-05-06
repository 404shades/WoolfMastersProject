-- Flyway baseline schema for JOINED inheritance (BaseModel -> Product/Category)

CREATE TABLE IF NOT EXISTS base_model (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS category (
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_category_base_model FOREIGN KEY (id) REFERENCES base_model (id)
);

CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY,
    description TEXT,
    price DOUBLE PRECISION,
    category_id BIGINT,
    CONSTRAINT fk_product_base_model FOREIGN KEY (id) REFERENCES base_model (id),
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE INDEX IF NOT EXISTS idx_product_category ON product (category_id);
