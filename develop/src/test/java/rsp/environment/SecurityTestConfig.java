package rsp.environment;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application-test.properties")
@EnableTransactionManagement
public class SecurityTestConfig {
}
