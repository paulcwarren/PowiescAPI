package pl.powiescdosukcesu.apis;

import org.junit.Test;

import com.google.gson.Gson;

import pl.powiescdosukcesu.appuser.AppUser;

public class GsonTest {

	@Test
	public void whatGsonBehavesLike() {
		Gson gson=new Gson();
		AppUser user=new AppUser();
		
		user.setUserName("test");
		System.out.println(gson.toJson(user));
	}
}
