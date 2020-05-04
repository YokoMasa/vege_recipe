CREATE TABLE IF NOT EXISTS image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    save_path VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL
);

ALTER TABLE recipe 
DROP header_image_url,
DROP thumbnail_url,
ADD header_image_id INT,
ADD thumbnail_id INT,
ADD CONSTRAINT 
    FOREIGN KEY (header_image_id) REFERENCES image (id),
ADD CONSTRAINT
    FOREIGN KEY (thumbnail_id) REFERENCES image (id);

ALTER TABLE recipeproc
DROP image_url,
ADD image_id INT,
ADD CONSTRAINT
    FOREIGN KEY (image_id) REFERENCES image (id);