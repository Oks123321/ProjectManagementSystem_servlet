INSERT INTO developers (first_name, last_name, age, salary) VALUES
('Harry', 'Harryson', 35, 1000),
('Jack', 'Sparrow', 28, 1200),
('Julia', 'Petterson', 30, 2000),
('Mary', 'Poppins', 40, 1000),
('Tom', 'Soyer', 18, 1200),
('Andrew', 'Smith', 50, 1900);
SELECT * FROM developers;

INSERT INTO skills (language, level) VALUES
('Java', 'Junior'),
('Java', 'Middle'),
('Java', 'Senior'),
('C++', 'Junior'),
('C++', 'Middle'),
('C++', 'Senior'),
('JavaScript', 'Junior'),
('JavaScript', 'Middle'),
('JavaScript', 'Senior');
SELECT * FROM skills;

INSERT INTO developers_skills_relation(developers_id, skills_id) VALUES
(1,1),
(1,5),
(2,9),
(2,7),
(3,6),
(3,4),
(4,2),
(4,8),
(5,2),
(6,9);
SELECT * FROM developers_skills_relation;

INSERT INTO projects(name, descriptions, cost, date) VALUES
('My house', 'application for payment of utilities', 3000, '2021-05-22'),
('Our city', 'application for communication with the city authorities', 2400, '2020-12-01'),
('Travel', 'application about interesting travels', 2900, '2022-01-15'),
('Traffic schedule', 'schedule of urban and suburban transport',1900, '2022-09-01'),
('Hot party', 'organization of parties and banquets', 1200, '2019-06-21'),
('Holiday', 'organization of children_s holidays',1200, '2020-03-08');
select * from projects

INSERT INTO projects_developers_relation(projects_id, developers_id) VALUES
(1,1),
(1,3),
(2,5),
(2,2),
(3,6),
(3,4),
(4,6),
(5,2),
(6,2);
SELECT * FROM projects_developers_relation;

INSERT INTO customers (name,descriptions) VALUES
('Kolos','children health resort'),
('JEO Ltd','management company'),
('VMR','Vinnytsia City Council'),
('TOM-VOYAG', 'travel agency'),
('Bomba-Tur', 'tour operator');
select * from customers

INSERT INTO companies (name,country) VALUES
('UBD','Germany'),
('Optima','Ukraine'),
('Maximus','Poland'),
('Plast', 'Ukraine');
select * from companies

INSERT INTO companies_developers_relation(companies_id, developers_id) VALUES
(1,1),
(1,4),
(2,2),
(2,6),
(2,5),
(3,3),
(3,2),
(4,6),
(4,1);
SELECT * FROM companies_developers_relation;


