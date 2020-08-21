package technicalblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class TechnicalBlogApp {

    public static void main(String[] args){
        SpringApplication.run(TechnicalBlogApp.class, args);
    }
}
