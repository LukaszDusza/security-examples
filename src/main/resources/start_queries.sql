
/*ROLES*/
INSERT INTO role ("role_id", "role")
VALUES (1, 'ADMIN');

INSERT INTO role ("role_id", "role")
VALUES (2, 'USER');


/*USERS*/
INSERT INTO user ("user_id", "active", "email", "last_name", "login", "name", "password")
VALUES (1, 1, 'example@example.pl', 'user', 'user', 'user',
        '$2a$10$xCcU4D6Qfv/BKNCGqu8hz.iQg8ZsPpnCbAqU0HtyonLG8jSV.H3ZG');

INSERT INTO user ("user_id", "active", "email", "last_name", "login", "name", "password")
VALUES (2, 1, 'admin@admin.pl', 'admin', 'admin', 'admin',
        '$2a$10$YToiTl9y93MBfNjGqJ7BMeiYQIwI6bau2Ko1Aoyl90HdlbfZiIFdu');


/*ADD ROLES TO USERS*/
INSERT INTO user_role ("user_id", "role_id") VALUES (1, 2);
INSERT INTO user_role ("user_id", "role_id") VALUES (2, 1);
INSERT INTO user_role ("user_id", "role_id") VALUES (2, 2);