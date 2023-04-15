package com.fatec.tcc.tccaudit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;
import com.fatec.tcc.tccaudit.repositories.QuestionRepository;
import com.fatec.tcc.tccaudit.security.AuthenticationCentral;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
        @Autowired
        private QuestionRepository questionRepository;

        @Autowired
        private AuthenticationCentral authenticationCentral;

        @Autowired
        private EmployeeRepository employeeRepository;

        @Override
        public void run(String... args) throws Exception {
                Question teste1 = new Question("teste1", "5.1 Políticas para segurança da informação.", 1, 0, 0, 0);
                Question teste2 = new Question("teste2", "resumo1", 0, 0, 0, 1);
                Question teste3 = new Question("teste3", "5.1 Políticas para segurança da informação.", 0, 0, 0, 0);
                Question teste4 = new Question("teste4", "resumo1", 0, 0, 1, 0);
                Question teste5 = new Question("teste5", "resumo1", 0, 0, 0, 0);
                Question teste6 = new Question("teste6", "5.1 Políticas para segurança da informação.", 0, 0, 0, 0);
                Question teste7 = new Question("teste7", "resumo1", 0, 0, 0, 0);
                Question teste8 = new Question("teste8", "resumo3", 0, 0, 0, 0);
                Question teste9 = new Question("teste9", "resumo1", 0, 0, 1, 0);
                Question teste10 = new Question("teste10", "resumo1", 0, 0, 0, 1);
                Question teste11 = new Question("teste11", "resumo3", 0, 0, 0, 0);
                Question teste12 = new Question("teste12", "5.1 Políticas para segurança da informação.", 0, 0, 0, 0);

                questionRepository.save(teste1);
                questionRepository.save(teste2);
                questionRepository.save(teste3);
                questionRepository.save(teste4);
                questionRepository.save(teste5);
                questionRepository.save(teste6);
                questionRepository.save(teste7);
                questionRepository.save(teste8);
                questionRepository.save(teste9);
                questionRepository.save(teste10);
                questionRepository.save(teste11);
                questionRepository.save(teste12);

                Employee employee1 = authenticationCentral
                                .cryptography(new SignUpDTO("Fulano", "fulano@example.com", "senha123", "admin"));
                Employee employee2 = authenticationCentral
                                .cryptography(new SignUpDTO("Beltrano", "beltrano@example.com", "senha456",
                                                "Infraestrutura"));
                Employee employee3 = authenticationCentral
                                .cryptography(new SignUpDTO("Ciclano", "ciclano@example.com", "senha789", "RH"));
                Employee employee4 = authenticationCentral
                                .cryptography(new SignUpDTO("Diego", "diego@example.com", "senha101112", "TI"));
                Employee employee5 = authenticationCentral
                                .cryptography(new SignUpDTO("Amanda", "amanda@example.com", "senha131415",
                                                "Contabilidade"));

                employeeRepository.save(employee1);
                employeeRepository.save(employee2);
                employeeRepository.save(employee3);
                employeeRepository.save(employee4);
                employeeRepository.save(employee5);

        }

}