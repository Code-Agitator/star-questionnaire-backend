package pers.star.questionnaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StarQuestionnaireApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarQuestionnaireApplication.class, args);
    }
}
