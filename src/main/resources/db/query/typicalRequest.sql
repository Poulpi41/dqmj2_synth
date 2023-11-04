select son_name,
    mc1.name_monster1 as parent1,
    mc1.name_monster2 as parent2,
    c2.name_monster1 as parent3,
    c2.name_monster2 as parent4,
    synth_property.synth_type,
    synth_property.rank_type,
    mc1.level_monster1 as level1,
    mc1.level_monster2 as level2,
    c2.level_monster1 as level3,
    c2.level_monster2 as level4,
    synthesis.number_of_parents
from synthesis
join monster_couple mc1 on mc1.id_couple = synthesis.ID_FIRST_COUPLE
join synth_property on synthesis.id_property=synth_property.id_property
left outer join monster_couple c2 on c2.id_couple = synthesis.ID_SECOND_COUPLE 
where son_name = 'great dragon'
order by (synth_property.prop_value) desc
