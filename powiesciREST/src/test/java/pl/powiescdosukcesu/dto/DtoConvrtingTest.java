package pl.powiescdosukcesu.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.modelmapper.ModelMapper;

import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.book.BookShortInfoDTO;
import pl.powiescdosukcesu.book.Genre;

public class DtoConvrtingTest {

	ModelMapper modelMapper = new ModelMapper();

	@Test
	public void whenConvertToFileEntShortDTO_thenCorrect() {
		String a = "sepignse";
		byte[] image = a.getBytes();
		Set<Genre> genres = new HashSet<>();
		genres.add(new Genre("Horror"));
		Book file = new Book("Harry Potter", image, genres, image);

		BookShortInfoDTO dto = modelMapper.map(file, BookShortInfoDTO.class);

		assertEquals("Harry Potter", dto.getTitle());

		assertTrue(dto.getGenres().size() > 0);

	}
}