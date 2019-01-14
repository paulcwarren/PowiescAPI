package pl.powiescdosukcesu.apis;

import org.junit.Test;

import com.google.gson.Gson;

import pl.powiescdosukcesu.entities.PowiesciUser;

public class GsonTest {

	@Test
	public void whatGsonBehavesLike() {
		Gson gson=new Gson();
		PowiesciUser user=new PowiesciUser();
		
		user.setUserName("test");
		System.out.println(gson.toJson(user));
	}
}
