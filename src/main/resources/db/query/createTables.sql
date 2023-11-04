CREATE TABLE MONSTER
(
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE MONSTER_COUPLE
(
    id_couple INTEGER AUTO_INCREMENT,
    name_monster1 VARCHAR(255) NOT NULL,
    name_monster2 VARCHAR(255) NOT NULL,
    level_monster1 VARCHAR(9),
    level_monster2 VARCHAR(9),
    FOREIGN KEY (name_monster1) REFERENCES MONSTER(name),
    FOREIGN KEY (name_monster2) REFERENCES MONSTER(name),
    PRIMARY KEY (id_couple),
    UNIQUE( name_monster1, name_monster2, level_monster1, level_monster2)
);

CREATE TABLE SYNTH_PROPERTY
(
    id_property INTEGER AUTO_INCREMENT,
    rank_type VARCHAR(255) NOT NULL,
    synth_type VARCHAR(255) NOT NULL,
    prop_value INTEGER NOT NULL,
    PRIMARY KEY (id_property)
);

CREATE TABLE SYNTHESIS
(
    id_synth INTEGER AUTO_INCREMENT,
    son_name VARCHAR(255) NOT NULL,
    id_property INTEGER NOT NULL,
    number_of_parents INTEGER NOT NULL,
    id_first_couple INTEGER NOT NULL,
    id_second_couple INTEGER,
    FOREIGN KEY (son_name) REFERENCES MONSTER(name),
    FOREIGN KEY (id_property) REFERENCES SYNTH_PROPERTY(id_property),
    FOREIGN KEY (id_first_couple) REFERENCES MONSTER_COUPLE(id_couple),
    FOREIGN KEY (id_second_couple) REFERENCES MONSTER_COUPLE(id_couple),
    PRIMARY KEY (id_synth)
);