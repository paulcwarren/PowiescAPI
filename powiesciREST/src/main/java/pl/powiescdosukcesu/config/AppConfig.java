package pl.powiescdosukcesu.config;

import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.book.BookShortInfoDTO;

@Configuration
@EntityScan("pl.powiescdosukcesu.entities")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "pl.powiescdosukcesu.repositories")
@EnableAsync
@EnableJpaAuditing
public class AppConfig {

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(100000);
		multipartResolver.setMaxInMemorySize(100000);
		return multipartResolver;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		return initializer;
	}

	@Bean
	public DataSource dataSource() {

		DataSource dataSource = DataSourceBuilder.create().username(username).password(password).url(url)
				.driverClassName(driverClassName).build();

		return dataSource;

	}

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper modelMapper = new ModelMapper();

		return modelMapper;
	}
	/*
	 * @Bean public CacheManager cacheManager() { return new
	 * ConcurrentMapCacheManager("files"); }
	 */

}
