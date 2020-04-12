CREATE TABLE IF NOT EXISTS Recipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    short_description VARCHAR(511) NOT NULL,
    long_description VARCHAR(4095) NOT NULL,
    header_image_url VARCHAR(255),
    thumbnail_url VARCHAR(255),
    ingredient_order VARCHAR(255),
    recipe_proc_order VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Ingredient (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    recipe_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity VARCHAR(255) NOT NULL,
    CONSTRAINT
        FOREIGN KEY (recipe_id)
        REFERENCES Recipe (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS RecipeProc (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    recipe_id INT NOT NULL,
    description VARCHAR(511) NOT NULL,
    image_url VARCHAR(255),
    CONSTRAINT
        FOREIGN KEY (recipe_id)
        REFERENCES Recipe (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);