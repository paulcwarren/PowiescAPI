package pl.powiescdosukcesu.config;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import pl.powiescdosukcesu.genre.Genre;
import pl.powiescdosukcesu.genre.GenreRepository;

@Component
public class SpringDataRestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        config.withEntityLookup().forValueRepository(GenreRepository.class, Genre::getName,GenreRepository::findByName);
    }
}
