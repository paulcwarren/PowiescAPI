package pl.powiescdosukcesu.book.files;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;
import org.springframework.stereotype.Component;

@Component
@StoreRestResource
public interface BookContentFileStore extends ContentStore<BookContentFile, String> {
}

