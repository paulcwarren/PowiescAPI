package pl.powiescdosukcesu.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

	private final EntityManager entityManager;

    @Autowired
    public BookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
	public void updateBook(Book book) {
		
		entityManager.merge(book);

	}

}
