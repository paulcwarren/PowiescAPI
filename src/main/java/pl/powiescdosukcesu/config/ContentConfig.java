package pl.powiescdosukcesu.config;

import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.content.rest.config.RestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.io.File;

@Configuration
@EnableFilesystemStores
@Import(RestConfiguration.class)
@PropertySource("classpath:application.properties")
public class ContentConfig {

    @Bean
    public File filesystemRoot() {

        return new File("/home/robertdev/Documents");
    }

    @Bean
    public FileSystemResourceLoader fsResourceLoader() {

        return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
    }
}
