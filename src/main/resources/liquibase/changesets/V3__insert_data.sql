INSERT INTO users (firstname, lastname, username, password)
VALUES ('John', 'Doe', 'johndoe@gmail.com', '$2a$10$F85NxhBROVJFH320z7V3W.8ChCRjs1.A9ChlrG7mveOwtegJmfAEm');

INSERT INTO users_roles (user_id, role)
VALUES (1, 'ROLE_ADMIN');