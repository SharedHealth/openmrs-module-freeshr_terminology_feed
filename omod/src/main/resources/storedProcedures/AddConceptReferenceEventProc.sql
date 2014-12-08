CREATE PROCEDURE add_concept_reference_events (new_concept_reference_term_id INT)

BEGIN

  select uuid into @db_uuid from concept_reference_term where concept_reference_term_id = new_concept_reference_term_id;
  select concat('/openmrs/ws/rest/v1/tr/referenceterms/', @db_uuid) into @uri;

  insert into event_records(uuid, title, category, uri, object) values(uuid(), 'ConceptReferenceTerm', 'ConceptReferenceTerm', @uri, @uri);

END;