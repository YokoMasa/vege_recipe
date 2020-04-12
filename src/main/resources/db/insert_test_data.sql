INSERT INTO recipe (name, short_description, long_description, header_image_url, thumbnail_url)
VALUES ("目玉焼き", "基本の目玉焼きです！朝食にバッチリ", "基本の目玉焼きです！朝食にバッチリです。絶対に失敗しない方法をご紹介します。", "/test/header1.png", "/test/thumb1.png");

SET @recipe_id = LAST_INSERT_ID();

INSERT INTO ingredient (recipe_id, name, quantity)
VALUES
(@recipe_id, "卵", "1個"),
(@recipe_id, "油", "少々"),
(@recipe_id, "塩", "少々"),
(@recipe_id, "こしょう", "少々");

INSERT INTO recipeproc (recipe_id, description, image_url)
VALUES
(@recipe_id, "フライパンを熱して油を敷く。", "/test/aaaa.png"),
(@recipe_id, "卵をそっとフライパンに割り落とす。中火で1分温める。", "/test/aaaa.png"),
(@recipe_id, "弱火にして、ふたを載せて2分蒸し焼きにする。", "/test/aaaa.png");
