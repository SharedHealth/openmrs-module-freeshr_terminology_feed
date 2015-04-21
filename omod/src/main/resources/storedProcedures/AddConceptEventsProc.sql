CREATE PROCEDURE add_concept_events (new_concept_id INT,
                              class_name VARCHAR(255),
                              url VARCHAR(255))

BEGIN

  select uuid into @db_uuid from concept where concept_id = new_concept_id;
  select concat(url, @db_uuid) into @uri;

  insert into event_records(uuid, title, category, uri, object) values(uuid(), 'concept', 'concept', @uri, @uri);
  insert into event_records(uuid, title, category, uri, object) values(uuid(), class_name, class_name, @uri, @uri);

END;
