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
  text text,
  id_topic BIGINT NOT NULL,
  PRIMARY KEY (id_summary),
  INDEX fk_summary_id_topic (id_topic),
  CONSTRAINT fk_summary_id_topic FOREIGN KEY (id_topic) REFERENCES tb_topic (id_topic));

CREATE TABLE IF NOT EXISTS tb_question (
  id_question BIGINT NOT NULL AUTO_INCREMENT,
  question TEXT,
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


insert into tb_topic  values(	default,"	5.1 Políticas para segurança da informação
Controle	");
insert into tb_topic  values(	default,"	5.2 Papéis e responsabilidades pela segurança da informação	");
insert into tb_topic  values(	default,"	5.3 Segregação de funções	");
insert into tb_topic  values(	default,"	5.4 Responsabilidades da direção	");
insert into tb_topic  values(	default,"	5.5 Contato com autoridades  ");
insert into tb_topic  values(	default,"	5.6 Contato com grupos de interesse especial
	");
insert into tb_topic  values(	default,"	5.7 Inteligência de ameaças
");
insert into tb_topic  values(	default,"	5.8 Segurança da informação no gerenciamento de projetos	");
insert into tb_topic  values(	default,"	5.9 Inventário de informações e outros ativos associados	");
insert into tb_topic  values(	default,"	5.10 Uso aceitável de informações e outros ativos associados	");
insert into tb_topic  values(	default,"	5.11 Devolução de ativos	");
insert into tb_topic  values(	default,"	5.12 Classificação das informações	");
insert into tb_topic  values(	default,"	5.13 Rotulagem de informações	");
insert into tb_topic  values(	default,"	5.14 Transferência de informações	");
insert into tb_topic  values(	default,"	5.15 Controle de acesso	");
insert into tb_topic  values(	default,"	5.16 Gestão de identidade	");
insert into tb_topic  values(	default,"	5.17 Informações de autenticação	");
insert into tb_topic  values(	default,"	5.18 Direitos de acesso
");
insert into tb_topic  values(	default,"	5.19 Segurança da informação nas relações com fornecedores
");
insert into tb_topic  values(	default,"	5.20 Abordagem da segurança da informação nos contratos de fornecedores
	");
insert into tb_topic  values(	default,"	5.21 Gestão da segurança da informação na cadeia de fornecimento de TIC
	");
insert into tb_topic  values(	default,"	5.22 Monitoramento, análise crítica e gestão de mudanças dos serviços de fornecedores	");
insert into tb_topic  values(	default,"	5.23 Segurança da informação para uso de serviços em nuvem
");
insert into tb_topic  values(	default,"	5.24 Planejamento e preparação da gestão de incidentes de segurança da informação.
	");
insert into tb_topic  values(	default,	"5.25 Avaliação e decisão sobre eventos de segurança da informação
"	);
insert into tb_topic  values(	default,"	5.26 Resposta a incidentes de segurança da informação
"	);
insert into tb_topic  values(	default,"	5.27 Aprendizado com incidentes de segurança da informação
"	);
insert into tb_topic  values(	default,"	5.28 Coleta de evidências
"	);
insert into tb_topic  values(	default,"	5.29 Segurança da informação durante a disrupção
"	);
insert into tb_topic  values(	default,"	5.30 Prontidão de TIC para continuidade de negócios
");
insert into tb_topic  values(	default,"5.31 Requisitos legais, estatutários, regulamentares e contratuais
");
insert into tb_topic  values(	default,"5.32 Direitos de propriedade intelectual
"	);
insert into tb_topic  values(	default,"	5.33 Proteção de registros
"	);
insert into tb_topic  values(	default,"	5.34 Privacidade e proteção de DP
"	);
insert into tb_topic  values(	default,"	5.35 Análise crítica independente da segurança da informação
");
insert into tb_topic  values(	default,"	5.36 Conformidade com políticas, regras e normas para segurança da informação.
	");
insert into tb_topic  values(	default,"5.37 Documentação dos procedimentos de operação
");
insert into tb_topic  values(	default,"	6.1 Seleção	");
insert into tb_topic  values(	default,"	6.2 Termos e condições de contratação
"	);
insert into tb_topic  values(	default,"	6.3 Conscientização, educação e treinamento em segurança da informação
	");
insert into tb_topic  values(	default,"6.4 Processo disciplinar
	");
insert into tb_topic  values(	default,	"6.5 Responsabilidades após encerramento ou mudança da contratação
	");
insert into tb_topic  values(	default,	"6.6 Acordos de confidencialidade ou não divulgação
	");
insert into tb_topic  values(	default,	"6.7 Trabalho remoto
	");
insert into tb_topic  values(	default,	"6.8 Relato de eventos de segurança da informação
	");
insert into tb_topic  values(	default,"7.1  Inventário dos ativos
	");
insert into tb_topic  values(	default,	"7.1.2 Proprietário dos ativos
");
insert into tb_topic  values(	default,	"7.1.3 Uso aceitável dos ativos
	");
insert into tb_topic  values(	default,	"7.2.1	");
insert into tb_topic  values(	default,	"7.2.2 Rótulos e tratamento da Informação
");
insert into tb_topic  values(	default,"8.1 Dispositivos endpoint do usuário
"	);
insert into tb_topic  values(	default,	"8.2 Direitos de acessos privilegiados
	");
insert into tb_topic  values(	default,	"8.3 Restrição de acesso à informação
	");
insert into tb_topic  values(	default,	"8.4 Acesso ao código-fonte
	");
insert into tb_topic  values(	default,	"8.5 Autenticação segura
	");
insert into tb_topic  values(	default,	"8.6 Gestão de capacidade
	");
insert into tb_topic  values(	default,	"8.7 Proteção contra malware
	");
insert into tb_topic  values(	default,	"8.8 Gestão de vulnerabilidades técnicas
	");
insert into tb_topic  values(	default,	"8.9 Gestão de configuração
Controle
	");
insert into tb_topic  values(	default,	"8.10 Exclusão de informações
");
insert into tb_topic  values(	default,	"8.11 - Mascaramento de dados
	");
insert into tb_topic  values(	default,	"8.12 - Prevenção de vazamento de dados
	");
insert into tb_topic  values(	default,	"8.13 - Backup das informações
	");
insert into tb_topic  values(	default,	"8.14 - Redundancia dos recursos de tratamento de informações
	");
insert into tb_topic  values(	default,	"8.15 - Log
	");
insert into tb_topic  values(	default,	"8.16 - Atividade de monitoramento
	");
insert into tb_topic  values(	default,	"8.17 - Sincronização do relógio
	");
insert into tb_topic  values(	default,	"8.18 - Uso de programas utilitários privilegiados
	");
insert into tb_topic  values(	default,	"8.19 - Instalação de softwares em sistemas operacionais
	");
insert into tb_topic  values(	default,	"8.20 - Segurança de redes
	");
insert into tb_topic  values(	default,	"8.21 - Segurança dos serviços de redes
	");
insert into tb_topic  values(	default,	"8.22 - Segregação de redes	");
insert into tb_topic  values(	default,	"8.23 - Filtragem da web
	");
insert into tb_topic  values(	default,	"8.24 - Uso de criptografia
	");
insert into tb_topic  values(	default,	"8.25 - Ciclo de vida de desenvolvimento seguro
	");
insert into tb_topic  values(	default,	"8.26 - Requisitos de segurança da aplicação
	");
insert into tb_topic  values(	default,	"8.27 - Princípios de arquitetura e engenharia de sistemas seguros
	");
insert into tb_topic  values(	default,	"8.28 Codificação segura

	");
insert into tb_topic  values(	default,	"8.29 Testes de segurança em desenvolvimento e aceitação

");
insert into tb_topic  values(	default,	"8.30 Desenvolvimento terceirizado
	");
insert into tb_topic  values(	default,	"8.31 Separação dos ambientes de desenvolvimento, teste e produção
	");
insert into tb_topic  values(	default,	"8.32 Gestão de mudanças
	");
insert into tb_topic  values(	default,	"8.33 Informações de teste
	");
insert into tb_topic  values(	default,	"8.34 Proteção de sistemas de informação durante os testes de auditoria.	");


insert into tb_summary  values(	default,	"Convém que a política de segurança da informação e as políticas específicas por tema sejam definidas, 
aprovadas pela direção, publicadas, comunicadas e reconhecidas pelo pessoal pertinente e partes 
interessadas pertinentes, e analisadas criticamente em intervalos planejados e se ocorrer mudanças 
signtb_summaryificativas.
"	,	1	);
insert into tb_summary  values(	default,	"Convém que os papéis e responsabilidades pela segurança da informação sejam definidos e alocados 
de acordo com as necessidades da organização"	,	2	);
insert into tb_summary  values(	default,	"Convém que funções conflitantes e áreas de responsabilidade sejam segregados.	",	3	);
insert into tb_summary  values(	default,	"Convém que a direção requeira que todo o pessoal aplique a segurança da informação de acordo 
com a política de segurança da informação estabelecida, com as políticas específicas por tema e com
os procedimentos da organização"	,	4	);
insert into tb_summary  values(	default,	"Convém que a organização estabeleça e mantenha contato com as autoridades relevantes	",	5	);
insert into tb_summary  values(	default,	"Convém que a organização estabeleça e mantenha contato com grupos de interesse especial ou 
outros fóruns de especialistas em segurança e associações profissionais."	,	6	);
insert into tb_summary  values(	default,	"Convém que as informações relacionadas a ameaças à segurança da informação sejam coletadas e 
analisadas para produzir inteligência de ameaças."	,	7	);
insert into tb_summary  values(	default,	"Convém que a segurança da informação seja integrada ao gerenciamento de projetos.	",	8	);
insert into tb_summary  values(	default,	"Convém que um inventário de informações e outros ativos associados, incluindo proprietários, seja 
desenvolvido e mantido."	,	9	);
insert into tb_summary  values(	default,	"Convém que regras para o uso aceitável e procedimentos para o manuseio de informações e outros 
ativos associados sejam identificados, documentados e implementados."	,	10	);
insert into tb_summary  values(	default,	"Convém que o pessoal e outras partes interessadas, conforme apropriado, devolvam todos os ativos 
da organização em sua posse após a mudança ou encerramento da contratação ou acordo."	,	11	);
insert into tb_summary  values(	default,	"Convém que as informações sejam classificadas de acordo com as necessidades de segurança da 
informação da organização com base na confidencialidade, integridade, disponibilidade e requisitos 
relevantes das partes interessadas."	,	12	);
insert into tb_summary  values(	default,	"Convém que um conjunto adequado de procedimentos para rotulagem de informações seja 
desenvolvido e implementado de acordo com o esquema de classificação de informações adotado 
pela organização."	,	13	);
insert into tb_summary  values(	default,	"Convém que regras, procedimentos ou acordos de transferência de informações sejam implementados 
para todos os tipos de recursos de transferência dentro da organização e entre a organização e outras partes."	,	14	);
insert into tb_summary  values(	default,	"Convém que as regras para controlar o acesso físico e lógico às informações e outros ativos associados 
sejam estabelecidas e implementadas com base nos requisitos de segurança da informação e de negócios"	,	15	);
insert into tb_summary  values(	default,	"Convém que o ciclo de vida completo das identidades seja gerenciado	",	16	);
insert into tb_summary  values(	default,	"Convém que a alocação e a gestão de informações de autenticação sejam controladas por uma 
gestão de processo, incluindo aconselhar o pessoal sobre o manuseio adequado de informações de 
autenticação."	,	17	);
insert into tb_summary  values(	default,	"Convém que os direitos de acesso às informações e outros ativos associados sejam provisionados, 
analisados criticamente, modificados e removidos de acordo com a política de tema específico e 
regras da organização para o controle de acesso."	,	18	);
insert into tb_summary  values(	default,	"Convém que processos e procedimentos sejam definidos e implementados para gerenciar a segurança 
da informação e os riscos associados com o uso dos produtos ou serviços dos fornecedores"	,	19	);
insert into tb_summary  values(	default,	"Convém que requisitos relevantes de segurança da informação sejam estabelecidos e acordados
com cada fornecedor com base no tipo de relacionamento com o fornecedor"	,	20	);
insert into tb_summary  values(	default,	"Convém que processos e procedimentos sejam definidos e implementados para gerenciar riscos
de segurança da informação associados à cadeia de fornecimento de produtos e serviços de TIC."	,	21	);
insert into tb_summary  values(	default,	"Convém que a organização monitore, analise criticamente, avalie e gerencie regularmente a mudança 
nas práticas de segurança da informação dos fornecedores e na prestação de serviços."	,	22	);
insert into tb_summary  values(	default,	"Convém que os processos de aquisição, uso, gestão e saída de serviços em nuvem sejam estabelecidos 
de acordo com os requisitos de segurança da informação da organização."	,	23	);
insert into tb_summary  values(	default,	"Convém que a organização planeje e se prepare para gerenciar incidentes de segurança da informação 
definindo, estabelecendo e comunicando processos, papéis e responsabilidades de gestão de incidentes 
de segurança da informação."	,	24	);
insert into tb_summary  values(	default,	"Convém que a organização avalie os eventos de segurança da informação e decida se categoriza 
como incidentes de segurança da informação."	,	25	);
insert into tb_summary  values(	default,	"Convém que os incidentes de segurança da informação sejam respondidos de acordo com os procedimentos documentados.	",	26	);
insert into tb_summary  values(	default,	"Convém que o conhecimento adquirido com incidentes de segurança da informação seja usado para fortalecer e melhorar os controles de segurança da informação.	",	27	);
insert into tb_summary  values(	default,	"Convém que a organização estabeleça e implemente procedimentos para identificação, coleta, 
aquisição e preservação de evidências relacionadas a eventos de segurança da informação."	,	28	);
insert into tb_summary  values(	default,	"Convém que a organização planeje como manter a segurança da informação em um nível apropriado 
durante a disrupção."	,	29	);
insert into tb_summary  values(	default,	"Convém que a prontidão da TIC seja planejada, implementada, mantida e testada com base nos 
objetivos de continuidade de negócios e nos requisitos de continuidade da TIC."	,	30	);
insert into tb_summary  values(	default,	"Convém que os requisitos legais, estatutários, regulamentares e contratuais pertinentes à segurança 
da informação e à abordagem da organização para atender a esses requisitos sejam identificados, 
documentados e atualizados."	,	31	);
insert into tb_summary  values(	default,	"Convém que a organização implemente procedimentos adequados para proteger os direitos de 
propriedade intelectual."	,	32	);
insert into tb_summary  values(	default,	"Convém que os registros sejam protegidos contra perdas, destruição, falsificação, acesso não 
autorizado e liberação não autorizada."	,	33	);
insert into tb_summary  values(	default,	"Convém que a organização identifique e atenda aos requisitos relativos à preservação da privacidade
e proteção de DP de acordo com as leis e regulamentos aplicáveis e requisitos contratuais"	,	34	);
insert into tb_summary  values(	default,	"Convém que a abordagem da organização para gerenciar a segurança da informação e sua 
implementação, incluindo pessoas, processos e tecnologias, seja analisada criticamente de forma 
independente a intervalos planejados ou quando ocorrem mudanças significativas."	,	35	);
insert into tb_summary  values(	default,	"Convém que o compliance da política de segurança da informação da organização, políticas, regras
e normas de temas específicos seja analisado criticamente a intervalos regulares."	,	36	);
insert into tb_summary  values(	default,	"Convém que os procedimentos de operação dos recursos de tratamento da informação sejam 
documentados e disponibilizados para o pessoal que necessite deles."	,	37	);
insert into tb_summary  values(	default,	"Convém que verificações de antecedentes de todos os candidatos a serem contratados sejam realizadas
antes de ingressarem na organização e de modo contínuo, de acordo com as leis, regulamentos e
ética aplicáveis e que sejam proporcionais aos requisitos do negócio, à classificação das informações
a serem acessadas e aos riscos percebidos."	,	38	);
insert into tb_summary  values(	default,	"Convém que os contratos trabalhistas declarem as responsabilidades do pessoal e da organização
para a segurança da informação."	,	39	);
insert into tb_summary  values(	default,	"Convém que o pessoal da organização e partes interessadas relevantes recebam treinamento, educação
e conscientização em segurança da informação apropriados e atualizações regulares da política de
segurança da informação da organização, políticas e procedimentos específicas por tema, pertinentes
para as suas funções."	,	40	);
insert into tb_summary  values(	default,	"Convém que um processo disciplinar seja formalizado e comunicado, para tomar ações contra pessoal
e outras partes interessadas relevantes que tenham cometido uma violação da política de segurança
da informação.
"	,	41	);
insert into tb_summary  values(	default,	"Convém que as responsabilidades e funções de segurança da informação que permaneçam válidos
após o encerramento ou mudança da contratação sejam definidos, aplicados e comunicados ao
pessoal e outras partes interessadas pertinentes.
"	,	42	);
insert into tb_summary  values(	default,	"Convém que acordos de confidencialidade ou não divulgação que reflitam as necessidades da organização
para a proteção das informações sejam identificados, documentados, analisados criticamente em
intervalos regulares e assinados por pessoal e outras partes interessadas pertinentes."	,	43	);
insert into tb_summary  values(	default,	"Convém que medidas de segurança sejam implementadas quando as pessoas estiverem trabalhando
remotamente para proteger as informações acessadas, tratadas ou armazenadas fora das instalações
da organização.
"	,	44	);
insert into tb_summary  values(	default,	"
Controle
Convém que a organização forneça um mecanismo para que as pessoas relatem eventos de segurança
da informação observados ou suspeitos através de canais apropriados em tempo hábil."	,	45	);
insert into tb_summary  values(	default,	"Convém que todos os ativos sejam claramente identificados e um inventário de todos os ativos importantes seja estruturado e mantido	",	46	);
insert into tb_summary  values(	default,	"Convém que todas as informações e ativos associados com os recursos de processamento da informação tenham um proprietário designado por uma parte definida da organização	",	47	);
insert into tb_summary  values(	default,	"Convém que sejam identificadas, documentadas e implementadas regras para que sejam permitidos o uso de informações e de ativos associados aos recusrsos de processamento de informações 	",	48	);
insert into tb_summary  values(	default,	"Convém que as informação seja classificada em termos do seu valor, requisitos legais, sensibilidade e criticidade para a organização.
Essas políticas devem possui convenções para catalogar as informações de classificação inicial e re-clasificação"	,	49	);
insert into tb_summary  values(	default,	"Convem que um conjunto apropriado de procedimentos para rotulação  e tratamento da informação seha definido e implementado de acordo com o esquema de classificação adotado pela organização.
Ele deve abranger tanto os ativos físicos como os ativos lógicos."	,	50	);
insert into tb_summary  values(	default,	"Convém que as informações armazenadas, tratadas ou acessíveis por meio de dispositivos endpoint do usuário sejam protegidas.	",	51	);
insert into tb_summary  values(	default,	"Convém restringir e gerenciar a atribuição e o uso de direitos de acessos privilegiados.",	52	);
insert into tb_summary  values(	default,	"Convém restringir e gerenciar a atribuição e o uso de direitos de acessos privilegiados.	",	53	);
insert into tb_summary  values(	default,	"Convém que os acessos de leitura e escrita ao código-fonte, ferramentas de desenvolvimento e bibliotecas de software sejam adequadamente gerenciados.	",	54	);
insert into tb_summary  values(	default,	"Convém que sejam implementadas tecnologias e procedimentos de autenticação seguros, com base em restrições de acesso à informação e à política específica por tema de controle de acesso.	",	55	);
insert into tb_summary  values(	default,	"Convém que o uso dos recursos seja monitorado e ajustado de acordo com os requisitos atuais e esperados de capacidade.	",	56	);
insert into tb_summary  values(	default,	"Convém que a proteção contra malware seja implementada e apoiada pela conscientização adequada do usuário.	",	57	);
insert into tb_summary  values(	default,	"Convém que informações sobre vulnerabilidades técnicas dos sistemas de informação em uso sejam obtidas, a exposição da organização a tais vulnerabilidades sejam avaliadas e medidas apropriadas sejam tomadas.	",	58	);
insert into tb_summary  values(	default,	"Convém que as configurações, incluindo configurações de segurança, de hardware, software, serviços e redes sejam estabelecidas, documentadas, implementadas, monitoradas e analisadas criticamente."	,	59	);
insert into tb_summary  values(	default,	"Convém que as informações armazenadas em sistemas de informação, dispositivos ou em qualquer outra mídia de armazenamento sejam excluídas quando não forem mais necessárias."	,	60	);
insert into tb_summary  values(	default,	"Convém que o mascaramento de dados seja usado de acordo com a política específica por tema da organização sobre controle de acesso e outros requisitos específicos por tema relacionados e requisitos de negócios, levando em consideração a legislação aplicável.	",	61	);
insert into tb_summary  values(	default,	"Convém que medidas de prevenção de vazamento de dados sejam aplicadas a sistemas, redes e 
quaisquer outros dispositivos que tratem, armazenem ou transmitam informações sensíveis."	,	62	);
insert into tb_summary  values(	default,	"Convém que cópias de backup de informações, software e sistemas sejam mantidas e testadas regularmente de acordo com a política específica por tema acordada sobre backup.	",	63	);
insert into tb_summary  values(	default,	"Convém que os recursos de tratamento de informações sejam implementados com redundância suficiente para atender aos requisitos de disponibilidade."	,	64	);
insert into tb_summary  values(	default,	"Convém que logs que registrem atividades, exceções, falhas e outros eventos relevantes sejam produzidos, armazenados, protegidos e analisados.	",	65	);
insert into tb_summary  values(	default,	"Convém que redes, sistemas e aplicações sejam monitorados por comportamentos anômalos e por ações apropriadas, tomadas para avaliar possíveis incidentes de segurança da informação."	,	66	);
insert into tb_summary  values(	default,	"Convém que os relógios dos sistemas de tratamento de informações utilizados pela organização sejam sincronizados com fontes de tempo aprovadas.	",	67	);
insert into tb_summary  values(	default,	"Convém que o uso de programas utilitários que possam ser capazes de substituir os controles de sistema e aplicações seja restrito e rigorosamente controlado."	,	68	);
insert into tb_summary  values(	default,	"Convém que procedimentos e medidas sejam implementados para gerenciar com segurança a instalação de software em sistemas operacionais."	,	69	);
insert into tb_summary  values(	default,	"Convém que redes e dispositivos de rede sejam protegidos, gerenciados e controlados para proteger as informações em sistemas e aplicações."	,	70	);
insert into tb_summary  values(	default,	"Convém que sejam identificados, implementados e monitorados mecanismos de segurança, níveis de serviço e requisitos de serviços de rede."	,	71	);
insert into tb_summary  values(	default,	"Convém que grupos de serviços de informação, usuários e sistemas de informação sejam segregados nas redes da organização.
"	,	72	);
insert into tb_summary  values(	default,	"Convém que o acesso a sites externos seja gerenciado para reduzir a exposição a conteúdo malicioso.	",	73	);
insert into tb_summary  values(	default,	"Convém que sejam definidas e implementadas regras para o uso efetivo da criptografia, incluindo o gerenciamento de chaves criptográficas."	,	74	);
insert into tb_summary  values(	default,	"Convém que regras para o desenvolvimento seguro de software e sistemas sejam estabelecidas e aplicadas."	,	75	);
insert into tb_summary  values(	default,	"Convém que os requisitos de segurança da informação sejam identificados, especificados e aprovados ao desenvolver ou adquirir aplicações	",	76	);
insert into tb_summary  values(	default,	"Convém que princípios de engenharia para sistemas de segurança sejam estabelecidos, documentados, mantidos e aplicados a qualquer atividade de esenvolvimento de sistemas	",	77	);
insert into tb_summary  values(	default,	"Convém que princípios de codificação segura sejam aplicados ao desenvolvimento de software.	",	78	);
insert into tb_summary  values(	default,	"Convém que os processos de teste de segurança sejam definidos e implementados no ciclo de vida do desenvolvimento.	",	79	);
insert into tb_summary  values(	default,	"Convém que a organização dirija, monitore e analise criticamente as atividades relacionadas à terceirização de desenvolvimento de sistemas."	,	80	);
insert into tb_summary  values(	default,	"Convém que ambientes de desenvolvimento, testes e produção sejam separados e protegidos."	,	81	);
insert into tb_summary  values(	default,	"Convém que mudanças nos recursos de tratamento de informações e sistemas de informação estejam sujeitas a procedimentos de gestão de mudanças."	,	82	);
insert into tb_summary  values(	default,	"Convém que as informações de teste sejam adequadamente selecionadas, protegidas e gerenciadas.	",	83	);
insert into tb_summary  values(	default,	"Convém que testes de auditoria e outras atividades de garantia envolvendo a avaliação de sistemas operacionais sejam planejados e acordados entre o testador e a gestão apropriada.	",	84	);


insert into tb_question  values(	default,"   a1) A empresa Possui uma PSI formal?",	1);
insert into tb_question  values(	default,"	a2) A empresa possui Politicas especificas documentadas? (Ex. Politica de Backup, de controle de acesso)",	1);
insert into tb_question  values(	default,"	b1) A PSI foi aprovada pela Direção?",	1);
insert into tb_question  values(	default,"	a) A PSI define e aloca responsáveis pelas informações e os papéis destes responsáveis?", 2);
insert into tb_question  values(	default,"	b) Os papéis e responsabilidades definidos atendem  as necessidades da organização?", 2);
insert into tb_question  values(	default,"	a) A PSI menciona a segregação de função?",	3);
insert into tb_question  values(	default,"	b) A empresa possui a descrição de cada cargo que compõe a SI?",	3);
insert into tb_question  values(	default,"	c) Há registros de aprovações e execuções com credenciais de quem aprovou?",	3);
insert into tb_question  values(	default,"	a) A direção da empresa formaliza, conscientiza e treina as equipes sobre a necessidade da aplicação da segurança da informação?",	4);
insert into tb_question  values(	default,"	b) São realizadas auditorias internas para checar o nível de aplicação da PSI, políticas específicas e procedimentos da organização? ",	4);
insert into tb_question  values(	default,"	a) a organização possui uma lista de autoridades?	",	5);
insert into tb_question  values(	default,"	a1) a organização divulga a lista de autoridades de acordo com perfil de cada área?	",	5);
insert into tb_question  values(	default,"	a) a organização possui uma lista de grupos de interesse especial?	",	6);
insert into tb_question  values(	default,"	a1) a organização divulga a lista dgrupos de interesse especial de acordo com perfil de cada área?	",	6);
insert into tb_question  values(	default,"	a) a organização cataloga informações relacionadas a ameaças de SI?	",	7	);
insert into tb_question  values(	default,"	b) as ameaças catalogadas são analisadas?	",	7	);
insert into tb_question  values(	default,"	c) há produção de inteligência de ameaças através da análise de ameaças?	",	7	);
insert into tb_question  values(	default,"	a) o gerenciamento de projetos leva em conta questões de segurança da informação?	",	8	);
insert into tb_question  values(	default,"	a) a organização possui inventário de ativos e informações?	",	9	);
insert into tb_question  values(	default,"	b) o inventário de ativos e informações é revisado periodicamente?",	9	);
insert into tb_question  values(	default,"	a) a PSI menciona o uso aceitavel de informações e ativos?	",	10	);
insert into tb_question  values(	default,"	b) existem regras de uso aceitável de informações e ativos associados?	",	10	);
insert into tb_question  values(	default,"	b1) as regras de uso aceitável de informações estão formalizadas?	",	10	);
insert into tb_question  values(	default,"	c) existem procedimentos para uso aceitável de informações e ativos associados?	",	10	);
insert into tb_question  values(	default,"	c1) os procedimentos para uso aceitável de informações e ativos associados?	",	10	);
insert into tb_question  values(	default,"	a) a empresa inventaria os ativos?	",	11	);
insert into tb_question  values(	default,"	b) o inventário de ativos é gerido?	",	11	);
insert into tb_question  values(	default,"	c) a entrega e devolução de ativos é formalizada?	",	11	);
insert into tb_question  values(	default,"	a) a PSI discorre sobre a classificação das informações;	",	12	);
insert into tb_question  values(	default,"	b) a organização possui informações classificadas?"	,	12	);
insert into tb_question  values(	default,"	a) a organização possui procedimento para rotulagem de informações?	",	13	);
insert into tb_question  values(	default,"	b) a organização possui uma lista de informações rotuladas?	",	13	);
insert into tb_question  values(	default,"	a) a organização possui regras de transferência de informações?	",	14	);
insert into tb_question  values(	default,"	b) a organização possui procedimentos de transferência de informações?	",	14	);
insert into tb_question  values(	default,"	c) a organização possui uma lista/ relatório de informações transferidas?	",	14	);
insert into tb_question  values(	default,"	a) a PSI discorre sobre controle de acesso físico?	",	15	);
insert into tb_question  values(	default,"	a1) existem regras de controle de acesso físico?	",	15	);
insert into tb_question  values(	default,"	a2) os acessos físicos são registrados?	",	15	);
insert into tb_question  values(	default,"	b) a PSI discorre sobre controle de acesso lógico?	",	15	);
insert into tb_question  values(	default,"	b1) existem regras de controle de acesso lógico?	",	15	);
insert into tb_question  values(	default,"	b2) os acessos lógicos são registrados?	",	15	);
insert into tb_question  values(	default,"	a) a organização possui alguma ferramenta para gerir o ciclo de vida completo das identidades?	",	16	);
insert into tb_question  values(	default,"	b) a organização possui um relatório atualizado das identidades?	",	16	);
insert into tb_question  values(	default,"	a) a PSI discorre sobre sobre autenticação?	",	17	);
insert into tb_question  values(	default,"	b) a organização controla a gestão do processo de alocação de informações de autenticação?	",	17	);
insert into tb_question  values(	default,"	c) a organização faz o controle da gestão de informações de autenticação?	",	17	);
insert into tb_question  values(	default,"	d) existe procedimento de manuseio de informações de autenticação formalizado?	",	17	);
insert into tb_question  values(	default,"	a) existe menção de direitos de acesso na PSI ou política específica?	",	18	);
insert into tb_question  values(	default,"	b) os acessos às informações são provisionados?	",	18	);
insert into tb_question  values(	default,"	c) a análise crítica de acesso às informações é realizada e formalizada?	",	18	);
insert into tb_question  values(	default,"	d) as modificações e remoções de acesso são formalizadas?	",	18	);
insert into tb_question  values(	default,"	a) existem processos de SI definidos na relação com fornecedores?	",	19	);
insert into tb_question  values(	default,"	a1) estão formalizados?	",	19	);
insert into tb_question  values(	default,"	b) existem procedimentos de SI definidos nas relações com fornecedores?	",	19	);
insert into tb_question  values(	default,"	b1) estão formalizados?	",	19	);
insert into tb_question  values(	default,"	c) a organização mapeia riscos relacionados ao uso de produtos ou serviços de fornecedores?	",	19	);
insert into tb_question  values(	default,"	a) existem requisitos relevantes de segurança da informação envolvendo contratos de fornecedores definidos, estabelecidos e acordados com cada fornecedor?	",	20	);
insert into tb_question  values(	default,"	b) a empresa evidencia requisitos relevantes de segurança da informação em contratos com fornecedores vigentes?	",	20	);
insert into tb_question  values(	default,"	a) a organização mapeia riscos de SI associados à cadeia de fornecimento de produtos e serviços de TIC?	",	21	);
insert into tb_question  values(	default,"	b) a organização possui processos definidos para gerenciamento de riscos de SI?	",	21	);
insert into tb_question  values(	default,"	b1) estão formalizados?	",	21	);
insert into tb_question  values(	default,"	c) a organização possui plano de ação de tratamento dos riscos mapeados	",	21	);
insert into tb_question  values(	default,"	a) a organização possui alguma ferramenta de monitoramento de práticas de SI dos fornecedores e na prestação de serviços?	",	22	);
insert into tb_question  values(	default,"	b) a organização faz análise crítica das mudanças nas práticas de SI dos fornecedores e a formaliza?	",	22	);
insert into tb_question  values(	default,"	c) a organização mapeia riscos das mudanças nas práticas de SI dos fornecedores e na prestação de serviço?	",	22	);
insert into tb_question  values(	default,"	d) a organização possui um plano de ação para tratar os riscos mapeados em relação a mudança nas práticas de SI dos fornecedores e na prestação de serviços	",	22	);
insert into tb_question  values(	default,"	a) a organização possui processo de aquisição de serviços em nuvem definido e formalizado?	",	23	);
insert into tb_question  values(	default,"	b) a organização possui processo de uso de serviços em nuvem definido e formalizado?	",	23	);
insert into tb_question  values(	default,"	c) a organização possui processo de gestão de serviços em nuvem definido e formalizado?	",	23	);
insert into tb_question  values(	default,"	d) a organização possui processo de saída de serviços em nuvem definido e formalizado	",	23	);
insert into tb_question  values(	default,"	a) a organização possui processo de gestão de incidentes de SI definido?	",	24	);
insert into tb_question  values(	default,"	b) a organização possui processo de gestão de incidentes de SI estabelecido?	",	24	);
insert into tb_question  values(	default,"	c) o proceso de gestão de incidentes é comunicado na organização?	",	24	);
insert into tb_question  values(	default,"	d) a organização possui uma equipe de gestão de incidentes de SI?",	24	);
insert into tb_question  values(	default,"	a) a organização categoriza incidentes de SI com base em critérios previamente estabelecidos?	",	25	);
insert into tb_question  values(	default,"	a) a organização possui procedimentos de resposta de incidentes de SI documentados?	",	26	);
insert into tb_question  values(	default,"	b) a organização possui histórico de resposta de incidentes de SI?	",	26	);
insert into tb_question  values(	default,"	a) a organização realiza análise crítica de incidentes de SI e a formaliza?	",	27	);
insert into tb_question  values(	default,"	b) a organização revisa os controles de SI com base nas análises críticas	",	27	);
insert into tb_question  values(	default,"	a) a organização possui procedimentos de identificação de evidências relacionadas a eventos de SI?	",	28	);
insert into tb_question  values(	default,"	b) a organização possui procedimentos de coleta de evidências relacionadas a eventos de SI?	",	28	);
insert into tb_question  values(	default,"	c) a organização possui procedimentos de aquisição de evidências relacionadas a eventos de SI?	",	28	);
insert into tb_question  values(	default,"	d) a organização possui procedimentos de preservação de evidências relacionadas a eventos de SI?	",	28	);
insert into tb_question  values(	default,"	e) a organização faz a gestão de evidencias através de cadeia de custódia?	",	28	);
insert into tb_question  values(	default,"	a) a empresa mapeia possíveis incidentes que geram disrupção?	",	29	);
insert into tb_question  values(	default,"	a1) a organização mapeia riscos do cenário de disrupção de serviços?	",	29	);
insert into tb_question  values(	default,"	b) a organização possui uma lista de autoridades para casos não previstos?	",	29	);
insert into tb_question  values(	default,"	a) a organização possui uma lista de ativos críticos?	",	30	);
insert into tb_question  values(	default,"	b) a organização possui procedimento de restauração de ativos documentado?	",	30	);
insert into tb_question  values(	default,"	c) a organização possui site alternativo ou redundância de ativos críticos?	",	30	);
insert into tb_question  values(	default,"	d) a organização realiza testes de TIC para continuidade de negócios e os formaliza?	",	30	);
insert into tb_question  values(	default,"	a) a organização mapeia as leis pertinentes a SI?	",	31	);
insert into tb_question  values(	default,"	b) a organização mapeia estatutos pertinentes a SI?	",	31	);
insert into tb_question  values(	default,"	c) a instituição mapeia regulamentos pertinentes a SI?	",	31	);
insert into tb_question  values(	default,"	d) a instituição mapeia itens contratuais pertinentes a SI?	",	31	);
insert into tb_question  values(	default,"	a) a PSI orienta sobre propriedade intelectual?	",	32	);
insert into tb_question  values(	default,"	b) a instituição inventaria os softwares utilizados?	",	32	);
insert into tb_question  values(	default,"	c) a instituição possui licenças dos softwares utilizados?	",	32	);
insert into tb_question  values(	default,"	a) a PSI orienta sobre proteção de registros?	",	33	);
insert into tb_question  values(	default,"	b) existe política de Controle de Acesso Físico?	",	33	);
insert into tb_question  values(	default,"	c) existe políticos de controle de Acesso Lógico?	",	33	);
insert into tb_question  values(	default,"	d) existe política de backup?	",	33	);
insert into tb_question  values(	default,"	e) existe registro de acesso físico?	",	33	);
insert into tb_question  values(	default,"	f) existe registro de acesso lógico?	",	33	);
insert into tb_question  values(	default,"	a) a organização mapeia leis relativas à preservação da privacidade e proteção de DP?	",	34	);
insert into tb_question  values(	default,"	b) a organização mapeia os regulamentos relativos à preservação da privacidade e proteção de DP?	",	34	);
insert into tb_question  values(	default,"	c) a organização mapeia requisitos contratuais relativos à preservação da privacidade e proteção de DP?	",	34	);
insert into tb_question  values(	default,"	d) a organização efetua auditorias internas relacionadas à preservação da privacidade e proteção de DP?	",	34	);
insert into tb_question  values(	default,"	a) a PSI orienta sobre análise crítica independente da segurança da informação?	",	35	);
insert into tb_question  values(	default,"	b) a organização analisa criticamente a segurança da informação em intervalos planejados ou em casos de mudanças significativas?	",	35	);
insert into tb_question  values(	default,"	a) a PSI orienta sobre a periodicidade de análise crítica do compliance?	",	36	);
insert into tb_question  values(	default,"	b) a organização realiza análise crítica do compliance da PSI?	",	36	);
insert into tb_question  values(	default,"	c) a organização realiza análise crítica de regras para SI?	",	36	);
insert into tb_question  values(	default,"	D) a organização realiza análise crítica de normas para SI?	",	36	);
insert into tb_question  values(	default,"	a) a organização possui procedimentos operacionais dos recursos de tratamento da informação formalizados?	",	37	);
insert into tb_question  values(	default,"	a1) estão acessíveis?	",	37	);
insert into tb_question  values(	default,"	b) a organização promove treinamentos a respeito do tema?	",	37	);
insert into tb_question  values(	default,"	a1) A empresa verifica os antecedentes profissionais e pessoais dos candidatos?	",	38	);
insert into tb_question  values(	default,"	a2) A empresa faz a verificação da exatidão dos dados contidos no curriculum vitae do candidato?	",	38	);
insert into tb_question  values(	default,"	a3) E feita a validação da veracidade de diplomas, certificações e carteira de conselhos?	",	38	);
insert into tb_question  values(	default,"	a4) É feita a verificação dos documentos entregues pelo candidato? (por exemplo, RG, CPF ou outro documento aceitável emitido pelas autoridades competentes)	",	38	);
insert into tb_question  values(	default,"	b) A empresa verifica de forma continua a validade de certificações e carteira de conselhos?	",	38	);
insert into tb_question  values(	default,"	a1) Os papéis e responsabilidades de cada funcionário ou terceiros são formalmente definidos e atribuídos?	",	39	);
insert into tb_question  values(	default,"	b1) Existe alguma política de confidencialidade ou não divulgação de informações sensíveis no ato da contratação ou antes que o funcionário tenha acesso a elas?	",	39	);
insert into tb_question  values(	default,"	c1) A empresa possui um controle de acesso e classificação de informações?	",	39	);
insert into tb_question  values(	default,"	d1) Existe um programa de treinamento em segurança da informação?	",	39	);
insert into tb_question  values(	default,"	e2) A empresa aplica algum processo disciplinar em caso de descobrimentos?	",	39	);
insert into tb_question  values(	default,"	a1) A empresa Possui uma PSI formal?	",	40	);
insert into tb_question  values(	default,"	b) A alta cúpula da empresa está compromissada com a segurança da informação em toda a organização?	",	40	);
insert into tb_question  values(	default,"	c) Existe algum treinamento sobre esta PSI?	",	40	);
insert into tb_question  values(	default,"	d) neste treinamento são informados procedimentos básicos de segurança da informação por exemplo, segurança de senhas, não compartilhar logins e não deixar computador desbloqueado enquanto não estiver usando.	",	40	);
insert into tb_question  values(	default,"	a1) Existe um processo disciplinar definido ?	",	41	);
insert into tb_question  values(	default,"	b2) O processo disciplinar é  fortemente aplicado ?	",	41	);
insert into tb_question  values(	default,"	a)Os direitos de acesso são removidos quando o RH solicita?	",	42	);
insert into tb_question  values(	default,"	b)Existe um controle de entrega e devolução de ativos para cada colaborador ao encerramento de contratos?	",	42	);
insert into tb_question  values(	default,"	c)O encerramento de atividade é sincronizado entre a área de atuação e o departamento de recursos humanos?	",	42	);
insert into tb_question  values(	default,"	a1) A empresa possui um controle de acesso e classificação de informações a serem protegidas?	",	43	);
insert into tb_question  values(	default,"	a2) A empresa possui controle de acordo com as leis vigentes quanto tempo aquela informação deve ser mantida como sigilosa?	",	43	);
insert into tb_question  values(	default,"	b1) Possui um plano de ação em caso de vazamento destas informações?	",	43	);
insert into tb_question  values(	default,"	b2) Empresa faz um monitoramento de quais pessoas estão acessando estas informações ou tentou acessá-las?	",	43	);
insert into tb_question  values(	default,"	c1) Política de destruição destes dados?	",	43	);
insert into tb_question  values(	default,"	a) Existem alguns controles relacionados ao trabalho remoto, principalmente no que diz respeito a autenticação dos usuários?	",	44	);
insert into tb_question  values(	default,"	b) A comunicação remota é criptografada?	",	44	);
insert into tb_question  values(	default,"	c) Somente colaboradores autorizados acessam aplicações específicas?	",	44	);
insert into tb_question  values(	default,"	d) Os procedimentos de trabalho remoto são formalmente definidos, comunicados e treinados?	",	44	);
insert into tb_question  values(	default,"	e) Somente colaboradores autorizados acessam aplicações específicas?	",	44	);
insert into tb_question  values(	default,"	f) Existe gerenciamento e monitoração dos acessos remotos, assim como ferramentas de prevenção e detecção de invasões?	",	44	);
insert into tb_question  values(	default,"	g) A autenticação dos usuários remotos é integrada com o gerenciamento de identidade da organização?	",	44	);
insert into tb_question  values(	default,"	h) Existe limitação de horários de conexão e finalizando sessões inativas?	",	44	);
insert into tb_question  values(	default,"	i) Testes de penetração e análises independentes são realizadas periodicamente?	",	44	);
insert into tb_question  values(	default,"	j) a empresa fornece equipamentos adequados para execução das atividades de trabalho remoto?	",	44	);
insert into tb_question  values(	default,"	K) a empresa adota medidas de segurança, como firewalls e proteção contra malware ?	",	44	);
insert into tb_question  values(	default,	"a) A empresa monitora os eventos de violação das expectativas de confidencialidade, integridade ou disponibilidade das informações?	",	45	);
insert into tb_question  values(	default,"	b) A empresa monitora as falhas em sistemas causadas por erros humanos?	",	45	);
insert into tb_question  values(	default,"	c) Existe algum monitoramento da segurança física dos locais acesso restrito?	",	45	);
insert into tb_question  values(	default,"	d) Existe monitoramento de tentativas malsucedidas ou violações de acesso em sistemas críticos?	",	45	);
insert into tb_question  values(	default,"	e) Existe um monitoramento das vulnerabilidades em sistemas da informação?	",	45	);
insert into tb_question  values(	default,"	f) Existe algum monitoramento infecção por malware?	",	45	);
insert into tb_question  values(	default,"	a1) A impresa possui muitos ativos importantes?	",	46	);
insert into tb_question  values(	default,"	a2) Existe uma listagem desses ativos para conferência?	",	46	);
insert into tb_question  values(	default,"	b)Quem deveria efetuar a verificação desses ativos?	",	46	);
insert into tb_question  values(	default,"	a1) Cada setor possui uma pessoa responsável por zelar pelos equipamentos?	",	47	);
insert into tb_question  values(	default,"	a2)Essas pessoas fazem um acompanhamento do estado desses ativos?	",	47	);
insert into tb_question  values(	default,"	a1) Existe algum documento que descreve como as informações do ativo devem ser utilizadas?	",	48	);
insert into tb_question  values(	default,"	a2) Nesse documento também especifica as responsabilidades de parceiros e terceiros?	",	48	);
insert into tb_question  values(	default,"	b1) Existe uma manual de boas práticas para o uso do e-mail?	",	48	);
insert into tb_question  values(	default,"	c1) Existe alguma ação de concientização sobre publicar os dados da empresa?	",	48	);
insert into tb_question  values(	default,"	a1) Existe algum movimento de concientização sobre o valor das informações  da empresa	",	49	);
insert into tb_question  values(	default,"	a2)Essas informações são de fácil acesso?	",	49	);
insert into tb_question  values(	default,"	a3) Qual a frequencia da reciclagem dessas informações?	",	49	);
insert into tb_question  values(	default,"	a1) Como é rotulado as informações dentro da empresa	",	50	);
insert into tb_question  values(	default,"	a2)Os terceiros também utilizam esses métodos?	",	50	);
insert into tb_question  values(	default,"	a1) Qual antivirus é utilizado por esta empresa/organização?	",	51	);
insert into tb_question  values(	default,"	a2) Qual a última atualização realizada no antivirus?	",	51	);
insert into tb_question  values(	default,"	b1) É realizado treinamentos de segurança da informação pra colaboradores?	",	51	);
insert into tb_question  values(	default,"	b2) Se sim, quando foi realizado o último treinamento?	",	51	);
insert into tb_question  values(	default,"	c1) Quando foi realizado a última atualização dos softwares de segurança?	",	51	);
insert into tb_question  values(	default,"	a) Como é realizado o controle de acesso privilegiado?	",	52	);
insert into tb_question  values(	default,"	b) É realizado desativação dos acessos privilegiados, após a conclusão da tarefa necessitada?	",	52	);
insert into tb_question  values(	default,"	a) A empresa/organização possui políticas especificas de controle de acesso?	",	53	);
insert into tb_question  values(	default,"	b) Quais são os controles de acesso físico e lógico aplicados para garantir a proteção do acesso não autorizado as informações e ativos?	",	53	);
insert into tb_question  values(	default,"	c) A empresa/organização possui uma lista de usuários e grupos de usuários que podem realizar alterações nas informações?	",	53	);
insert into tb_question  values(	default,"	a) A empresa possui um sistema de gerenciamento de acesso ao código fonte?	",	54	);
insert into tb_question  values(	default,"	b) Como é realizado a concessão de acesso ao código fonte?	",	54	);
insert into tb_question  values(	default,"	c) Existe algum repositório do código fonte?	",	54	);
insert into tb_question  values(	default,"	d) Como é realizado o acesso a ferramentas de desenvolvimento e a biblioteca de software?	",	54	);
insert into tb_question  values(	default,"	a) A empresa possui um sistema de autenticação em dois fatores?	",	55	);
insert into tb_question  values(	default,"	b) É realizado algum bloqueio no login após várias tentativas de acesso incorretas?	",	55	);
insert into tb_question  values(	default,"	c) A empresa/organização possui registros dos logins bem e mal sucedidos?	",	55	);
insert into tb_question  values(	default,"	d) A empresa possui política de troca de senhas? Se sim quando foi realizado o último treinamento a respeito?	",	55	);
insert into tb_question  values(	default,"	a) A empresa possui um sistema de monitoramento dos recursos?	",	56	);
insert into tb_question  values(	default,"	b) Como é realizado o cálculo para produção estimada?	",	56	);
insert into tb_question  values(	default,"	c) São realizados teste de estresse nos sistemas?	",	56	);
insert into tb_question  values(	default,"	d) A empresa possui arquivado os relatórios de produção?	",	56	);
insert into tb_question  values(	default,"	a) A empresa possui algum software ou ferramenta para detecção de malware?	",	57	);
insert into tb_question  values(	default,"	b) São realizados treinamentos sobre malware?	",	57	);
insert into tb_question  values(	default,"	c) Como é realizado a comunicação de novos malwares?	",	57	);
insert into tb_question  values(	default,"	d) Existe algum tipo de bloqueio para sites suspeitos?	",	57	);
insert into tb_question  values(	default,"	a) A empresa possui um histórico de vulnerabilidades que já ocorreram?	",	58	);
insert into tb_question  values(	default,"	b) Quais são os cargos responsáveis pela gestão técnica de vulnerabilidade?	",	58	);
insert into tb_question  values(	default,"	c) Quando foi realizado o último teste de inversão?	",	58	);
insert into tb_question  values(	default,"	d) Qual a última atualização de segurança realizada?	",	58	);
insert into tb_question  values(	default,"	e) Quem realiza a comunicação das vulnerabilidades com os órgãos competentes?	",	58	);
insert into tb_question  values(	default,"	a) A empresa/organização possui políticas de instalação de serviços?	",	59	);
insert into tb_question  values(	default,"	b) É disponibilizado um modelo padrão (manual) para utilização dos serviços, hardware, software, redes e sistemas de segurança?	",	59	);
insert into tb_question  values(	default,"	c) Como é realizado a concessão de acesso privilegiado?	",	59	);
insert into tb_question  values(	default,"	d) A empresa/organização possui ferramentas de monitoramento e gerenciamento dos sistemas?	",	59	);
insert into tb_question  values(	default,"	a) Como é realizado o descarte das informações?	",	60	);
insert into tb_question  values(	default,"	b) A empresa possui algum registro que comprove o descarte dessas informações?	",	60	);
insert into tb_question  values(	default,"	a) A empresa possui procedimentos para mascaramento de dados? Se sim, quais técnicas são utilizadas?	",	61	);
insert into tb_question  values(	default,"	b) Quais dados passam pelo mascaramento?	",	61	);
insert into tb_question  values(	default,"	a) Quais são e como são configuradas as ferramentas de monitoramento para prevenção de vazamento de informações?	",	62	);
insert into tb_question  values(	default,"	b) Como é feita a prevenção de vazamento de dados?	",	62	);
insert into tb_question  values(	default,"	a) A empresa faz backups?	",	63	);
insert into tb_question  values(	default,"	b) Quais os procedimentos?	",	63	);
insert into tb_question  values(	default,"	c) Os backups são testados?	",	63	);
insert into tb_question  values(	default,"	d) Onde são armazenados?	",	63	);
insert into tb_question  values(	default,"	e) são seguros?	",	63	);
insert into tb_question  values(	default,"	a) Quais sistemas utilizam redundância?	",	64	);
insert into tb_question  values(	default,"	b) Quantas horas os equipamentos de redundância suportam?	",	64	);
insert into tb_question  values(	default,"	c) Quais os métodos de redundância?	",	64	);
insert into tb_question  values(	default,"	d) Como e quando são acionados esses métodos?	",	64	);
insert into tb_question  values(	default,"	e) Quem é o responsável por acionar?",	64	);
insert into tb_question  values(	default,"	a) Como são coletados?	",	65	);
insert into tb_question  values(	default,"	b) Quais informações são coletadas?	",	65	);
insert into tb_question  values(	default,"	c) Como são armazenados e protegidos?	",	65	);
insert into tb_question  values(	default,"	d) Quem tem acesso?	",	65	);
insert into tb_question  values(	default,"	e) Quanto tempo ficam armazenados?	",	65	);
insert into tb_question  values(	default,"	a) Como é feito o monitoramento?	",	66	);
insert into tb_question  values(	default,"	b) Quais ferramentas são utilizadas?	",	66	);
insert into tb_question  values(	default,"	c) Quem é o responsável?	",	66	);
insert into tb_question  values(	default,"	d) O que é monitorado?	",	66	);
insert into tb_question  values(	default,"	e) Qual grau de prioridade do monitoramento?	",	66	);
insert into tb_question  values(	default,"	a) Qual o protocolo utilizado?	",	67	);
insert into tb_question  values(	default,"	b) Com que frequência o relógio é ajustado?	",	67	);
insert into tb_question  values(	default,"	c) Quantas fontes de tempos externas são utilizadas?	",	67	);
insert into tb_question  values(	default,"	a) Quais são os programas?	",	68	);
insert into tb_question  values(	default,"	b) Qual a periodicidade com que as permissões do usuario são revistas?	",	68	);
insert into tb_question  values(	default,"	c) Quem pode utilizar esses programas?	",	68	);
insert into tb_question  values(	default,"	a) Quem pode instalar?	",	69	);
insert into tb_question  values(	default,"	b) Quais são esses softwares?	",	69	);
insert into tb_question  values(	default,"	c) De quanto em quanto tempo é verificado se o software é utilizado?	",	69	);
insert into tb_question  values(	default,"	d) Quais os procedimentos para instalação de novo software?	",	69	);
insert into tb_question  values(	default,"	a) Quais as técnicas utilizadas para proteção dos dados que trafegam na rede?	",	70	);
insert into tb_question  values(	default,"	b) As redes são segmentadas?	",	70	);
insert into tb_question  values(	default,"	c) Quais os métodos de segurança física?	",	70	);
insert into tb_question  values(	default,"	d) É necessário autenticação para entrar na rede?	",	70	);
insert into tb_question  values(	default,"	a) Quais são as tecnicas que os provedores utilizam para deixar os serviços seguros?	",	71	);
insert into tb_question  values(	default,"	b) O contrato preve que o provedor do serviço tome medidas para deixa-los mais seguros?	",	71	);
insert into tb_question  values(	default,"	a) São utilizadas VLANs? Se sim, como são configuradas?	",	72	);
insert into tb_question  values(	default,"	b) Quem controla essas divisões?	",	72	);
insert into tb_question  values(	default,"	c) Os computadores de diferentes divisões conseguem se comunicar, se necessário?	",	72	);
insert into tb_question  values(	default,"	a) O acesso a sites não relacionados ao cotidiano da empresa é bloqueado?	",	73	);
insert into tb_question  values(	default,"	b) Quando o usuário tenta acessar sites bloqueados é gerado algum registro? Se sim, o que é coletado?	",	73	);
insert into tb_question  values(	default,"	c) Os colaboradores receberam treinamento de como utilizar internet de forma segura?	",	73	);
insert into tb_question  values(	default,"	a) As informações confidenciais e sensíveis são criptografadas?	",	74	);
insert into tb_question  values(	default,"	b) Qual tipo de criptografia é utilizada dentro da empresa?	",	74	);
insert into tb_question  values(	default,"	c) Quem gerencia a criptografia dos dados?	",	74	);
insert into tb_question  values(	default,"	d) As informações são classificadas de acordo com seu grau de relevância?	",	74	);
insert into tb_question  values(	default,"	e) Os algoritmos criptográficos são escolhidos de acordo com a classificação das informações?	",	74	);
insert into tb_question  values(	default,"	f) Onde são armazenadas as chaves criptográficas?	",	74	);
insert into tb_question  values(	default,"	g) Como é feito o gerenciamento das chaves criptográficas?	",	74	);
insert into tb_question  values(	default,"	a) Os softwares utilizados pela empresa são baseados em técnicas de desenvolvimento seguro?	",	75	);
insert into tb_question  values(	default,"	b) Os softwares utilizados pela empresa são testados?	",	75	);
insert into tb_question  values(	default,"	c) a empresa possui acesso ao código-fonte? Se sim, eles são verificados?	",	75	);
insert into tb_question  values(	default,"	a) Os softwares são testados?	",	76	);
insert into tb_question  values(	default,"	b) Os requisitos de segurança de aplicações foram identificados e especificados?	",	76	);
insert into tb_question  values(	default,"	c) o responsável por realizar a identificação desses requisitos teve apoio de especialistas em segurança da informação?	",	76	);
insert into tb_question  values(	default,"	d) Antes de identificar os requisitos foi realizada uma análise de riscos? 5) Para levantar os requisitos de segurança foi levado em consideração o propósito da aplicação?	",	76	);
insert into tb_question  values(	default,"	a) Todas as camadas de arquitetura possuem segurança?	",	77	);
insert into tb_question  values(	default,"	b) Os princípios de engenharia de segurança são documentados e aplicados junto as atividades?	",	77	);
insert into tb_question  values(	default,"	c) O sistema de informação utilizado possui autenticação do usuário, controle de sessão segura e validação de dados e higienização?	",	77	);
insert into tb_question  values(	default,"	d) São utilizados controles de segurança manuais e automatizados?	",	77	);
insert into tb_question  values(	default,"	e) É especificado onde os controles de segurança devem ser aplicados?	",	77	);
insert into tb_question  values(	default,"	f) É garantido que os controles de segurança aplicados são de fato eficazes?	",	77	);
insert into tb_question  values(	default,"	g) O design foi desenvolvido pensando na segurança	",	77	);
insert into tb_question  values(	default,"	a) Como é realizado o acesso dos desenvolvedores ao código fonte do software?	",	78	);
insert into tb_question  values(	default,"	b) Quais usuários possuem permissão de escrita no software?	",	78	);
insert into tb_question  values(	default,"	c) Como é realizado o registro de erros ou defeitos de codificação do software?	",	78	);
insert into tb_question  values(	default,"	a) A empresa/organização possui padrões de teste de segurança bem definidos?	",	79	);
insert into tb_question  values(	default,"	b) Como é realizado a implementação dos testes de segurança?	",	79	);
insert into tb_question  values(	default,"	c) Como é realizado os testes de segurança em caso de novas implementações de segurança?	",	79	);
insert into tb_question  values(	default,"	d) Como é realizado o processo de aquisição de componentes desenvolvidos por terceiros?	",	79	);
insert into tb_question  values(	default,"	a) Como são elaborados os contratos de terceirização para Desenvolvimento de software?	",	80	);
insert into tb_question  values(	default,"	b) Como a empresa/organização assegura que estão realizando os testes de segurança necessários?	",	80	);
insert into tb_question  values(	default,"	c) Há alguma garantia na entrega do software?	",	80	);
insert into tb_question  values(	default,"	d) Como são concedidos os créditos de autoria do software?	",	80	);
insert into tb_question  values(	default,"	a) A empresa possui sala individuais para cada etapa de desenvolvimento do software até integração ao processo de produção?	",	81	);
insert into tb_question  values(	default,"	b) A empresa possui cronograma das atividades realizadas?	",	81	);
insert into tb_question  values(	default,"	c) Como é realizado o controle de acesso a sala de desenvolvimento e teste?	",	81	);
insert into tb_question  values(	default,"	a) Existe algum procedimento de autorização para mudanças na gestão?	",	82	);
insert into tb_question  values(	default,"	b) Há relatórios de testes realizados antes da implementação da mudança?	",	82	);
insert into tb_question  values(	default,"	c) Como é realizado o procedimento de backup?	",	82	);
insert into tb_question  values(	default,"	d) Em caso de falha no processo de mudança existe alum procedimento de retorno?	",	82	);
insert into tb_question  values(	default,"	a) Como é realizado o controle de acesso aos ambientes de teste?	",	83	);
insert into tb_question  values(	default,"	b) Há algum registro de autorização para realização dos testes?	",	83	);
insert into tb_question  values(	default,"	c) Como é assegurado a funcionalidade do programa/software?	",	83	);
insert into tb_question  values(	default,"	a) Como é realizado o planejamento de auditorias internas?	",	84	);
insert into tb_question  values(	default,"	b) É necessária alguma autorização para realizar a auditoria?	",	84	);
insert into tb_question  values(	default,"	c) Como é realizado o controle de acesso aos dados e software?	",	84	);
insert into tb_question  values(	default,"	d) Como é realizado a exclusão de arquivos do sistema que não são mais uteis?	",	84	);

insert into tb_department  values(	default, "Administrator");
