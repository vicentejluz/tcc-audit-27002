CREATE SCHEMA IF NOT EXISTS mydb;

USE mydb;

CREATE TABLE IF NOT EXISTS tb_company (
  id_company BIGINT NOT NULL AUTO_INCREMENT,
  city VARCHAR(60),
  postal_code VARCHAR(9),
  state VARCHAR(60),
  street VARCHAR(60),
  cnpj VARCHAR(18),
  name VARCHAR(60),
  PRIMARY KEY (id_company),
  UNIQUE INDEX uk_cnpj (cnpj));

CREATE TABLE IF NOT EXISTS tb_topic (
  id_topic BIGINT NOT NULL AUTO_INCREMENT,
  text VARCHAR(255),
  PRIMARY KEY (id_topic));

CREATE TABLE IF NOT EXISTS tb_summary (
  id_summary BIGINT NOT NULL AUTO_INCREMENT,
  text VARCHAR(255),
  id_topic BIGINT NOT NULL,
  PRIMARY KEY (id_summary),
  INDEX fk_summary_id_topic (id_topic),
  CONSTRAINT fk_summary_id_topic FOREIGN KEY (id_topic) REFERENCES tb_topic (id_topic));

CREATE TABLE IF NOT EXISTS tb_question (
  id_question BIGINT NOT NULL AUTO_INCREMENT,
  question VARCHAR(255),
  id_summary BIGINT NOT NULL,
  PRIMARY KEY (id_question),
  INDEX fk_question_id_summary (id_summary), 
  CONSTRAINT fk_question_id_summary FOREIGN KEY (id_summary) REFERENCES tb_summary (id_summary));

CREATE TABLE IF NOT EXISTS tb_answer (
  id_answer BIGINT NOT NULL AUTO_INCREMENT,
  fully_met BIT(1) NOT NULL,
  not_applicable BIT(1) NOT NULL,
  not_met BIT(1) NOT NULL,
  partially_met BIT(1) NOT NULL,
  id_company BIGINT NOT NULL,
  id_question BIGINT NOT NULL,
  PRIMARY KEY (id_answer),
  INDEX fk_answer_id_company (id_company),
  INDEX fk_answer_id_question (id_question),
  CONSTRAINT fk_answer_id_company FOREIGN KEY (id_company) REFERENCES tb_company (id_company),
  CONSTRAINT fk_answer_id_question FOREIGN KEY (id_question) REFERENCES tb_question (id_question));

CREATE TABLE IF NOT EXISTS tb_department (
  id_department BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(30),
  PRIMARY KEY (id_department));

CREATE TABLE IF NOT EXISTS tb_employee (
  id_employee BIGINT NOT NULL AUTO_INCREMENT,
  email VARCHAR(60) NOT NULL,
  name VARCHAR(60),
  password VARCHAR(60) NOT NULL,
  role VARCHAR(30),
  id_company BIGINT NOT NULL,
  id_department BIGINT NOT NULL,
  PRIMARY KEY (id_employee),
  UNIQUE INDEX uk_email (email),
  INDEX fk_employee_id_company (id_company),
  INDEX fk_employee_id_department (id_department),
  CONSTRAINT fk_employee_id_company FOREIGN KEY (id_company) REFERENCES tb_company (id_company),
  CONSTRAINT fk_employee_id_department FOREIGN KEY (id_department) REFERENCES tb_department (id_department));

CREATE TABLE IF NOT EXISTS tb_evidence (
  id_evidence BIGINT NOT NULL,
  file TINYBLOB NOT NULL,
  name VARCHAR(60),
  PRIMARY KEY (id_evidence),
  CONSTRAINT fk_evidence_id_answer FOREIGN KEY (id_evidence) REFERENCES tb_answer (id_answer));

CREATE TABLE IF NOT EXISTS tb_weight (
  id_weight BIGINT NOT NULL,
  weight DOUBLE NOT NULL,
  PRIMARY KEY (id_weight),
  CONSTRAINT fk_weight_id_answer FOREIGN KEY (id_weight) REFERENCES tb_answer (id_answer));
