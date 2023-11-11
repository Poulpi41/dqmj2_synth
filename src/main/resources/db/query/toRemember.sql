select *
from synth
join SYNTH_PROP on SYNTH_PROP.prop_val = synth.id_pr
join pl_s on pl_s.id_s = synth.id_s
join PARENT_LIST on PARENT_LIST.ID_PL = pl_s.id_pl