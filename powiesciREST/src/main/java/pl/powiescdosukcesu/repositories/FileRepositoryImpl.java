package pl.powiescdosukcesu.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.powiescdosukcesu.entities.FileEnt;

@Repository
public class FileRepositoryImpl implements FileRepositoryCustom {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void updateFile(FileEnt file) {
		
		entityManager.merge(file);

	}

}
