package pl.powiescdosukcesu.appuser.projections;

import org.springframework.data.rest.core.config.Projection;
import pl.powiescdosukcesu.appuser.AppUser;

@Projection(types = {AppUser.class})
public interface NoBooksProjection {

    String getUsername();
    String getEmail();
    String getFirstName();
    String getLastName();
    String getSex();
}
