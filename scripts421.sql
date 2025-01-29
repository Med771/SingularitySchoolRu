ALTER TABLE student ADD CONSTRAINT age_check CHECK (age >= 16);

ALTER TABLE student ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty ADD CONSTRAINT unique_name_color UNIQUE (name, color);

ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;
