package pl.powiescdosukcesu.book;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookImpl implements BookCustom {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void updateFile(Book file) {
		
		entityManager.merge(file);

	}

}
