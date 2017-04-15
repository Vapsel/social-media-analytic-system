package smas.core.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Defines packages to scan with {@link org.springframework.data.repository.Repository} annotation
 */
@Configuration
@EnableJpaRepositories(basePackages = "smas.core.database.repository")
public class JpaConfig {

}
