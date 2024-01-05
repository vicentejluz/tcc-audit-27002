CREATE SCHEMA IF NOT EXISTS mydb;

USE mydb;

SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

CREATE TABLE IF NOT EXISTS tb_company (
  id_company BIGINT NOT NULL AUTO_INCREMENT,
  city VARCHAR(40) NOT NULL,
  postal_code CHAR(9) NOT NULL,
  state VARCHAR(20) NOT NULL,
  street VARCHAR(60) NOT NULL,
  cnpj CHAR(18) NOT NULL,
  name VARCHAR(60) NOT NULL,
  PRIMARY KEY (id_company),
  UNIQUE INDEX uk_cnpj (cnpj));

CREATE TABLE IF NOT EXISTS tb_topic (
  id_topic BIGINT NOT NULL AUTO_INCREMENT,
  text VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_topic));

CREATE TABLE IF NOT EXISTS tb_summary (
  id_summary BIGINT NOT NULL AUTO_INCREMENT,
  text TEXT NOT NULL,
  id_topic BIGINT NOT NULL,
  PRIMARY KEY (id_summary),
  INDEX fk_summary_id_topic (id_topic),
  CONSTRAINT fk_summary_id_topic FOREIGN KEY (id_topic) REFERENCES tb_topic (id_topic));

CREATE TABLE IF NOT EXISTS tb_question (
  id_question BIGINT NOT NULL AUTO_INCREMENT,
  question TEXT NOT NULL,
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
  name VARCHAR(30) NOT NULL,
  PRIMARY KEY (id_department));

CREATE TABLE IF NOT EXISTS tb_employee (
  id_employee BIGINT NOT NULL AUTO_INCREMENT,
  email VARCHAR(60) NOT NULL,
  name VARCHAR(60) NOT NULL,
  password VARCHAR(60) NOT NULL,
  employee_role VARCHAR(10) NOT NULL,
  is_enabled BIT(1) NOT NULL,
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
  file LONGBLOB NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_evidence),
  CONSTRAINT fk_evidence_id_answer FOREIGN KEY (id_evidence) REFERENCES tb_answer (id_answer));

CREATE TABLE IF NOT EXISTS tb_weight (
  id_weight BIGINT NOT NULL,
  weight DOUBLE NOT NULL,
  PRIMARY KEY (id_weight),
  CONSTRAINT fk_weight_id_answer FOREIGN KEY (id_weight) REFERENCES tb_answer (id_answer));

insert into tb_topic  values(default,"5.1 Políticas para segurança da informação");
insert into tb_topic  values(default,"5.2 Papéis e responsabilidades pela segurança da informação");
insert into tb_topic  values(default,"5.3 Segregação de funções");
insert into tb_topic  values(default,"5.4 Responsabilidades da direção");
insert into tb_topic  values(default,"5.5 Contato com autoridades");
insert into tb_topic  values(default,"5.6 Contato com grupos de interesse especial");
insert into tb_topic  values(default,"5.7 Inteligência de ameaças");
insert into tb_topic  values(default,"5.8 Segurança da informação no gerenciamento de projetos");
insert into tb_topic  values(default,"5.9 Inventário de informações e outros ativos associados");
insert into tb_topic  values(default,"5.10 Uso aceitável de informações e outros ativos associados");
insert into tb_topic  values(default,"5.11 Devolução de ativos");
insert into tb_topic  values(default,"5.12 Classificação das informações");
insert into tb_topic  values(default,"5.13 Rotulagem de informações");
insert into tb_topic  values(default,"5.14 Transferência de informações");
insert into tb_topic  values(default,"5.15 Controle de acesso");
insert into tb_topic  values(default,"5.16 Gestão de identidade");
insert into tb_topic  values(default,"5.17 Informações de autenticação");
insert into tb_topic  values(default,"5.18 Direitos de acesso");
insert into tb_topic  values(default,"5.19 Segurança da informação nas relações com fornecedores ");
insert into tb_topic  values(default,"5.20 Abordagem da segurança da informação nos contratos de fornecedores");
insert into tb_topic  values(default,"5.21 Gestão da segurança da informação na cadeia de fornecimento de TIC");
insert into tb_topic  values(default,"5.22 Monitoramento, análise crítica e gestão de mudanças dos serviços de fornecedores");
insert into tb_topic  values(default,"5.23 Segurança da informação para uso de serviços em nuvem");
insert into tb_topic  values(default,"5.24 Planejamento e preparação da gestão de incidentes de segurança da informação.");
insert into tb_topic  values(default,"5.25 Avaliação e decisão sobre eventos de segurança da informação");
insert into tb_topic  values(default,"5.26 Resposta a incidentes de segurança da informação");
insert into tb_topic  values(default,"5.27 Aprendizado com incidentes de segurança da informação");
insert into tb_topic  values(default,"5.28 Coleta de evidências");
insert into tb_topic  values(default,"5.29 Segurança da informação durante a disrupção");
insert into tb_topic  values(default,"5.30 Prontidão de TIC para continuidade de negócios");
insert into tb_topic  values(default,"5.31 Requisitos legais, estatutários, regulamentares e contratuais");
insert into tb_topic  values(default,"5.32 Direitos de propriedade intelectual");
insert into tb_topic  values(default,"5.33 Proteção de registros");
insert into tb_topic  values(default,"5.34 Privacidade e proteção de DP");
insert into tb_topic  values(default,"5.35 Análise crítica independente da segurança da informação");
insert into tb_topic  values(default,"5.36 Conformidade com políticas, regras e normas para segurança da informação.");
insert into tb_topic  values(default,"5.37 Documentação dos procedimentos de operação");
insert into tb_topic  values(default,"6.1 Seleção");
insert into tb_topic  values(default,"6.2 Termos e condições de contratação");
insert into tb_topic  values(default,"6.3 Conscientização, educação e treinamento em segurança da informação");
insert into tb_topic  values(default,"6.4 Processo disciplinar");
insert into tb_topic  values(default,"6.5 Responsabilidades após encerramento ou mudança da contratação");
insert into tb_topic  values(default,"6.6 Acordos de confidencialidade ou não divulgação");
insert into tb_topic  values(default,"6.7 Trabalho remoto");
insert into tb_topic  values(default,"6.8 Relato de eventos de segurança da informação");
insert into tb_topic  values(default,"7.1 Perímetros de segurança física ");
insert into tb_topic  values(default,"7.2 Entrada física");
insert into tb_topic  values(default,"7.3 Segurança de escritórios, salas e instalações");
insert into tb_topic  values(default,"7.4 Monitoramento de segurança física");
insert into tb_topic  values(default,"7.5 Proteção contra ameaças físicas e ambientais");
insert into tb_topic  values(default,"7.6 Trabalho em áreas seguras");
insert into tb_topic  values(default,"7.7 Mesa limpa e tela limpa");
insert into tb_topic  values(default,"7.8 Localização e proteção de equipamentos");
insert into tb_topic  values(default,"7.9 Segurança de ativos fora das instalações da organização");
insert into tb_topic  values(default,"7.10 Mídia de armazenamento");
insert into tb_topic  values(default,"7.11 Serviços de infraestrutura");
insert into tb_topic  values(default,"7.12 Segurança do cabeamento");
insert into tb_topic  values(default,"7.13 Manutenção de equipamentos");
insert into tb_topic  values(default,"7.14 Descarte seguro ou reutilização de equipamentos");
insert into tb_topic  values(default,"8.1 Dispositivos endpoint do usuário");
insert into tb_topic  values(default,"8.2 Direitos de acessos privilegiados");
insert into tb_topic  values(default,"8.3 Restrição de acesso à informação");
insert into tb_topic  values(default,"8.4 Acesso ao código-fonte");
insert into tb_topic  values(default,"8.5 Autenticação segura");
insert into tb_topic  values(default,"8.6 Gestão de capacidade");
insert into tb_topic  values(default,"8.7 Proteção contra malware");
insert into tb_topic  values(default,"8.8 Gestão de vulnerabilidades técnicas");
insert into tb_topic  values(default,"8.9 Gestão de configuração");
insert into tb_topic  values(default,"8.10 Exclusão de informações");
insert into tb_topic  values(default,"8.11 Mascaramento de dados");
insert into tb_topic  values(default,"8.12 Prevenção de vazamento de dados");
insert into tb_topic  values(default,"8.13 Backup das informações");
insert into tb_topic  values(default,"8.14 Redundancia dos recursos de tratamento de informações");
insert into tb_topic  values(default,"8.15 Log");
insert into tb_topic  values(default,"8.16 Atividade de monitoramento");
insert into tb_topic  values(default,"8.17 Sincronização do relógio");
insert into tb_topic  values(default,"8.18 Uso de programas utilitários privilegiados");
insert into tb_topic  values(default,"8.19 Instalação de softwares em sistemas operacionais");
insert into tb_topic  values(default,"8.20 Segurança de redes");
insert into tb_topic  values(default,"8.21 Segurança dos serviços de redes");
insert into tb_topic  values(default,"8.22 Segregação de redes");
insert into tb_topic  values(default,"8.23 Filtragem da web");
insert into tb_topic  values(default,"8.24 Uso de criptografia");
insert into tb_topic  values(default,"8.25 Ciclo de vida de desenvolvimento seguro");
insert into tb_topic  values(default,"8.26 Requisitos de segurança da aplicação");
insert into tb_topic  values(default,"8.27 Princípios de arquitetura e engenharia de sistemas seguros");
insert into tb_topic  values(default,"8.28 Codificação segura");
insert into tb_topic  values(default,"8.29 Testes de segurança em desenvolvimento e aceitação");
insert into tb_topic  values(default,"8.30 Desenvolvimento terceirizado");
insert into tb_topic  values(default,"8.31 Separação dos ambientes de desenvolvimento, teste e produção");
insert into tb_topic  values(default,"8.32 Gestão de mudanças");
insert into tb_topic  values(default,"8.33 Informações de teste");
insert into tb_topic  values(default,"8.34 Proteção de sistemas de informação durante os testes de auditoria.");


insert into tb_summary  values(	default,"Convém que a política de segurança da informação e as políticas específicas por tema sejam definidas, aprovadas pela direção, publicadas, comunicadas e reconhecidas pelo pessoal pertinente e partes interessadas pertinentes, e analisadas criticamente em intervalos planejados e se ocorrer mudanças significativas."	,	1	);
insert into tb_summary  values(	default,"Convém que os papéis e responsabilidades pela segurança da informação sejam definidos e alocados de acordo com as necessidades da organização",	2	);
insert into tb_summary  values(	default,"Convém que funções conflitantes e áreas de responsabilidade sejam segregados.",	3	);
insert into tb_summary  values(	default,"Convém que a direção requeira que todo o pessoal aplique a segurança da informação de acordo com a política de segurança da informação estabelecida, com as políticas específicas por tema e com os procedimentos da organizaçã",	4	);
insert into tb_summary  values(	default,"Convém que a organização estabeleça e mantenha contato com as autoridades relevantes	",	5	);
insert into tb_summary  values(	default,"Convém que a organização estabeleça e mantenha contato com grupos de interesse especial ou outros fóruns de especialistas em segurança e associações profissionais.",	6	);
insert into tb_summary  values(	default,"Convém que as informações relacionadas a ameaças à segurança da informação sejam coletadas e analisadas para produzir inteligência de ameaças.",	7	);
insert into tb_summary  values(	default,"Convém que a segurança da informação seja integrada ao gerenciamento de projetos.",	8	);
insert into tb_summary  values(	default,"Convém que um inventário de informações e outros ativos associados, incluindo proprietários, seja desenvolvido e mantido."	,	9	);
insert into tb_summary  values(	default,"Convém que regras para o uso aceitável e procedimentos para o manuseio de informações e outros ativos associados sejam identificados, documentados e implementados.",	10	);
insert into tb_summary  values(	default,"Convém que o pessoal e outras partes interessadas, conforme apropriado, devolvam todos os ativos da organização em sua posse após a mudança ou encerramento da contratação ou acordo."	,	11	);
insert into tb_summary  values(	default,"Convém que as informações sejam classificadas de acordo com as necessidades de segurança da informação da organização com base na confidencialidade, integridade, disponibilidade e requisitos relevantes das partes interessadas.",	12	);
insert into tb_summary  values(	default,"Convém que um conjunto adequado de procedimentos para rotulagem de informações seja desenvolvido e implementado de acordo com o esquema de classificação de informações adotado pela organização."	,	13	);
insert into tb_summary  values(	default,"Convém que regras, procedimentos ou acordos de transferência de informações sejam implementados para todos os tipos de recursos de transferência dentro da organização e entre a organização e outras partes."	,	14	);
insert into tb_summary  values(	default,"Convém que as regras para controlar o acesso físico e lógico às informações e outros ativos associados sejam estabelecidas e implementadas com base nos requisitos de segurança da informação e de negócios"	,	15	);
insert into tb_summary  values(	default,"Convém que o ciclo de vida completo das identidades seja gerenciado",	16	);
insert into tb_summary  values(	default,"Convém que a alocação e a gestão de informações de autenticação sejam controladas por uma gestão de processo, incluindo aconselhar o pessoal sobre o manuseio adequado de informações de autenticação.",	17	);
insert into tb_summary  values(	default,"Convém que os direitos de acesso às informações e outros ativos associados sejam provisionados, analisados criticamente, modificados e removidos de acordo com a política de tema específico e regras da organização para o controle de acesso."	,	18	);
insert into tb_summary  values(	default,"Convém que processos e procedimentos sejam definidos e implementados para gerenciar a segurança da informação e os riscos associados com o uso dos produtos ou serviços dos fornecedores"	,	19	);
insert into tb_summary  values(	default,"Convém que requisitos relevantes de segurança da informação sejam estabelecidos e acordados com cada fornecedor com base no tipo de relacionamento com o fornecedor",	20	);
insert into tb_summary  values(	default,"Convém que processos e procedimentos sejam definidos e implementados para gerenciar riscos de segurança da informação associados à cadeia de fornecimento de produtos e serviços de TIC."	,21	);
insert into tb_summary  values(	default,"Convém que a organização monitore, analise criticamente, avalie e gerencie regularmente a mudança nas práticas de segurança da informação dos fornecedores e na prestação de serviços.",	22	);
insert into tb_summary  values(	default,"Convém que os processos de aquisição, uso, gestão e saída de serviços em nuvem sejam estabelecidos de acordo com os requisitos de segurança da informação da organização."	,	23	);
insert into tb_summary  values(	default,"Convém que a organização planeje e se prepare para gerenciar incidentes de segurança da informação definindo, estabelecendo e comunicando processos, papéis e responsabilidades de gestão de incidentes  de segurança da informação."	,	24	);
insert into tb_summary  values(	default,"Convém que a organização avalie os eventos de segurança da informação e decida se categoriza como incidentes de segurança da informação."	,	25	);
insert into tb_summary  values(	default,"Convém que os incidentes de segurança da informação sejam respondidos de acordo com os procedimentos documentados.",	26	);
insert into tb_summary  values(	default,"Convém que o conhecimento adquirido com incidentes de segurança da informação seja usado para fortalecer e melhorar os controles de segurança da informação.",	27	);
insert into tb_summary  values(	default,"Convém que a organização estabeleça e implemente procedimentos para identificação, coleta, aquisição e preservação de evidências relacionadas a eventos de segurança da informação.",	28	);
insert into tb_summary  values(	default,"Convém que a organização planeje como manter a segurança da informação em um nível apropriado durante a disrupção.",	29	);
insert into tb_summary  values(	default,"Convém que a prontidão da TIC seja planejada, implementada, mantida e testada com base nos objetivos de continuidade de negócios e nos requisitos de continuidade da TIC."	,	30	);
insert into tb_summary  values(	default,"Convém que os requisitos legais, estatutários, regulamentares e contratuais pertinentes à segurança da informação e à abordagem da organização para atender a esses requisitos sejam identificados, documentados e atualizados.",	31	);
insert into tb_summary  values(	default,"Convém que a organização implemente procedimentos adequados para proteger os direitos de propriedade intelectual.",32	);
insert into tb_summary  values(	default,"Convém que os registros sejam protegidos contra perdas, destruição, falsificação, acesso não autorizado e liberação não autorizada.",	33	);
insert into tb_summary  values(	default,"Convém que a organização identifique e atenda aos requisitos relativos à preservação da privacidade e proteção de DP de acordo com as leis e regulamentos aplicáveis e requisitos contratuais"	,	34	);
insert into tb_summary  values(	default,"Convém que a abordagem da organização para gerenciar a segurança da informação e sua implementação, incluindo pessoas, processos e tecnologias, seja analisada criticamente de forma independente a intervalos planejados ou quando ocorrem mudanças significativas.",	35	);
insert into tb_summary  values(	default,"Convém que o compliance da política de segurança da informação da organização, políticas, regras e normas de temas específicos seja analisado criticamente a intervalos regulares.",	36	);
insert into tb_summary  values(	default,"Convém que os procedimentos de operação dos recursos de tratamento da informação sejam documentados e disponibilizados para o pessoal que necessite deles.",	37	);
insert into tb_summary  values(	default,"Convém que verificações de antecedentes de todos os candidatos a serem contratados sejam realizadas antes de ingressarem na organização e de modo contínuo, de acordo com as leis, regulamentos e ética aplicáveis e que sejam proporcionais aos requisitos do negócio, à classificação das informações a serem acessadas e aos riscos percebidos.",	38	);
insert into tb_summary  values(	default,"Convém que os contratos trabalhistas declarem as responsabilidades do pessoal e da organização para a segurança da informação.",	39	);
insert into tb_summary  values(	default,"Convém que o pessoal da organização e partes interessadas relevantes recebam treinamento, educação e conscientização em segurança da informação apropriados e atualizações regulares da política de segurança da informação da organização, políticas e procedimentos específicas por tema, pertinentes para as suas funções."	,	40	);
insert into tb_summary  values(	default,"Convém que um processo disciplinar seja formalizado e comunicado, para tomar ações contra pessoal e outras partes interessadas relevantes que tenham cometido uma violação da política de segurança da informação."	,	41	);
insert into tb_summary  values(	default,"Convém que as responsabilidades e funções de segurança da informação que permaneçam válidos após o encerramento ou mudança da contratação sejam definidos, aplicados e comunicados ao pessoal e outras partes interessadas pertinentes."	,	42	);
insert into tb_summary  values(	default,"Convém que acordos de confidencialidade ou não divulgação que reflitam as necessidades da organização para a proteção das informações sejam identificados, documentados, analisados criticamente em intervalos regulares e assinados por pessoal e outras partes interessadas pertinentes.",	43	);
insert into tb_summary  values(	default,"Convém que medidas de segurança sejam implementadas quando as pessoas estiverem trabalhando remotamente para proteger as informações acessadas, tratadas ou armazenadas fora das instalações da organização."	,	44	);
insert into tb_summary  values(	default,"Convém que a organização forneça um mecanismo para que as pessoas relatem eventos de segurança da informação observados ou suspeitos através de canais apropriados em tempo hábil.",	45	);
insert into tb_summary  values(	default,"Convém que perímetros de segurança sejam definidos e usados para proteger áreas que contenham informações e outros ativos associadoConvém que perímetros de segurança sejam definidos e usados para proteger áreas que contenham informações e outros ativos associados.",	46	);
insert into tb_summary  values(	default,"Convém que as áreas seguras sejam protegidas por controles de entrada e pontos de acesso apropriados.",	47	);
insert into tb_summary  values(	default,"Convém que seja projetada e implementada segurança física para escritórios, salas e instalações.",	48	);
insert into tb_summary  values(	default,"Convém que as instalações sejam monitoradas continuamente para acesso físico não autorizado."	,	49	);
insert into tb_summary  values(	default,"Convém que a proteção contra ameaças físicas e ambientais, como desastres naturais e outras ameaças físicas intencionais ou não intencionais à infraestrutura, seja projetada e implementada."	,	50	);
insert into tb_summary  values(	default,"Convém que medidas de segurança para trabalhar em áreas seguras sejam projetadas e implementadas."	,	51	);
insert into tb_summary  values(	default,"Convém que regras de mesa limpa para documentos impressos e mídia de armazenamento removível e regras de tela limpa para os recursos de tratamento das informações sejam definidas e adequadamente aplicadas."	,	52  );
insert into tb_summary  values(	default,"Convém que os equipamentos sejam posicionados com segurança e proteção.",	53	);
insert into tb_summary  values(	default,"Convém proteger os ativos fora das instalações da organização.",	54	);
insert into tb_summary  values(	default,"Convém que as mídias de armazenamento sejam gerenciadas por seu ciclo de vida de aquisição, uso, transporte e descarte, de acordo com o esquema de classificação e com os requisitos de manuseio da organização."	,	55	);
insert into tb_summary  values(	default,"Convém que as instalações de tratamento de informações sejam protegidas contra falhas de energia e outras disrupções causadas por falhas nos serviços de infraestrutura."	,	56	);
insert into tb_summary  values(	default,"Convém que os cabos que transportam energia ou dados ou que sustentam serviços de informação sejam protegidos contra interceptação, interferência ou danos."	,	57	);
insert into tb_summary  values(	default,"Convém que os equipamentos sejam mantidos corretamente para assegurar a disponibilidade, integridade e confidencialidade da informação.",	58	);
insert into tb_summary  values(	default,"Convém que sejam verificados os itens dos equipamentos que contenham mídia de armazenamento, para assegurar que quaisquer dados confidenciais e software licenciado tenham sido removidos ou substituídos com segurança antes do descarte ou reutilização.",	59	);
insert into tb_summary  values(	default,"Convém que as informações armazenadas, tratadas ou acessíveis por meio de dispositivos endpoint do usuário sejam protegidas.",	60	);
insert into tb_summary  values(	default,"Convém restringir e gerenciar a atribuição e o uso de direitos de acessos privilegiados.",	61	);
insert into tb_summary  values(	default,"Convém restringir e gerenciar a atribuição e o uso de direitos de acessos privilegiados.",	62	);
insert into tb_summary  values(	default,"Convém que os acessos de leitura e escrita ao código-fonte, ferramentas de desenvolvimento e bibliotecas de software sejam adequadamente gerenciados.",	63	);
insert into tb_summary  values(	default,"Convém que sejam implementadas tecnologias e procedimentos de autenticação seguros, com base em restrições de acesso à informação e à política específica por tema de controle de acesso.",	64	);
insert into tb_summary  values(	default,"Convém que o uso dos recursos seja monitorado e ajustado de acordo com os requisitos atuais e esperados de capacidade.",	65	);
insert into tb_summary  values(	default,"Convém que a proteção contra malware seja implementada e apoiada pela conscientização adequada do usuário.",	66	);
insert into tb_summary  values(	default,"Convém que informações sobre vulnerabilidades técnicas dos sistemas de informação em uso sejam obtidas, a exposição da organização a tais vulnerabilidades sejam avaliadas e medidas apropriadas sejam tomadas.",	67	);
insert into tb_summary  values(	default,"Convém que as configurações, incluindo configurações de segurança, de hardware, software, serviços e redes sejam estabelecidas, documentadas, implementadas, monitoradas e analisadas criticamente."	,	68	);
insert into tb_summary  values(	default,"Convém que as informações armazenadas em sistemas de informação, dispositivos ou em qualquer outra mídia de armazenamento sejam excluídas quando não forem mais necessárias."	,	69	);
insert into tb_summary  values(	default,"Convém que o mascaramento de dados seja usado de acordo com a política específica por tema da organização sobre controle de acesso e outros requisitos específicos por tema relacionados e requisitos de negócios, levando em consideração a legislação aplicável.",	70	);
insert into tb_summary  values(	default,"Convém que medidas de prevenção de vazamento de dados sejam aplicadas a sistemas, redes e quaisquer outros dispositivos que tratem, armazenem ou transmitam informações sensíveis."	,	71	);
insert into tb_summary  values(	default,"Convém que cópias de backup de informações, software e sistemas sejam mantidas e testadas regularmente de acordo com a política específica por tema acordada sobre backup.	",	72	);
insert into tb_summary  values(	default,"Convém que os recursos de tratamento de informações sejam implementados com redundância suficiente para atender aos requisitos de disponibilidade."	,	73	);
insert into tb_summary  values(	default,"Convém que logs que registrem atividades, exceções, falhas e outros eventos relevantes sejam produzidos, armazenados, protegidos e analisados.	",	74	);
insert into tb_summary  values(	default,"Convém que redes, sistemas e aplicações sejam monitorados por comportamentos anômalos e por ações apropriadas, tomadas para avaliar possíveis incidentes de segurança da informação."	,	75	);
insert into tb_summary  values(	default,"Convém que os relógios dos sistemas de tratamento de informações utilizados pela organização sejam sincronizados com fontes de tempo aprovadas.",	76	);
insert into tb_summary  values(	default,"Convém que o uso de programas utilitários que possam ser capazes de substituir os controles de sistema e aplicações seja restrito e rigorosamente controlado."	,	77	);
insert into tb_summary  values(	default,"Convém que procedimentos e medidas sejam implementados para gerenciar com segurança a instalação de software em sistemas operacionais."	,	78	);
insert into tb_summary  values(	default,"Convém que redes e dispositivos de rede sejam protegidos, gerenciados e controlados para proteger as informações em sistemas e aplicações."	,	79	);
insert into tb_summary  values(	default,"Convém que sejam identificados, implementados e monitorados mecanismos de segurança, níveis de serviço e requisitos de serviços de rede."	,	80	);
insert into tb_summary  values(	default,"Convém que grupos de serviços de informação, usuários e sistemas de informação sejam segregados nas redes da organização."	,	81	);
insert into tb_summary  values(	default,"Convém que o acesso a sites externos seja gerenciado para reduzir a exposição a conteúdo malicioso.	",	82 );
insert into tb_summary  values(	default,"Convém que sejam definidas e implementadas regras para o uso efetivo da criptografia, incluindo o gerenciamento de chaves criptográficas."	,	83  );
insert into tb_summary  values(	default,"Convém que regras para o desenvolvimento seguro de software e sistemas sejam estabelecidas e aplicadas."	,	84	);
insert into tb_summary  values(	default,"Convém que os requisitos de segurança da informação sejam identificados, especificados e aprovados ao desenvolver ou adquirir aplicações",	85	);
insert into tb_summary  values(	default,"Convém que princípios de engenharia para sistemas de segurança sejam estabelecidos, documentados, mantidos e aplicados a qualquer atividade de esenvolvimento de sistemas",	86	);
insert into tb_summary  values(	default,"Convém que princípios de codificação segura sejam aplicados ao desenvolvimento de software.",	87	);
insert into tb_summary  values(	default,"Convém que os processos de teste de segurança sejam definidos e implementados no ciclo de vida do desenvolvimento.	",	88	);
insert into tb_summary  values(	default,"Convém que a organização dirija, monitore e analise criticamente as atividades relacionadas à terceirização de desenvolvimento de sistemas."	,	89	);
insert into tb_summary  values(	default,"Convém que ambientes de desenvolvimento, testes e produção sejam separados e protegidos.",	90	);
insert into tb_summary  values(	default,"Convém que mudanças nos recursos de tratamento de informações e sistemas de informação estejam sujeitas a procedimentos de gestão de mudanças."	,	91	);
insert into tb_summary  values(	default,"Convém que as informações de teste sejam adequadamente selecionadas, protegidas e gerenciadas.",	92	);
insert into tb_summary  values(	default,"Convém que testes de auditoria e outras atividades de garantia envolvendo a avaliação de sistemas operacionais sejam planejados e acordados entre o testador e a gestão apropriada.",	93	);


insert into tb_question  values(default,"a) A organização Possui uma Política de Segurança da Informação?",	1	);
insert into tb_question  values(default,"b) A organização possui políticas especificas documentadas? (Ex. Política de Backup, de controle de acesso)",	1	);
insert into tb_question  values(default,"c) As políticas da organização foram aprovadas pela Direção?",	1	);
insert into tb_question  values(default,"d) A organização evidencia a comunicação das políticas?",	1	);
insert into tb_question  values(default,"e) A organização evidencia o reconhecimento das políticas pelo pessoal pertinente? (Ex. Atas, treinamentos).",	1	);
insert into tb_question  values(default,"f) A organização evidencia o reconhecimento das políticas pelas partes interessadas?",	1	);
insert into tb_question  values(default,"g) A organização evidencia revisão das políticas em período determinado ou em casos significativos?",	1	);
insert into tb_question  values(default,"a) A organização evidencia e formaliza a definição e alocação de papéis e responsabilidades pela segurança da informação conforme item da Política da Segurança da Informação?",	2	);
insert into tb_question  values(default,"b) Os papéis e responsabilidades definidos e alocados atendem as necessidades da organização?",	2	);
insert into tb_question  values(default,"a) A Política de Segurança da Informação define a segregação de função?",	3	);
insert into tb_question  values(default,"b) A empresa possui a descrição de cada cargo que compõe a área de Segurança da Informação?",	3	);
insert into tb_question  values(default,"c) Há registros de aprovações e execuções com credenciais de quem aprovou?",	3	);
insert into tb_question  values(default,"a) A direção da empresa formaliza, conscientiza e treina as equipes sobre a necessidade da aplicação da segurança da informação?",	4	);
insert into tb_question  values(default,"b) São realizadas auditorias internas para checar o nível de aplicação da Política de Segurança da Informação, políticas específicas e procedimentos da organização? 	",	4	);
insert into tb_question  values(default,"a) A organização possui uma lista de autoridades?",	5	);
insert into tb_question  values(default,"b) A organização divulga a lista de autoridades de acordo com perfil de cada área?",	5	);
insert into tb_question  values(default,"a) A organização possui uma lista de grupos de interesse especial?",	6	);
insert into tb_question  values(default,"b) A organização divulga a lista de grupos de interesse especial de acordo com perfil de cada área?",	6	);
insert into tb_question  values(default,"a) A organização coleta e cataloga informações relacionadas a ameaças de segurança da informação?",	7	);
insert into tb_question  values(default,"b) Há produção de inteligência de ameaças através da análise de ameaças?",	7	);
insert into tb_question  values(default,"a) O gerenciamento de projetos leva em conta questões de segurança da informação?",	8	);
insert into tb_question  values(default,"a) A organização possui inventário de ativos e informações?",	9	);
insert into tb_question  values(default,"b) O inventário de ativos e informações é revisado periodicamente?",	9	);
insert into tb_question  values(default,"a) A organização possui regras e procedimentos formais sobre o uso aceitável de informações e outros ativos associados?",	10	);
insert into tb_question  values(default,"b) A organização evidencia o manuseio de informações e ativos conforme regras e procedimentos?",	10	);
insert into tb_question  values(default,"a) A empresa inventaria os ativos?",	11	);
insert into tb_question  values(default,"b) O inventário de ativos é gerido?",	11	);
insert into tb_question  values(default,"c) A entrega e devolução de ativos é formalizada?",	11	);
insert into tb_question  values(default,"a) A Política de Segurança da Informação discorre sobre a classificação das informações?",	12	);
insert into tb_question  values(default,"b) A organização formaliza a classificação das informações?",	12	);
insert into tb_question  values(default,"a) A organização possui procedimentos formais para rotulagem de informações?",	13	);
insert into tb_question  values(default,"b) A organização possui uma lista de informações rotuladas?",	13	);
insert into tb_question  values(default,"a) A organização possui regras e procedimentos formais de transferência de informações?",	14	);
insert into tb_question  values(default,"b) A organização possui uma lista/ relatório de informações transferidas?",	14	);
insert into tb_question  values(default,"a) A Política de Segurança da Informação discorre sobre controle de acesso físico e lógico?",	15	);
insert into tb_question  values(default,"b) Existem regras formais de controle de acesso físico?",	15	);
insert into tb_question  values(default,"c) Os acessos físicos são registrados?",	15	);
insert into tb_question  values(default,"d) Existem regras formais de controle de acesso lógico?",	15	);
insert into tb_question  values(default,"e) Os acessos lógicos são registrados?",	15	);
insert into tb_question  values(default,"a) A Organização possui alguma ferramenta para gerir o ciclo de vida completo das identidades?",	16	);
insert into tb_question  values(default,"b) A organização possui relatório atualizado das identidades?",	16	);
insert into tb_question  values(default,"a) A organização possui gestão de processo formal relacionada as informações de autenticação e seu manuseio?",	17	);
insert into tb_question  values(default,"b) A organização evidencia a capacitação de pessoal sobre o tema?",	17	);
insert into tb_question  values(default,"a) A organização possui Política de Controle de Acesso físico e lógico?",	18	);
insert into tb_question  values(default,"b) Os acessos as informações e outros ativos são provisionados?",	18	);
insert into tb_question  values(default,"c) A organização analisa de forma crítica o acesso às informações e outros ativos?",	18	);
insert into tb_question  values(default,"d) As modificações e remoções de acesso são formalizadas?",	18	);
insert into tb_question  values(default,"a) A organização possui processos e procedimentos formais de Segurança da Informação nas relações com fornecedores?",	19	);
insert into tb_question  values(default,"b) A organização mapeia riscos relacionados ao uso de produtos ou serviços de fornecedores?",	19	);
insert into tb_question  values(default,"a) A organização possui acordos de requisitos relevantes de segurança da informação com base no tipo de relacionamento com fornecedores?",	20	);
insert into tb_question  values(default,"a) A organização possui processos e procedimentos formais de gerenciamento de riscos de Segurança da Informação na cadeia de fornecimento de TIC?",	21	);
insert into tb_question  values(default,"a) A organização possui alguma ferramenta de monitoramento de práticas de Segurança da Informação de fornecedores e na prestação de serviços?",	22	);
insert into tb_question  values(default,"b) A organização avalia e gerencia as mudanças nas práticas de segurança da informação dos fornecedores e na prestação de serviço?",	22	);
insert into tb_question  values(default,"a) A organização possui processos de aquisição, uso, gestão e saída de serviços em nuvem de acordo com requisitos próprios de segurança da informação?",	23	);
insert into tb_question  values(default,"a) A organização possui processos de gestão de incidentes de segurança da informação definidos formalmente?",	24	);
insert into tb_question  values(default,"b) A organização possui papéis e responsabilidades de gestão de incidentes de segurança da informação definidos formalmente?",	24	);
insert into tb_question  values(default,"c) A organização evidencia a ciência e capacitação das partes envolvidas?",	24	);
insert into tb_question  values(default,"a) A organização avalia os eventos de de segurança da informação para então categorizá-los?",	25	);
insert into tb_question  values(default,"a) A organização possui procedimentos formais de resposta de incidentes de segurança da informação?",	26	);
insert into tb_question  values(default,"b) A organização evidencia que os incidentes de segurança da informação foram respondidos de acordo com os procedimentos pré-estabelecidos?",	26	);
insert into tb_question  values(default,"a) A organização realiza formalmente a análise crítica de incidentes de segurança da informação?",	27	);
insert into tb_question  values(default,"b) A organização revisa os controles de segurança da informação com base nas análises críticas?",	27	);
insert into tb_question  values(default,"a) A organização possui procedimentos de identificação de evidências relacionadas a eventos de SI?",	28	);
insert into tb_question  values(default,"b) A organização possui procedimentos de coleta de evidências relacionadas a eventos de SI?",	28	);
insert into tb_question  values(default,"c) A organização possui procedimentos de aquisição de evidências relacionadas a eventos de SI?",	28	);
insert into tb_question  values(default,"d) A organização possui procedimentos de preservação de evidências relacionadas a eventos de SI?",	28	);
insert into tb_question  values(default,"e) A organização faz a gestão de evidências através de cadeia de custódia?",	28	);
insert into tb_question  values(default,"a) A organização mapeia riscos relacionados a possíveis incidentes que geram disrupção?",	29	);
insert into tb_question  values(default,"b) A organização possui uma lista de autoridades para casos não previstos?",	29	);
insert into tb_question  values(default,"a) A organização possui uma lista de ativos críticos?",	30	);
insert into tb_question  values(default,"b) A organização possui procedimento formal de restauração de ativos?",	30	);
insert into tb_question  values(default,"c) A organização possui site alternativo ou redundância de ativos críticos?",	30	);
insert into tb_question  values(default,"d) A organização realiza testes formais de TIC para continuidade de negócios?",	30	);
insert into tb_question  values(default,"a) A organização mapeia as leis pertinentes a segurança da informação?",	31	);
insert into tb_question  values(default,"b) A organização mapeia estatutos pertinentes a segurança da informação?",	31	);
insert into tb_question  values(default,"c) A organização mapeia regulamentos pertinentes a segurança da informação?",	31	);
insert into tb_question  values(default,"d) A organização mapeia itens contratuais pertinentes a segurança da informação?",	31	);
insert into tb_question  values(default,"a) A Política de Segurança da Informação orienta sobre propriedade intelectual?",	32	);
insert into tb_question  values(default,"b) A instituição inventaria os softwares utilizados?",	32	);
insert into tb_question  values(default,"c) A instituição possui licenças dos softwares utilizados?",	32	);
insert into tb_question  values(default,"a) A organização possui uma Política de Controle de Acesso Físico?",	33	);
insert into tb_question  values(default,"b) A organização possui uma Política de Controle de Acesso Lógico?",	33	);
insert into tb_question  values(default,"c) A organização possui uma Política de Backup?",	33	);
insert into tb_question  values(default,"d) A organização evidencia registros de acesso físico?",	33	);
insert into tb_question  values(default,"e) A organização evidencia registros de acesso lógico?",	33	);
insert into tb_question  values(default,"a) A organização mapeia leis relativas à preservação da privacidade e proteção de dados pessoais?",	34	);
insert into tb_question  values(default,"b) A organização mapeia os regulamentos relativos à preservação da privacidade e proteção de dados pessoais?",	34	);
insert into tb_question  values(default,"c) A organização mapeia requisitos contratuais relativos à preservação da privacidade e proteção de dados pessoais?",	34	);
insert into tb_question  values(default,"d) A organização efetua auditorias internas relacionadas à preservação da privacidade e proteção de dados pessoais?",	34	);
insert into tb_question  values(default,"a) A organização é analisada criticamente por terceiros em relação a segurança da informação em intervalos planejados ou em casos de mudanças significativas?",	35	);
insert into tb_question  values(default,"a) A organização realiza análise crítica do compliance da política, regras e normas para segurança da informação?",	36	);
insert into tb_question  values(default,"a) A organização possui procedimentos operacionais dos recursos de tratamento da informação formalizados?",	37	);
insert into tb_question  values(default,"b) Os procedimentos operacionais estão acessíveis?",	37	);
insert into tb_question  values(default,"c) A organização promove treinamentos a respeito do tema?",	37	);
insert into tb_question  values(default,"a) A empresa verifica os antecedentes profissionais e pessoais dos candidatos?",	38	);
insert into tb_question  values(default,"b) A empresa faz a verificação da exatidão dos dados contidos no curriculum vitae do candidato?",	38	);
insert into tb_question  values(default,"c) E feita a validação da veracidade de diplomas, certificações e carteira de conselhos?",	38	);
insert into tb_question  values(default,"d) É feita a verificação dos documentos entregues pelo candidato? (por exemplo, RG, CPF ou outro documento aceitável emitido pelas autoridades competentes)",	38	);
insert into tb_question  values(default,"e) A empresa verifica de forma continua a validade de certificações e carteira de conselhos?",	38	);
insert into tb_question  values(default,"a) Os papéis e responsabilidades de cada funcionário ou terceiros são formalmente definidos e atribuídos?",	39	);
insert into tb_question  values(default,"b) Existe alguma política de confidencialidade ou não divulgação de informações sensíveis no ato da contratação ou antes que o funcionário tenha acesso a elas?",	39	);
insert into tb_question  values(default,"c) A empresa possui um controle de acesso e classificação de informações?",	39	);
insert into tb_question  values(default,"d) Existe um programa de treinamento em segurança da informação?",	39	);
insert into tb_question  values(default,"e) A empresa aplica algum processo disciplinar em caso de descumprimento das políticas?",	39	);
insert into tb_question  values(default,"a) A empresa Possui uma PSI formal?",	40	);
insert into tb_question  values(default,"b) A alta cúpula da empresa está compromissada com a segurança da informação em toda a organização?",	40	);
insert into tb_question  values(default,"c) Existe algum treinamento sobre esta PSI?",	40	);
insert into tb_question  values(default,"d) neste treinamento são informados procedimentos básicos de segurança da informação por exemplo, segurança de senhas, não compartilhar logins e não deixar computador desbloqueado enquanto não estiver usando.",	40	);
insert into tb_question  values(default,"a) Existe um processo disciplinar definido?",	41	);
insert into tb_question  values(default,"b) O processo disciplinar é fortemente aplicado?",	41	);
insert into tb_question  values(default,"a) Os direitos de acesso são removidos quando o RH solicita?",	42	);
insert into tb_question  values(default,"b) Existe um controle de entrega e devolução de ativos para cada colaborador ao encerramento de contratos?",	42	);
insert into tb_question  values(default,"c) O encerramento de atividade é sincronizado entre a área de atuação e o departamento de recursos humanos?",	42	);
insert into tb_question  values(default,"a) A empresa possui um controle de acesso e classificação de informações a serem protegidas?",	43	);
insert into tb_question  values(default,"b) A empresa possui controle de acordo com as leis vigentes de quanto tempo aquela informação deve ser mantida como sigilosa?",	43	);
insert into tb_question  values(default,"c) Possui um plano de ação em caso de vazamento destas informações?",	43	);
insert into tb_question  values(default,"d) A empresa faz um monitoramento de quais pessoas estão acessando estas informações ou tentou acessá-las?",	43	);
insert into tb_question  values(default,"e) A empresa possuí política de Destruição destes dados?",	43	);
insert into tb_question  values(default,"a) Existem alguns controles relacionados ao trabalho remoto, principalmente no que diz respeito a autenticação dos usuários?",	44	);
insert into tb_question  values(default,"b) A comunicação remota é criptografada?",	44	);
insert into tb_question  values(default,"c) Somente colaboradores autorizados acessam aplicações específicas?",	44	);
insert into tb_question  values(default,"d) Os procedimentos de trabalho remoto são formalmente definidos, comunicados e treinados?",	44	);
insert into tb_question  values(default,"e) Somente colaboradores autorizados acessam aplicações específicas?",	44	);
insert into tb_question  values(default,"f) Existe gerenciamento e monitoração dos acessos remotos, assim como ferramentas de prevenção e detecção de invasões?",	44	);
insert into tb_question  values(default,"g) A autenticação dos usuários remotos é integrada com o gerenciamento de identidade da organização?",	44	);
insert into tb_question  values(default,"h) Existe limitação de horários de conexão e finalizando sessões inativas?",	44	);
insert into tb_question  values(default,"i) Testes de penetração e análises independentes são realizadas periodicamente?",	44	);
insert into tb_question  values(default,"j) A empresa fornece equipamentos adequados para execução das atividades de trabalho remoto?",	44	);
insert into tb_question  values(default,"K) A empresa adota medidas de segurança, como firewalls e proteção contra malware?",	44	);
insert into tb_question  values(default,"a) A empresa monitora os eventos de violação das expectativas de confidencialidade, integridade ou disponibilidade das informações?",	45	);
insert into tb_question  values(default,"b) A empresa monitora as falhas em sistemas causadas por erros humanos?",	45	);
insert into tb_question  values(default,"c) Existe algum monitoramento da segurança física dos locais acesso restrito?",	45	);
insert into tb_question  values(default,"d) Existe monitoramento de tentativas mal sucedidas ou violações de acesso em sistemas críticos?",	45	);
insert into tb_question  values(default,"e) Existe um monitoramento das vulnerabilidades em sistemas da informação?",	45	);
insert into tb_question  values(default,"f) Existe algum monitoramento de infecção por malware?",	45	);
insert into tb_question  values(default,"a) A organização possui perímetros de segurança física definidos para proteger as áreas que contém informações e outros ativos associados?",	46	);
insert into tb_question  values(default,"b) A organização garante que não haja lacunas no perímetro ou áreas onde um arrombamento possa ocorrer facilmente, como telhados externos, paredes, tetos e pisos do local?",	46	);
insert into tb_question  values(default,"c) Possui mecanismos de controle para as portas externas contra acesso não autorizado?",	46	);
insert into tb_question  values(default,"d) A organização alarma, monitora e testa todas as portas de incêndio do perímetro de segurança para estabelecer o nível de resistência requerido, de acordo com as normas adequadas?",	46	);
insert into tb_question  values(default,"e) Os requisitos de segurança da informação que a organização considera importantes estão contemplados por um perímetro de segurança física?",	46	);
insert into tb_question  values(default,"a) A organização controla e restringe o acesso às áreas físicas, incluindo pontos de entrada, entrega e carregamento?",	47	);
insert into tb_question  values(default,"b) Utiliza de mecanismos técnicos utilizados para gerenciar o acesso às áreas onde a informação é tratada ou armazenada?",	47	);
insert into tb_question  values(default,"c) A organização autêntica a identidade dos visitantes e registra suas entradas e saídas?",	47	);
insert into tb_question  values(default,"d)A organização gerencia as chaves físicas ou informações de autenticação para garantir o controle do acesso a áreas seguras ou recursos de tratamento da informação?",	47	);
insert into tb_question  values(default,"a) Possui alguma diretriz para proteger os escritórios, salas e instalações da organização?",	48	);
insert into tb_question  values(default,"b) A organização tem adotado a recomendação de localizar as instalações-chave de maneira a evitar o acesso do público?",	48	);
insert into tb_question  values(default,"c) A organização tem medidas em vigor para evitar que listas de pessoas, guias telefônicos internos e mapas acessíveis on-line identifiquem locais de instalações de tratamento de informações confidenciais facilmente acessíveis para qualquer pessoa não autorizada?",	48	);
insert into tb_question  values(default,"a) A organização monitora fisicamente suas instalações para detectar acesso não autorizado?",	49	);
insert into tb_question  values(default,"b) Utiliza de sistemas de vigilância para monitorar as instalações físicas, incluindo edifícios que abrigam sistemas críticos?",	49	);
insert into tb_question  values(default,"c)A organização protege seus sistemas de monitoramento contra acesso não autorizado?	",	49	);
insert into tb_question  values(default,"d)Quais mecanismos de detecção de intrusão são utilizados pela organização para detectar comportamento suspeito?",	49	);
insert into tb_question  values(default,"e) A organização efetua testes a fim de garantir que seus sistemas de monitoramento e gravação estejam em conformidade com as leis e regulamentos locais, incluindo a legislação de proteção de dados?",	49	);
insert into tb_question  values(default,"a) Foram realizados processos de avaliação de risco para identificar os potenciais consequências das ameaças físicas e ambientais antes do início das operações críticas em um local físico e em intervalos regulares?",	50	);
insert into tb_question  values(default,"b) Foram identificadas ameaças físicas e ambientais relevantes e controles apropriados considerados nos seguintes contextos: fogo, inundações, sobrecargas elétricas, explosivos e armas?",	50	);
insert into tb_question  values(default,"c) A localização e construção das instalações físicas consideraram a topografia local, como elevação apropriada, cursos de água e linhas de falha tectônica, bem como as ameaças urbanas, como locais com alto perfil para atrair manifestação política, atividade criminosa ou ataques terroristas?",	50	);
insert into tb_question  values(default,"d) Foram adotados sistemas capazes de proteger sistemas de informação de servidores e clientes contra sobrecargas elétricas ou eventos semelhantes para minimizar as consequências de tais eventos?",	50	);
insert into tb_question  values(default,"a) As medidas de segurança para trabalhar em áreas seguras foram projetadas e implementadas adequadamente?",	51	);
insert into tb_question  values(default,"b) Todas as atividades ocorrendo na área segura são abrangidas pelas medidas de segurança?",	51	);
insert into tb_question  values(default,"c) São evitados trabalhos não supervisionados em áreas seguras, tanto por razões de segurança quanto para reduzir as chances de atividades maliciosas?",	51	);
insert into tb_question  values(default,"d) São controlados adequadamente o transporte e o uso de dispositivos endpoint do usuário em áreas seguras?",	51	);
insert into tb_question  values(default,"a) A organização estabeleceu e comunicou uma política específica sobre mesa limpa e tela limpa?",	52	);
insert into tb_question  values(default,"b) Os computadores e sistemas foram configurados com um recurso de tempo-limite ou encerramento de sessão automáticos?",	52	);
insert into tb_question  values(default,"c) A organização estabeleceu regras e orientações para a configuração de alertas nas telas?",	52	);
insert into tb_question  values(default,"d) A organização tem procedimentos em vigor ao desocupar instalações?",	52	);
insert into tb_question  values(default,"e) A organização protege os dispositivos endpoint do usuário através de cadeados ou outros meios de segurança quando não estiver em uso ou sem vigilância?",	52	);
insert into tb_question  values(default,"a) Existe um política de posicionamento dos equipamentos?",	53	);
insert into tb_question  values(default,"b) As diretrizes de posicionamento dos equipamentos estão sendo seguidas?",	53	);
insert into tb_question  values(default,"c) As diretrizes de proteção contra ameaças físicas e ambientais estão sendo adotadas?",	53	);
insert into tb_question  values(default,"d) As diretrizes de proteção de equipamentos que tratam informações confidenciais estão sendo seguidas?",	53	);
insert into tb_question  values(default,"e) As instalações de tratamento de informações confidenciais estão sendo protegidas contra riscos de vazamento de informações?",	53	);
insert into tb_question  values(default,"a) Os dispositivos móveis usados fora das instalações da organização são protegidos?",	54	);
insert into tb_question  values(default,"b) A organização mantém um registro da cadeia de custódia dos equipamentos quando forem transferidos entre diferentes indivíduos ou partes interessadas?",	54	);
insert into tb_question  values(default,"c) A organização exige autorização para a remoção de equipamentos das instalações da organização e mantém um registro dessas remoções?",	54	);
insert into tb_question  values(default,"d) A organização exige autorização para a remoção de mídias das instalações da organização e mantém um registro dessas remoções?",	54	);
insert into tb_question  values(default,"a) A organização tem uma política específica sobre o gerenciamento de mídia de armazenamento removível?",	55	);
insert into tb_question  values(default,"b) A organização exige autorização para a remoção de meios de armazenamento e mantém um registro dessas remoções?",	55	);
insert into tb_question  values(default,"c) As informações armazenadas em mídias de armazenamento removíveis são protegidas por técnicas criptográficas?",	55	);
insert into tb_question  values(default,"d) A organização tem procedimentos estabelecidos para reutilização ou descarte seguro de mídia de armazenamento contendo informações confidenciais?",	55	);
insert into tb_question  values(default,"e) A organização registra o descarte de itens sensíveis para manter uma trilha de auditoria?",	55	);
insert into tb_question  values(default,"a) A organização assegura que os equipamentos que apoiam os serviços de infraestrutura sejam configurados, operados e mantidos de acordo com as especificações do fabricante pertinente?",	56	);
insert into tb_question  values(default,"b) A organização assegura que os serviços de infraestrutura sejam avaliados regularmente frente à sua capacidade de atender ao crescimento dos negócios e interações com outros serviços de infraestrutura?",	56	);
insert into tb_question  values(default,"c) A organização assegura que os equipamentos que apoiam os serviços de infraestrutura sejam inspecionados e testados regularmente para assegurar seu bom funcionamento?",	56	);
insert into tb_question  values(default,"d) A organização assegura que a infraestrutura tenha múltiplas fontes com roteamento físico diversificado?",	56	);
insert into tb_question  values(default,"e) A organização assegura que os equipamentos dos serviços de infraestrutura estejam em uma rede separada dos recursos de tratamento de informações, caso conectados a uma rede?",	56	);
insert into tb_question  values(default,"a) O cabeamento de energia e de comunicação da organização está protegido contra interceptação, interferência ou danos?	",	57	);
insert into tb_question  values(default,"b) A organização tem diretrizes para a segurança do cabeamento?",	57	);
insert into tb_question  values(default,"c) Os cabos de energia e de comunicação estão segregados para evitar interferências?",	57	);
insert into tb_question  values(default,"d) São realizadas varreduras técnicas periódicas e inspeções físicas para detectar dispositivos não autorizados anexados aos cabos?",	57	);
insert into tb_question  values(default,"e) São realizadas varreduras técnicas periódicas e inspeções físicas para detectar dispositivos não autorizados anexados aos cabos?",	57	);
insert into tb_question  values(default,"a) A organização tem um programa de manutenção em vigor?",	58	);
insert into tb_question  values(default,"b) A organização segue as recomendações do fornecedor para manutenção de equipamentos?",	58	);
insert into tb_question  values(default,"c) A organização mantém registros de todas as falhas suspeitas ou reais, e de toda a manutenção preventiva e corretiva?",	58	);
insert into tb_question  values(default,"d) O pessoal de manutenção assina um acordo de confidencialidade adequado?",	58	);
insert into tb_question  values(default,"e) A organização fiscaliza o pessoal de manutenção durante a realização da manutenção no local?",	58	);
insert into tb_question  values(default,"f) Existem medidas de segurança aplicadas para ativos fora das instalações da organização, se os equipamentos contendo informações forem retirados das dependências da organização para manutenção?",	58	);
insert into tb_question  values(default,"g) A organização inspeciona o equipamento antes de colocá-lo de volta em operação após a manutenção?",	58	);
insert into tb_question  values(default,"a) A organização verifica se os equipamentos de mídias de armazenamento estão presentes antes do descarte ou reutilização?",	59	);
insert into tb_question  values(default,"b) Convém que as mídias de armazenamento que contenham informações confidenciais ou com direitos autorais sejam fisicamente destruídas ou que as informações sejam destruídas, excluídas ou sobre gravadas usando técnicas para tornar as informações originais não recuperáveis?",	59	);
insert into tb_question  values(default,"c) São removidos os rótulos e as marcas que identifiquem a organização ou indiquem a classificação, o proprietário, o sistema ou a rede antes do descarte?",	59	);
insert into tb_question  values(default,"d) Os equipamentos danificados que contenham mídia de armazenamento podem requerer um processo de avaliação de risco para determinar se convém que os itens sejam fisicamente destruídos ao invés de enviados para reparo ou descarte?",	59	);
insert into tb_question  values(default,"e) A organização utiliza a ISO/IEC 27040 para obter detalhes sobre métodos para higienizar a mídia de armazenamento?",	59	);
insert into tb_question  values(default,"f) A organização faz a criptografia completa do disco para reduzir o risco de divulgação de informações confidenciais ao descartar ou reutilizar o equipamento?",	59	);
insert into tb_question  values(default,"a) A organização possui normas e regulamentos para uso de dispositivos endpoint?",	60	);
insert into tb_question  values(default,"b) A organização possui políticas para o uso de dispositivos endpoint?",	60	);
insert into tb_question  values(default,"c) É realizado a classificação das informações?",	60	);
insert into tb_question  values(default,"d) É realizado o gerenciamento dos dispositivos endpoint em especial BYOD?",	60	);
insert into tb_question  values(default,"a) A organização possui controle dos acessos privilegiados?",	61	);
insert into tb_question  values(default,"b) A organização possui arquivos dos acessos privilegiados já concedidos, a serem concedidos e excluídos?",	61	);
insert into tb_question  values(default,"c) Há regra para o uso de acesso privilegiado?",	61	);
insert into tb_question  values(default,"a) A organização possui uma política específica para controle de acesso, incluindo controle de acesso a informações sensíveis?",	62	);
insert into tb_question  values(default,"b) A organização possui mecanismos de bloqueio para acesso indevido as informações e ativos?",	62	);
insert into tb_question  values(default,"c) A organização possui uma lista de usuários e grupos de usuários que podem realizar alterações nas informações?",	62	);
insert into tb_question  values(default,"a) A organização possui um sistema de controle de acesso ao código fonte?",	63	);
insert into tb_question  values(default,"b) É realizado a concessão de acesso ao código fonte apenas para os desenvolvedores?",	63	);
insert into tb_question  values(default,"c) A organização possui algum repositório do código fonte?",	63	);
insert into tb_question  values(default,"d) Para acesso as ferramentas de desenvolvimento e a biblioteca de software é realizado alguma autenticação do acesso?",	63	);
insert into tb_question  values(default,"a) A organização possui um sistema de autenticação em múltiplos fatores?",	64	);
insert into tb_question  values(default,"b) É realizado algum bloqueio no login após várias tentativas de acesso incorretas?",	64	);
insert into tb_question  values(default,"c) A organização possui registros dos logins bem e mal sucedidos?",	64	);
insert into tb_question  values(default,"d) A organização possui política de troca de senhas?",	64	);
insert into tb_question  values(default,"a) A organização tem um plano de gestão de capacidade documentado e atualizado?",	65	);
insert into tb_question  values(default,"b) A capacidade dos recursos de TI é monitorada regularmente para garantir que atenda às necessidades de negócios da organização?",	65	);
insert into tb_question  values(default,"c) A organização realiza testes de capacidade e planejamento de capacidade para garantir que os recursos de TI possam lidar com picos de demanda e situações de falha?",	65	);
insert into tb_question  values(default,"d) A organização possui políticas e procedimentos para gerenciar mudanças na capacidade dos recursos de TI, incluindo a avaliação de riscos e impactos?",	65	);
insert into tb_question  values(default,"a) A organização tem políticas e procedimentos documentados para proteção contra malware?",	66	);
insert into tb_question  values(default,"b) A organização possui sistemas de antivírus atualizados e implantados em todos os seus sistemas de TI?",	66	);
insert into tb_question  values(default,"c) A organização possui sistemas de detecção de intrusão e prevenção de intrusão em sua rede?",	66	);
insert into tb_question  values(default,"d) A organização fornece treinamento de conscientização sobre segurança para seus funcionários, incluindo informações sobre a prevenção de malware?",	66	);
insert into tb_question  values(default,"a) A organização possui um inventário de sistemas de informação em uso?",	67	);
insert into tb_question  values(default,"b) A organização tem um processo formalizado para identificar vulnerabilidades em seus sistemas e aplicativos?",	67	);
insert into tb_question  values(default,"c) A organização tem um processo formalizado para avaliar o impacto de vulnerabilidades técnicas identificadas em seus sistemas de informação?",	67	);
insert into tb_question  values(default,"d) A organização tem processos de rastreamento e gerenciamento de correções de vulnerabilidades em seus sistemas de informação?",	67	);
insert into tb_question  values(default,"a) A organização possui um processo formalizado para gerenciamento de configuração, incluindo configurações de segurança, de hardware, software, serviços e redes?",	68	);
insert into tb_question  values(default,"b) A organização mantém um inventário atualizado de hardware, software, serviços e redes em uso?",	68	);
insert into tb_question  values(default,"c) A organização tem uma equipe dedicada ou um responsável pela gestão de configuração?",	68	);
insert into tb_question  values(default,"d) A organização possui um processo formalizado para implementar configurações de segurança em seus sistemas?",	68	);
insert into tb_question  values(default,"a) A organização possui procedimentos para identificar onde as informações são armazenadas em sistemas de informação, dispositivos ou qualquer outra mídia de armazenamento?",	69	);
insert into tb_question  values(default,"b) A organização possui procedimentos para excluir as informações de forma segura quando elas não são mais necessárias?",	69	);
insert into tb_question  values(default,"c) A organização possui procedimentos para lidar com situações em que as informações excluídas precisam ser recuperadas?",	69	);
insert into tb_question  values(default,"a) A política de mascaramento de dados da organização está alinhada com a legislação aplicável?",	70	);
insert into tb_question  values(default,"b) A organização possui procedimentos para identificar quais dados devem ser mascarados e quando isso deve ser feito?",	70	);
insert into tb_question  values(default,"c) A organização realiza testes regulares para garantir que os dados mascarados sejam efetivos para impedir o acesso não autorizado aos dados?",	70	);
insert into tb_question  values(default,"a) A organização possui uma política de prevenção de vazamento de dados?",	71	);
insert into tb_question  values(default,"b) A política de prevenção de vazamento de dados é divulgada e compreendida pelos funcionários da organização?",	71	);
insert into tb_question  values(default,"c) A organização possui controles para proteger os dados confidenciais e sensíveis, como controle de acesso e criptografia?",	71	);
insert into tb_question  values(default,"a) A organização possui uma política de backup de informações?",	72	);
insert into tb_question  values(default,"b) A organização armazena backups em locais seguros, de acordo com a política de backup de informações?",	72	);
insert into tb_question  values(default,"c) A organização realiza testes regulares de restauração de backup para verificar sua eficácia?",	72	);
insert into tb_question  values(default,"d) A organização mantém registros de backup, incluindo a data do backup, local de armazenamento e informações relacionadas à integridade e eficácia do backup?",	72	);
insert into tb_question  values(default,"a) A organização possui uma política de redundância de recursos de tratamento de informações?",	73	);
insert into tb_question  values(default,"b) A organização realiza testes regulares para garantir a eficácia da redundância dos recursos de tratamento de informações?",	73	);
insert into tb_question  values(default,"c) A organização possui procedimentos para garantir que os recursos de tratamento de informações redundantes não sejam corrompidos ou comprometidos?",	73	);
insert into tb_question  values(default,"a) A organização possui procedimentos documentados para garantir o registro de log?",	74	);
insert into tb_question  values(default,"b) A organização registra as atividades de acesso aos sistemas, incluindo autenticação, autorização e negação de acesso?",	74	);
insert into tb_question  values(default,"c) A organização registra as atividades relacionadas à criação, modificação e exclusão de registros?",	74	);
insert into tb_question  values(default,"a) A organização possui procedimentos documentados para garantir o monitoramento adequado de seus sistemas de informação?",	75	);
insert into tb_question  values(default,"b) A organização monitora seus sistemas de informação em tempo real?",	75	);
insert into tb_question  values(default,"c) A organização monitora os sistemas de rede e os logs de eventos para identificar e responder a atividades suspeitas ou anormais?",	75	);
insert into tb_question  values(default,"d) A organização monitora o uso dos recursos de rede para identificar possíveis violações de políticas de uso aceitável?",	75	);
insert into tb_question  values(default,"a) A organização possui procedimentos documentados para garantir a sincronização adequada do relógio em seus sistemas de informação?",	76	);
insert into tb_question  values(default,"b) A organização utiliza um servidor de tempo confiável e preciso para sincronizar o relógio de seus sistemas de informação?",	76	);
insert into tb_question  values(default,"c) A organização monitora a sincronização do relógio de seus sistemas de informação para garantir que estejam funcionando corretamente?",	76	);
insert into tb_question  values(default,"a) A organização utiliza um processo de aprovação para conceder acesso a programas utilitários privilegiados a funcionários específicos?",	77	);
insert into tb_question  values(default,"b) A organização monitora o uso de programas utilitários privilegiados para garantir que estejam sendo usados ​​de acordo com a política e os procedimentos?",	77	);
insert into tb_question  values(default,"c) A organização possui uma lista de programas utilitários privilegiados aprovados e não aprovados?",	77	);
insert into tb_question  values(default,"a) A organização possui uma política de instalação de software em sistemas operacionais?",	78	);
insert into tb_question  values(default,"b) A política de instalação de software em sistemas operacionais está alinhada com a legislação aplicável?",	78	);
insert into tb_question  values(default,"c) A organização possui procedimentos documentados para garantir a instalação adequada de software em seus sistemas de informação?",	78	);
insert into tb_question  values(default,"a) A organização realiza avaliações de risco de suas redes para identificar possíveis ameaças e vulnerabilidades?",	79	);
insert into tb_question  values(default,"b) A organização utiliza medidas de segurança, como firewalls, para proteger suas redes contra acesso não autorizado?",	79	);
insert into tb_question  values(default,"c) A organização monitora suas redes para detectar atividades maliciosas, como ataques de negação de serviço ou tentativas de invasão?",	79	);
insert into tb_question  values(default,"a) A organização possui processos para garantir que os serviços de rede sejam atualizados regularmente para corrigir vulnerabilidades de segurança?",	80	);
insert into tb_question  values(default,"b) A organização monitora regularmente os serviços de rede para identificar possíveis violações de segurança e está preparada para responder a incidentes de segurança de rede?",	80	);
insert into tb_question  values(default,"c) A organização tem processos de resposta a incidentes estabelecidos para os serviços de rede que incluem procedimentos de backup e recuperação em caso de falhas de serviço ou violações de segurança?",	80	);
insert into tb_question  values(default,"a) A organização realiza segregação de redes?",	81	);
insert into tb_question  values(default,"b) A segregação de redes é aplicada apenas em ambientes internos ou também em ambientes externos, como conexões VPN ou acesso remoto?",	81	);
insert into tb_question  values(default,"c) A organização gerencia o acesso e as permissões de usuários e sistemas de informação em diferentes redes?",	81	);
insert into tb_question  values(default,"a) A organização possui uma política de filtragem da web?",	82	);
insert into tb_question  values(default,"b) A organização realiza monitoramento e registro das tentativas de acesso a sites externos bloqueados?",	82	);
insert into tb_question  values(default,"c) Os sites externos são classificados e filtrados de acordo com o risco?",	82	);
insert into tb_question  values(default,"d) A organização utiliza ferramentas de filtragem da web atualizadas e eficazes para identificar e bloquear sites maliciosos?",	82	);
insert into tb_question  values(default,"a) A organização possui políticas e procedimentos definidos para o uso de criptografia?",	83	);
insert into tb_question  values(default,"b) A organização realiza a seleção dos algoritmos e chaves criptográficas que serão utilizadas?",	83	);
insert into tb_question  values(default,"c) As chaves criptográficas utilizadas pela organização são gerenciadas de forma segura?",	83	);
insert into tb_question  values(default,"d) Existe um processo definido para a geração, distribuição, armazenamento e revogação de chaves?",	83	);
insert into tb_question  values(default,"a) A organização possui um processo formal de desenvolvimento seguro de software e sistemas?",	84	);
insert into tb_question  values(default,"b) O processo de desenvolvimento seguro de software e sistemas é comunicado e entendido por todas as partes envolvidas no desenvolvimento?",	84	);
insert into tb_question  values(default,"c) A organização realiza revisões de código para garantir a segurança do software e sistemas durante o ciclo de vida de desenvolvimento?",	84	);
insert into tb_question  values(default,"d) A organização mantém um registro de vulnerabilidades de softwares e sistemas identificadas e como foram tratadas durante o ciclo de vida de desenvolvimento?",	84	);
insert into tb_question  values(default,"a) A organização tem um processo formal para identificar e especificar os requisitos de segurança da informação para as aplicações que desenvolve ou adquire?",	85	);
insert into tb_question  values(default,"b) Os requisitos de segurança da informação são documentados e aprovados por todas as partes interessadas relevantes?",	85	);
insert into tb_question  values(default,"c) Os requisitos de segurança da informação estão sendo levados em consideração em todas as fases do ciclo de vida do desenvolvimento da aplicação?	",	85	);
insert into tb_question  values(default,"d) As informações sobre os requisitos de segurança da informação são compartilhadas com os fornecedores externos que desenvolvem ou fornecem aplicações para a organização?",	85	);
insert into tb_question  values(default,"a) A organização possui princípios de engenharia para sistemas de segurança estabelecidos e documentados?",	86	);
insert into tb_question  values(default,"b) Os princípios de engenharia para sistemas de segurança são mantidos e aplicados em qualquer atividade de desenvolvimento de sistemas?",	86	);
insert into tb_question  values(default,"c) A organização utiliza padrões de segurança reconhecidos na indústria para orientar a arquitetura e engenharia de sistemas seguros?",	86	);
insert into tb_question  values(default,"a) A equipe de desenvolvimento de software é treinada em princípios de codificação segura?",	87	);
insert into tb_question  values(default,"b) A organização realiza revisões regulares de código para identificar vulnerabilidades e garantir a conformidade com as políticas de codificação segura?",	87	);
insert into tb_question  values(default,"c) A organização garante que as informações confidenciais e sensíveis sejam protegidas durante o desenvolvimento e testes de software?	",	87	);
insert into tb_question  values(default,"a) A organização possui um processo formal de testes de segurança realizados durante o ciclo de vida do desenvolvimento de software?",	88	);
insert into tb_question  values(default,"b) Os testes de segurança são realizados em todas as fases do desenvolvimento (desenvolvimento, teste, homologação e produção)?",	88	);
insert into tb_question  values(default,"c) A organização realiza testes de aceitação de segurança antes da implantação de novos sistemas ou atualizações de sistemas existentes?",	88	);
insert into tb_question  values(default,"a) A organização possui políticas e procedimentos documentados para a terceirização de desenvolvimento de sistemas?",	89	);
insert into tb_question  values(default,"b) A organização realiza uma análise de risco antes de terceirizar o desenvolvimento de sistemas?",	89	);
insert into tb_question  values(default,"c) A organização implementa controles de segurança da informação para gerenciar o acesso e o uso de informações sensíveis por parte dos fornecedores de serviços de desenvolvimento terceirizado?",	89	);
insert into tb_question  values(default,"d) A organização monitora as atividades do fornecedor de serviços de desenvolvimento terceirizado para garantir que estejam em conformidade com as políticas e os requisitos de segurança da informação?",	89	);
insert into tb_question  values(default,"a) A organização possui ambientes de desenvolvimento, teste e produção separados, identificados e protegidos?",	90	);
insert into tb_question  values(default,"b) A organização possui procedimentos para lidar com situações em que os ambientes de desenvolvimento, teste e produção precisam ser mesclados temporariamente?",	90	);
insert into tb_question  values(default,"c) A organização possui uma abordagem de gerenciamento de mudanças para garantir que alterações em um ambiente não afetem os outros ambientes?",	90	);
insert into tb_question  values(default,"a) A organização possui um processo formal de gestão de mudanças?",	91	);
insert into tb_question  values(default,"b) O processo de gestão de mudanças inclui etapas para avaliar os riscos e impactos das mudanças propostas?",	91	);
insert into tb_question  values(default,"c) O processo de gestão de mudanças é comunicado a todas as partes interessadas?",	91	);
insert into tb_question  values(default,"a) Os dados de teste utilizados pela organização estão em conformidade com a legislação e regulamentação aplicáveis à proteção de dados pessoais?",	92	);
insert into tb_question  values(default,"b) A organização protege adequadamente as informações de teste, por exemplo, por meio de criptografia ou outras medidas de segurança?",	92	);
insert into tb_question  values(default,"c) Os funcionários e terceiros que têm acesso às informações de teste são devidamente autorizados e treinados para lidar com esses dados?",	92	);
insert into tb_question  values(default,"d) A organização implementou procedimentos de descarte seguro das informações de teste, após a conclusão dos testes ou do ciclo de vida do sistema?",	92	);
insert into tb_question  values(default,"a) É realizado o planejamento de auditorias internas e externas?",	93	);
insert into tb_question  values(default,"b) Quais são as medidas de segurança adotadas para proteger os sistemas de informação durante os testes de auditoria?",	93	);
insert into tb_question  values(default,"c) É feita a gestão dos registros e evidências dos testes de auditoria para garantir a integridade e confidencialidade das informações envolvidas?	",	93	);

insert into tb_department values(default,"Administrator");
insert into tb_department values(default,"TI");
insert into tb_department values(default,"RH");
insert into tb_department values(default,"DEV");