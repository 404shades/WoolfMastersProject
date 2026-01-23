-- description already lives in base_model, product had its own copy by mistake.
-- move old values to base_model before dropping so nothing is lost.
ALTER TABLE base_model
    ALTER COLUMN description TYPE TEXT;

UPDATE base_model b
SET description = p.description
FROM product p
WHERE p.id = b.id
  AND b.description IS NULL;

ALTER TABLE product
    DROP COLUMN description;
