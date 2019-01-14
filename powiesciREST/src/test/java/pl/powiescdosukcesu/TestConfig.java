package pl.powiescdosukcesu;

import javax.sql.DataSource;

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

@Configuration
@EntityScan("pl.powiescdosukcesu.entities")
@EnableTransactionManagement
@PropertySource("application.properties")
@EnableJpaRepositories(basePackages = "pl.powiescdosukcesu.repositories")
@EnableAsync
@EnableJpaAuditing
public class TestConfig {
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String url;


	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		return initializer;
	}

	@Bean
	public DataSource dataSource() {

		DataSource dataSource = DataSourceBuilder.create().url(url)
				.driverClassName(driverClassName).build();

		return dataSource;

	}
}
