-- Table: axn_admin
DROP SEQUENCE IF EXISTS axn_admin_id_seq;
CREATE SEQUENCE axn_admin_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
ALTER TABLE axn_admin_id_seq OWNER TO dbaxn;

DROP TABLE IF EXISTS axn_admin;
CREATE TABLE axn_admin
(
  id        BIGINT NOT NULL DEFAULT nextval('axn_admin_id_seq' :: REGCLASS),
  email     TEXT   NOT NULL,
  password  TEXT   NOT NULL,

  CONSTRAINT axn_admin_pk PRIMARY KEY (id),
  CONSTRAINT axn_admin_email_un UNIQUE (email)
)
WITH (OIDS =FALSE);
ALTER TABLE axn_admin OWNER TO dbaxn;
-- Set grants
GRANT SELECT ON ALL TABLES IN SCHEMA public TO dbaxn_application;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO dbaxn_application;