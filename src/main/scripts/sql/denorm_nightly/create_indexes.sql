drop index if exists cinefiles_denorm.persontermgroup_idx;

create index persontermgroup_idx
   on cinefiles_denorm.persontermgroup(id);

drop index if exists cinefiles_denorm.persontermgroup_termdisplayname_idx;

create index persontermgroup_termdisplayname_idx
   on cinefiles_denorm.persontermgroup(termdisplayname);
