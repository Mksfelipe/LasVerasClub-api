INSERT INTO users (id, first_name, last_name, cpf, password, email, enable) VALUES (1, 'João', 'Silva', '07782169348', '$2a$12$yr2vdqAMBVayCp64I7QVce7XMVSMzG7yy0APvCgXSaWzTczRiPbIC','sousafelipe123@gmail.com', 'Y');


INSERT INTO quadra (id,nome,price_per_hour,ativo) VALUES (1, 'QUADRA A', 25.00, 'Y');

insert into "role" (id, "name") values (1, 'ADMIN');
insert into "role" (id, "name") values (2, 'USER');

insert into user_roles (user_id, role_id) values (1,1);