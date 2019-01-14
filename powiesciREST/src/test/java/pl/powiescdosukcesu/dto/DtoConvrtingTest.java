package pl.powiescdosukcesu.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import pl.powiescdosukcesu.dtos.FileEntShortInfoDTO;
import pl.powiescdosukcesu.entities.FileEnt;
import pl.powiescdosukcesu.entities.Genre;

public class DtoConvrtingTest {

	ModelMapper modelMapper = new ModelMapper();
	Converter<FileEnt, FileEntShortInfoDTO> toFileDTO = new Converter<FileEnt, FileEntShortInfoDTO>() {

		@Override
		public FileEntShortInfoDTO convert(MappingContext<FileEnt, FileEntShortInfoDTO> context) {

			context.getDestination().setTitle(context.getSource().getTitle());
			context.getDestination().setBackgroundImage(context.getSource().getBackgroundImage());

			context.getDestination().setFile(context.getSource().getFile());
			context.getDestination().setUser(context.getSource().getUser().getUserName());

			context.getDestination()
					.setGenres(context.getSource().getGenres().stream().map(g -> g.getName()).collect(Collectors.toList()).toArray(String[]::new));

			return context.getDestination();
		}

	};

	@Before
	public void setup() {
		modelMapper.addConverter(toFileDTO);
	}

	@Test
	public void whenConvertToFileEntShortDTO_thenCorrect() {
		String a = "sepignse";
		byte[] image = a.getBytes();
		Set<Genre> genres = new TreeSet<>();
		genres.add(new Genre(1, "Horror"));
		FileEnt file = new FileEnt("Harry Potter", image, genres, image);

		FileEntShortInfoDTO dto = modelMapper.map(file, FileEntShortInfoDTO.class);

		assertEquals("Harry Potter", dto.getTitle());

		assertFalse(dto.getGenres().length == 0);

	}
}