-- every product now belongs to the user who created it (vendor or admin).
-- nullable because products created before auth existed have no owner,
-- only admin can edit those.
ALTER TABLE product
    ADD COLUMN vendor_id BIGINT;

ALTER TABLE product
    ADD CONSTRAINT fk_product_vendor FOREIGN KEY (vendor_id) REFERENCES users (id) ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_product_vendor ON product (vendor_id);
