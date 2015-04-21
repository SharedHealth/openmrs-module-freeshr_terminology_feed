CREATE PROCEDURE add_concept_reference_events (new_concept_reference_term_id INT,
                                              url VARCHAR(255))

BEGIN

  select uuid into @db_uuid from concept_reference_term where concept_reference_term_id = new_concept_reference_term_id;
  select concat(url, @db_uuid) into @uri;

  insert into event_records(uuid, title, category, uri, object) values(uuid(), 'ConceptReferenceTerm', 'ConceptReferenceTerm', @uri, @uri);

END;