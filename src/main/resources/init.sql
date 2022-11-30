CREATE TABLE IF NOT EXISTS developers (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
	age INTEGER,
	salary INTEGER
	);
ALTER TABLE developers owner to postgres;

CREATE TABLE IF NOT EXISTS projects (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    descriptions VARCHAR (200),
    cost int,
    date DATE
    );

ALTER TABLE projects owner to postgres;

CREATE TABLE IF NOT EXISTS projects_developers_relation (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projects_id BIGINT NOT NULL,
    developers_id BIGINT NOT NULL,
    FOREIGN KEY(projects_id) REFERENCES projects(id),
    FOREIGN KEY (developers_id) REFERENCES developers(id)
);

ALTER TABLE projects_developers_relation owner to postgres;

CREATE TABLE skills (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    language VARCHAR(200),
    level VARCHAR(150)
);
ALTER TABLE skills owner to postgres;

CREATE TABLE developers_skills_relation (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    developers_id BIGINT NOT NULL,
	skills_id BIGINT NOT NULL,
    FOREIGN KEY (developers_id) REFERENCES developers(id),
	FOREIGN KEY(skills_id) REFERENCES skills(id)
);
ALTER TABLE developers_skills_relation owner to postgres;

CREATE TABLE IF NOT EXISTS companies (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    country VARCHAR(150)
);
ALTER TABLE companies owner to postgres;

CREATE TABLE customers (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    descriptions VARCHAR(150)
);
ALTER TABLE customers owner to postgres;

CREATE TABLE IF NOT EXISTS projects_customers_relation (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projects_id BIGINT NOT NULL,
	customers_id BIGINT NOT NULL,
    FOREIGN KEY(customers_id) REFERENCES customers(id),
    FOREIGN KEY (projects_id) REFERENCES projects(id)
);
ALTER TABLE projects_customers_relation owner to postgres;

CREATE TABLE IF NOT EXISTS companies_developers_relation (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    developers_id BIGINT NOT NULL,
    companies_id BIGINT NOT NULL,
    FOREIGN KEY(developers_id) REFERENCES developers(id),
    FOREIGN KEY (companies_id) REFERENCES companies(id)
    );
ALTER TABLE companies_developers_relation owner to postgres;

