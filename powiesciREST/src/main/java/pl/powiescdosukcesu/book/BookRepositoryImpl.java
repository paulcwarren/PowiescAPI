package pl.powiescdosukcesu.book;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void updateBook(Book book) {
		
		entityManager.merge(book);

	}

}
