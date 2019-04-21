package pl.powiescdosukcesu.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.content.rest.config.RestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;

@Configuration
@EntityScan("pl.powiescdosukcesu")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "pl.powiescdosukcesu")
@EnableJpaAuditing
@EnableAsync
@EnableFilesystemStores
@Import(RestConfiguration.class)
public class JPAConfig {

    @Bean
    File filesystemRoot() {

        return new File("/home/robertdev/Documents");
    }

    @Bean
    public FileSystemResourceLoader fsResourceLoader() {

        return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
    }
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        return initializer;
    }

    @Bean
    public DataSource dataSource() {

        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}
