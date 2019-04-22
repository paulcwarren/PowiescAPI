package pl.powiescdosukcesu.appuser;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;
import org.springframework.stereotype.Component;

@StoreRestResource
@Component
public interface UserImageContentStore extends ContentStore<AppUserImage,String> {
}
