package pl.atooris.SocialPostAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.atooris.SocialPostAPI.entity.Post;
import pl.atooris.SocialPostAPI.entity.Role;
import pl.atooris.SocialPostAPI.entity.User;
import pl.atooris.SocialPostAPI.repository.PostRepository;
import pl.atooris.SocialPostAPI.repository.RoleRepository;
import pl.atooris.SocialPostAPI.repository.UserRepository;
import pl.atooris.SocialPostAPI.security.SecurityConstants;

import java.time.LocalDate;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "SocialPost REST API Documentation",
				description = "Documentation to better understand how to use SocialPost REST API",
				version = "v1.1",
				contact = @Contact(
						name = "Łukasz Małysz",
						email = "lukaszmalysz45@gmail.com"
				)
		)
)
@EnableJpaRepositories
@EnableTransactionManagement
@AllArgsConstructor
public class SocialPostApiApplication implements CommandLineRunner {

	PostRepository postRepository;
	UserRepository userRepository;
	RoleRepository roleRepository;


	public static void main(String[] args) {
		SpringApplication.run(SocialPostApiApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {

		Role[] roles = new Role[]{
				new Role(SecurityConstants.ROLE_ADMIN),
				new Role(SecurityConstants.ROLE_USER),
				new Role(SecurityConstants.ROLE_STAFF),
				new Role(SecurityConstants.ROLE_GUEST)
		};

		for (int i = 0; i < roles.length; i++) {
			roleRepository.save(roles[i]);
		}

		User admin = new User("admin", "admin", "admin", bCryptPasswordEncoder().encode("admin-pass"), "admin@gmail.com", LocalDate.of(2000, 10, 3));
		admin.getRoles().add(roleRepository.findByName("ADMIN").get());
		userRepository.save(admin);
	}
}
