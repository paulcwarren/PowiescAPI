package pl.powiescdosukcesu.book.files;

import org.springframework.content.commons.repository.ContentStore;

import java.util.UUID;

public interface BackgroundImageStore extends ContentStore<BackgroundImage, UUID> {
}
