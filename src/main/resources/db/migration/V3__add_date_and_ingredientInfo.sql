ALTER TABLE recipe
ADD create_date DATE NOT NULL DEFAULT '2020-04-01',
ADD serving VARCHAR(20) DEFAULT "2人分";