ALTER TABLE Recipe
ADD create_date DATE NOT NULL DEFAULT '2020-04-01',
ADD serving VARCHAR(20) NOT NULL DEFAULT "2人分";