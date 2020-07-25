
-- CREATE TYPES TABLE
CREATE TABLE IF NOT EXISTS TYPES 
(
    type_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    type_name VARCHAR(30) NOT NULL
);


-- CREATE ABILITIES TABLE
CREATE TABLE IF NOT EXISTS ABILITIES 
(
    ability_id INT AUTO_INCREMENT  PRIMARY KEY NOT NULL,
    ability_name VARCHAR(30) NOT NULL,
    ability_description VARCHAR(250)
);


-- CREATE POKEMON TABLE
CREATE TABLE IF NOT EXISTS POKEMON 
(
    dex_no INT PRIMARY KEY NOT NULL,
    pokemon_name VARCHAR(100) NOT NULL,
    pokemon_height VARCHAR(15),
    pokemon_weight VARCHAR(15),
    pokemon_category VARCHAR(50),
    pokemon_gender_one VARCHAR(10) NOT NULL,
    pokemon_gender_two VARCHAR(10),
    pokemon_ability_one INT,
    pokemon_ability_two INT,
    pokemon_ability_three INT,
    pokemon_type_one INT,
    pokemon_type_two INT,
    pokemon_weakness_one INT,
    pokemon_weakness_two INT,
    pokemon_weakness_three INT,
    pokemon_weakness_four INT,
    pokemon_weakness_five INT,
    pokemon_weakness_six INT,
    pokemon_weakness_seven INT,
    pokemon_description_one VARCHAR(250) NOT NULL,
    pokemon_description_two VARCHAR(250) NOT NULL,
 	FOREIGN KEY (pokemon_ability_one)
        REFERENCES ABILITIES (ability_id),
    FOREIGN KEY (pokemon_ability_two)
        REFERENCES ABILITIES (ability_id),
    FOREIGN KEY (pokemon_ability_three)
        REFERENCES ABILITIES (ability_id),

    FOREIGN KEY (pokemon_type_one)
        REFERENCES TYPES (type_id),
    FOREIGN KEY (pokemon_type_two)
        REFERENCES TYPES (type_id),

    FOREIGN KEY (pokemon_weakness_one)
        REFERENCES TYPES (type_id),
    FOREIGN KEY (pokemon_weakness_two)
        REFERENCES TYPES (type_id),
    FOREIGN KEY (pokemon_weakness_three)
        REFERENCES TYPES (type_id),
    FOREIGN KEY (pokemon_weakness_four)
        REFERENCES TYPES (type_id),
    FOREIGN KEY (pokemon_weakness_five)
        REFERENCES TYPES (type_id),
    FOREIGN KEY (pokemon_weakness_six)
        REFERENCES TYPES (type_id),
    FOREIGN KEY (pokemon_weakness_seven)
        REFERENCES TYPES (type_id)
);


-- INSERT POKEMON TYPES
INSERT INTO types (type_name) VALUES ('NORMAL');
INSERT INTO types (type_name) VALUES ('FIGHTING');
INSERT INTO types (type_name) VALUES ('FLYING');
INSERT INTO types (type_name) VALUES ('POISON');
INSERT INTO types (type_name) VALUES ('GROUND');
INSERT INTO types (type_name) VALUES ('ROCK');
INSERT INTO types (type_name) VALUES ('BUG');
INSERT INTO types (type_name) VALUES ('GHOST');
INSERT INTO types (type_name) VALUES ('STEEL');
INSERT INTO types (type_name) VALUES ('FIRE');
INSERT INTO types (type_name) VALUES ('WATER');
INSERT INTO types (type_name) VALUES ('GRASS');
INSERT INTO types (type_name) VALUES ('ELECTRIC');
INSERT INTO types (type_name) VALUES ('PSYCHIC');
INSERT INTO types (type_name) VALUES ('ICE');
INSERT INTO types (type_name) VALUES ('DRAGON');
INSERT INTO types (type_name) VALUES ('DARK');
INSERT INTO types (type_name) VALUES ('FAIRY');