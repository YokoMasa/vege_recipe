CREATE TABLE IF NOT EXISTS Image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    save_path VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL
);

ALTER TABLE Recipe 
DROP header_image_url,
DROP thumbnail_url,
ADD header_image_id INT,
ADD thumbnail_id INT,
ADD CONSTRAINT 
    FOREIGN KEY (header_image_id) REFERENCES Image (id),
ADD CONSTRAINT
    FOREIGN KEY (thumbnail_id) REFERENCES Image (id);

