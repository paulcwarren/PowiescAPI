package pl.powiescdosukcesu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableAsync
public class WebConfig {

    private final static int MAX_UPLOAD_SIZE = 100000;

    private final static int MAX_IN_MEMORY_SIZE = 100000;

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
        multipartResolver.setMaxInMemorySize(MAX_IN_MEMORY_SIZE);
        return multipartResolver;
    }

	/*@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("my.gmail@gmail.com");
		mailSender.setPassword("password");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}*/
    /*
     * @Bean public CacheManager cacheManager() { return new
     * ConcurrentMapCacheManager("books"); }
     */

}
