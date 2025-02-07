package org.alfresco;

import org.alfresco.opensearch.index.OpenSearchConfiguration;
import org.alfresco.repo.service.BatchIndexerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Main application class responsible for initializing and running the Alfresco application.
 */
@SpringBootApplication
@EnableScheduling
public class App implements CommandLineRunner {

    @Autowired
    private OpenSearchConfiguration openSearchConfiguration;

    @Autowired
    private BatchIndexerService batchIndexerService;

    /**
     * Entry point for the application.
     *
     * @param args command line arguments
     */
    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Apply OpenSearch configuration and initiate batch indexing
     *
     * @param args command line arguments
     * @throws Exception if an error occurs during application execution
     */
    @Override
    public void run(String... args) throws Exception {
        openSearchConfiguration.apply();
    }

    /**
     * Configures security filters for HTTP requests.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during security configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

}
