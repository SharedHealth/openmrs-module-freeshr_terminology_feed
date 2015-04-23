CREATE PROCEDURE add_concept_reference_map (INOUT concept_reference_term_id_var INT,
        concept_id INT,
        concept_source_identifier INT,
        reference_term VARCHAR(255),
        reference_name VARCHAR(255),
        reference_type_id INT)
        BEGIN

        INSERT INTO concept_reference_term (concept_source_id, name, code, creator, date_created, uuid)
        VALUES (concept_source_identifier, reference_name, reference_term, 1, now(), uuid());

        select concept_reference_term_id into concept_reference_term_id_var from concept_reference_term
        where name = reference_name and code = reference_term and concept_source_id = concept_source_identifier;

        INSERT INTO concept_reference_map (concept_reference_term_id, concept_map_type_id, creator, date_created, concept_id, uuid)
        VALUES(concept_reference_term_id_var, reference_type_id, 1, now(), concept_id, uuid());
END;