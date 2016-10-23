-- Table: axn_admin
DROP TABLE IF EXISTS axn_admin;
CREATE TABLE axn_admin
(
  id        INTEGER PRIMARY KEY,
  email     VARCHAR(100)   NOT NULL,
  password  VARCHAR(100)   NOT NULL,

  CONSTRAINT axn_admin_pk PRIMARY KEY (id),
  CONSTRAINT axn_admin_email_un UNIQUE (email)
);
INSERT INTO axn_admin (id, email, password) VALUES (1, 'test1@test.mail', 'hashcode1');
INSERT INTO axn_admin (id, email, password) VALUES (2, 'test2@test.mail', 'hashcode2');
INSERT INTO axn_admin (id, email, password) VALUES (3, 'test3@test.mail', 'hashcode3');
INSERT INTO axn_admin (id, email, password) VALUES (4, 'test4@test.mail', 'hashcode4');