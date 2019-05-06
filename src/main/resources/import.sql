INSERT INTO role (name) values('ROLE_ADMIN');
INSERT INTO role (name) values('ROLE_USER');

INSERT INTO webuser(name, username, password) VALUES ('Administrador', 'admin','$2a$10$.PVIfB07x.SfMYTcToxL0.yxcLWU0GbS2NUO1W1QAvqMm/TsFhVem');
INSERT INTO webuser(name, username, password) VALUES ('Teste', 'teste','$2a$10$.PVIfB07x.SfMYTcToxL0.yxcLWU0GbS2NUO1W1QAvqMm/TsFhVem');

INSERT INTO webuser_roles(user_id, roles_id) VALUES (1, 1);
INSERT INTO webuser_roles(user_id, roles_id) VALUES (1, 2);
INSERT INTO webuser_roles(user_id, roles_id) VALUES (2, 2);
