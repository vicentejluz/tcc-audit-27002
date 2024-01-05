package com.fatec.tcc.tccaudit.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fatec.tcc.tccaudit.models.dto.LoginDTO;
import com.fatec.tcc.tccaudit.models.entities.Address;
import com.fatec.tcc.tccaudit.models.entities.Answer;
import com.fatec.tcc.tccaudit.models.entities.Company;
import com.fatec.tcc.tccaudit.models.entities.Department;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.models.entities.Evidence;
import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.models.entities.Summary;
import com.fatec.tcc.tccaudit.models.entities.Topic;
import com.fatec.tcc.tccaudit.repositories.AnswerRepository;
import com.fatec.tcc.tccaudit.repositories.CompanyRepository;
import com.fatec.tcc.tccaudit.repositories.DepartmentRepository;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;
import com.fatec.tcc.tccaudit.repositories.EvidenceRepository;
import com.fatec.tcc.tccaudit.repositories.QuestionRepository;
import com.fatec.tcc.tccaudit.repositories.SummaryRepository;
import com.fatec.tcc.tccaudit.repositories.TopicRepository;
import com.fatec.tcc.tccaudit.security.AuthenticationCentral;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

        @Autowired
        private AuthenticationCentral authenticationCentral;

        @Autowired
        private CompanyRepository companyRepository;

        @Autowired
        private DepartmentRepository departmentRepository;

        @Autowired
        private EmployeeRepository employeeRepository;

        @Autowired
        private TopicRepository topicRepository;

        @Autowired
        private SummaryRepository summaryRepository;

        @Autowired
        private QuestionRepository questionRepository;

        @Autowired
        private AnswerRepository answerRepository;

        @Autowired
        private EvidenceRepository evidenceRepository;

        @Override
        public void run(String... args) throws Exception {
                Address address1 = new Address("Rua A", "São Paulo", "SP", "12345-678");
                Company company1 = new Company("Empresa 1", "12.345.678/0001-01", address1);

                Address address2 = new Address("Rua B", "Rio de Janeiro", "RJ", "98765-432");
                Company company2 = new Company("Empresa 2", "98.765.432/0001-02", address2);

                Address address3 = new Address("Rua C", "Belo Horizonte", "MG", "54321-876");
                Company company3 = new Company("Empresa 3", "54.321.876/0001-03", address3);

                companyRepository.save(company1);

                companyRepository.save(company2);

                Department department = new Department("Administrator");
                Department department1 = new Department("RH");
                Department department2 = new Department("TI");

                departmentRepository.saveAll(Arrays.asList(department, department1, department2));

                Employee employee = authenticationCentral.cryptography(
                                new LoginDTO("admin@admin.com", "Admin123@"), company1);
                Employee employee1 = authenticationCentral.cryptography(
                                new LoginDTO("admin1@admin.com",
                                                "Admin123@"),
                                company2);

                employeeRepository.save(employee);

                employeeRepository.save(employee1);

                company1.getEmployees().addAll(Arrays.asList(employee, employee1));

                department.getEmployees().addAll(Arrays.asList(employee, employee1));

                departmentRepository.save(department);

                companyRepository.save(company1);
                companyRepository.save(company2);
                companyRepository.save(company3);

                Topic topic1 = new Topic("Topic 1");
                Topic topic2 = new Topic("Topic 2");

                topicRepository.save(topic1);

                topicRepository.save(topic2);

                Summary summary1 = new Summary("Teste 1", topic1);
                Summary summary2 = new Summary("Summary 2", topic1);
                Summary summary3 = new Summary("Summary 3", topic1);
                Summary summary4 = new Summary("Summary 4", topic1);
                Summary summary5 = new Summary("Summary 5", topic1);

                Summary summary6 = new Summary("Teste 11", topic2);
                Summary summary7 = new Summary("Summary 21", topic2);
                Summary summary8 = new Summary("Summary 31", topic2);
                Summary summary9 = new Summary("Summary 41", topic2);
                Summary summary10 = new Summary("Summary 51", topic2);

                summaryRepository.saveAll(Arrays.asList(summary1, summary2, summary3, summary4, summary5));

                summaryRepository.saveAll(Arrays.asList(summary6, summary7, summary8, summary9, summary10));

                topic1.getSummaries().addAll(Arrays.asList(summary1, summary2, summary3, summary4, summary5));

                topic2.getSummaries().addAll(Arrays.asList(summary6, summary7, summary8, summary9, summary10));

                topicRepository.save(topic1);

                topicRepository.save(topic2);

                // Crie as 10 perguntas e atribua a um tópico existente
                Question question1 = new Question("Qual é a capital do Brasil?", summary1);
                Question question2 = new Question("Qual é a cor do cavalo branco de Napoleão?", summary1);
                Question question3 = new Question("Qual é a raiz quadrada de 144?", summary2);
                Question question4 = new Question("Quem pintou a Mona Lisa?", summary2);
                Question question5 = new Question("O que significa a sigla HTML?", summary3);
                Question question6 = new Question("Quem é o criador do Facebook?", summary3);
                Question question7 = new Question("Quando foi fundada a cidade de São Paulo?", summary3);
                Question question8 = new Question("Qual é o maior planeta do sistema solar?", summary3);
                Question question9 = new Question("Quem é o protagonista do livro 'O Pequeno Príncipe'?",
                                summary4);
                Question question10 = new Question("Quem é o autor da obra 'Dom Casmurro'?", summary5);

                Question question11 = new Question("Qual é o melhor filme que você já assistiu?", summary6);
                Question question12 = new Question("Qual é a sua comida favorita?", summary6);
                Question question13 = new Question("Você prefere praia ou montanha?", summary7);
                Question question14 = new Question("Qual é a sua música favorita?", summary8);
                Question question15 = new Question("Qual é a sua série favorita?", summary9);
                Question question16 = new Question("Qual é o seu livro favorito?", summary10);
                Question question17 = new Question("Você prefere cachorro ou gato?", summary8);
                Question question18 = new Question("Qual é o seu esporte favorito?", summary8);
                Question question19 = new Question("Qual é o seu destino de viagem dos sonhos?", summary6);
                Question question20 = new Question("Qual é a sua cor favorita?", summary9);
                Question question21 = new Question("Qual é a sua profissão?", summary10);
                Question question22 = new Question("Você prefere café ou chá?", summary7);
                Question question23 = new Question("Qual é o seu jogo de videogame favorito?", summary8);
                Question question24 = new Question("Você gosta mais de filmes de ação ou de comédia?",
                                summary10);
                Question question25 = new Question("Qual é o seu programa de TV favorito?", summary6);
                Question question26 = new Question("Qual é o seu hobby?", summary7);
                Question question27 = new Question("Você prefere filmes legendados ou dublados?", summary8);
                Question question28 = new Question("Qual é o seu estilo de música favorito?", summary9);
                Question question29 = new Question("Qual é o seu escritor favorito?", summary10);
                Question question30 = new Question("Qual é o seu videogame favorito?", summary6);

                questionRepository.saveAll(Arrays.asList(question1, question2, question3, question4, question5,
                                question6, question7, question8, question9, question10));

                questionRepository.saveAll(
                                Arrays.asList(question11, question12, question13, question14, question15, question16,
                                                question17, question18, question19, question20, question21, question22,
                                                question23, question24, question25, question26, question27, question28,
                                                question29, question30));

                Answer answer1 = new Answer(question1, company1, false, false, true, false);
                Answer answer2 = new Answer(question20, company2, true, false, false, false);
                Answer answer3 = new Answer(question5, company1, false, false, true, false);
                Answer answer4 = new Answer(question10, company1, false, false, false, true);
                Answer answer5 = new Answer(question7, company2, false, true, false, false);
                Answer answer6 = new Answer(question2, company1, false, false, false, true);
                Answer answer7 = new Answer(question30, company1, true, false, false, false);
                Answer answer8 = new Answer(question8, company1, false, true, false, false);
                Answer answer9 = new Answer(question7, company1, false, false, true, false);
                Answer answer10 = new Answer(question15, company1, true, false, false, false);

                // Criando 5 instâncias de Evidence
                Evidence evidence1 = new Evidence(answer1, "evidence1.pdf", new byte[] { 1,
                                2, 3 });
                Evidence evidence2 = new Evidence(answer2, "evidence2.pdf", new byte[] { 4,
                                5, 6 });
                Evidence evidence3 = new Evidence(answer3, "evidence3.pdf", new byte[] { 7,
                                8, 9 });
                Evidence evidence4 = new Evidence(answer4, "evidence4.pdf", new byte[] { 10,
                                11, 12 });

                answer1.setEvidence(evidence1);
                answer2.setEvidence(evidence2);
                answer3.setEvidence(evidence3);
                answer4.setEvidence(evidence4);

                evidenceRepository.saveAll(Arrays.asList(evidence1, evidence2, evidence3,
                                evidence4));

                answerRepository.saveAll(Arrays.asList(answer1, answer2, answer3, answer4,
                                answer5, answer6, answer7, answer8, answer9, answer10));

                company1.getAnswers().addAll(
                                Arrays.asList(answer1, answer3, answer4, answer6, answer7, answer8, answer9, answer10));

                company2.getAnswers().addAll(Arrays.asList(answer2, answer5));

                companyRepository.saveAll(Arrays.asList(company1, company2));
        }
}