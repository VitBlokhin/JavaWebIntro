INSERT INTO USERS (login, FIRST_NAME, LAST_NAME, date_create, password, email, status, role)
VALUES('root','Администратор', 'Администратор', CURRENT_TIMESTAMP,
       '$2a$10$LijUmixpYL0i9rRvwXrnX.heUijboQzE3PsoCrxuJANIDVX28FNjS',
       'admin@email', 'ACTIVE', 'ROLE_ADMIN');

INSERT INTO USERS (login, FIRST_NAME, LAST_NAME, date_create, password, email, status, role)
VALUES('operator','Оператор', 'Оператор', CURRENT_TIMESTAMP,
       '$2a$10$LijUmixpYL0i9rRvwXrnX.heUijboQzE3PsoCrxuJANIDVX28FNjS',
       'operator@email', 'ACTIVE', 'ROLE_USER');

INSERT INTO CATEGORIES (name, date_create)
VALUES('Разное', CURRENT_TIMESTAMP);