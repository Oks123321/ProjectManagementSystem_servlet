ALTER TABLE developers_skills_relation
    DROP CONSTRAINT developers_skills_relation_developers_id_fkey,
    ADD CONSTRAINT developers_skills_relation_developers_id_fkey
        FOREIGN KEY (developers_id)
            REFERENCES developers (id)
            ON DELETE CASCADE;
ALTER TABLE developers_skills_relation
    DROP CONSTRAINT developers_skills_relation_skills_id_fkey,
    ADD CONSTRAINT developers_skills_relation_skills_id_fkey
        FOREIGN KEY (skills_id)
            REFERENCES skills (id)
            ON DELETE CASCADE;
ALTER TABLE projects_developers_relation
    DROP CONSTRAINT projects_developers_relation_developers_id_fkey,
    ADD CONSTRAINT projects_developers_relation_developers_id_fkey
        FOREIGN KEY (developers_id)
            REFERENCES developers (id)
            ON DELETE CASCADE;
ALTER TABLE projects_developers_relation
    DROP CONSTRAINT projects_developers_relation_projects_id_fkey,
    ADD CONSTRAINT projects_developers_relation_projects_id_fkey
        FOREIGN KEY (projects_id)
            REFERENCES projects(id)
            ON DELETE CASCADE;
ALTER TABLE companies_developers_relation
    ADD CONSTRAINT companies_developers_companies_id_fkey
        FOREIGN KEY (companies_id)
            REFERENCES companies(id)
            ON DELETE CASCADE;
ALTER TABLE companies_developers_relation
    ADD CONSTRAINT companies_developers_companies_id_fkey
        FOREIGN KEY (developers_id)
            REFERENCES developers(id)
            ON DELETE CASCADE;
DROP TABLE companies_developers_relation;