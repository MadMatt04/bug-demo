package si.mkejzar.ignite.bugdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(scanBasePackageClasses = IgniteConfig.class, exclude = HibernateJpaAutoConfiguration.class)
public class BugDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugDemoApplication.class, args);
	}
}
