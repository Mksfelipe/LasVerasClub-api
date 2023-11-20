INSERT INTO client (id, first_name, last_name, cpf, password, email, enable) VALUES (1,'João', 'Silva', '07782169348', '$2a$12$yr2vdqAMBVayCp64I7QVce7XMVSMzG7yy0APvCgXSaWzTczRiPbIC','sousafelipe123@gmail.com', 'Y');


INSERT INTO quadra (id,nome,price_per_hour,ativo) VALUES (1, 'QUADRA A', 25.00, 'Y');

insert into "role" (id, "name") values (1, 'ADMIN');
insert into "role" (id, "name") values (2, 'USER');

insert into client_roles (client_id, role_id) values (1,1);

insert	into reserva_fixa  (id,	ativo,	amount,	horario_inicio,	horario_fim, individual_value,	number_participants,	type_sport,	client_id,	quadra_id,	domingo,	quarta,	quinta,	sabado,	segunda,	sexta,	terca)values (1,'Y',50.00,'10:00:00Z','12:00:00Z',10.00,8,1,1,1,0,'N','Y','N','N','N','N');