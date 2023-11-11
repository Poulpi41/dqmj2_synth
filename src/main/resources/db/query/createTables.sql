create table monster(
    name varchar(127) not null,
    primary key (name)
);
create table synth_prop(
    prop_val int not null,
    rank varchar(63) not null,
    synth_type varchar(63) not null,
    primary key (prop_val),
    unique nulls not distinct (prop_val, rank, synth_type)
);
create table synth_monster(
    parent_name varchar(127) not null,
    lvl varchar(15),
    primary key (parent_name, lvl),
    foreign key (parent_name) references monster(name),
    unique nulls not distinct (parent_name, lvl)
);
create table synth(
    id_s int not null auto_increment,
    son_name varchar(127) not null,
    id_pr int not null,
    primary key (id_s),
    foreign key (son_name) references monster(name),
    foreign key (id_pr) references synth_prop(prop_val),
    unique nulls not distinct (son_name, id_pr)
);
create table parent_list(
    id_pl int not null auto_increment,
    
    p1 varchar(127) not null,
    p2 varchar(127) not null,
    p3 varchar(127),
    p4 varchar(127),
    lv1 varchar(15),
    lv2 varchar(15),
    lv3 varchar(15),
    lv4 varchar(15),
    primary key (id_pl),
    foreign key (p1,lv1) references synth_monster(parent_name,lvl),
    foreign key (p2,lv2) references synth_monster(parent_name,lvl),
    foreign key (p3,lv3) references synth_monster(parent_name,lvl),
    foreign key (p4,lv4) references synth_monster(parent_name,lvl),
    unique nulls not distinct (p1,p2,p3,p4,lv1,lv2,lv3,lv4)
);

create table pl_s(
    id_s int not null,
    id_pl int not null,
    primary key (id_pl, id_s),
    foreign key (id_pl) references parent_list(id_pl),
    foreign key (id_s) references synth(id_s),
    unique nulls not distinct (id_pl, id_s)
);