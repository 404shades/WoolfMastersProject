ALTER TABLE base_model
    ADD description VARCHAR(255);

CREATE SEQUENCE IF NOT EXISTS base_model_id_seq;
ALTER TABLE base_model
    ALTER COLUMN id SET NOT NULL;
ALTER TABLE base_model
    ALTER COLUMN id SET DEFAULT nextval('base_model_id_seq');

ALTER SEQUENCE base_model_id_seq OWNED BY base_model.id;