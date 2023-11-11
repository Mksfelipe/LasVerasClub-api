INSERT INTO sports_reservations_db.sports_reservations_db.client  (id, first_name, last_name, cpf, password, email, enable) VALUES (1,'João', 'Silva', '07782169348', '$2a$12$yr2vdqAMBVayCp64I7QVce7XMVSMzG7yy0APvCgXSaWzTczRiPbIC','sousafelipe123@gmail.com', 'Y');


INSERT INTO quadra  (id,nome,price_per_hour) VALUES (1, 'QUADRA 1', 25.00);

INSERT INTO reserva  (id, inicio, fim, quadra_id, client_id, amount, number_participants, individual_value) VALUES (1, '2023-11-10T12:00:00', '2023-11-10T14:00:00', 1, 1, 100.00, 5, 20.00);


insert into "role" (id, "name") values (1, 'ADMIN');
insert into "role" (id, "name") values (2, 'USER');

insert into client_roles (client_id, role_id) values (1,1);
