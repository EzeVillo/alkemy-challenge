package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.converters.FromPageToPageDTO;
import com.villo.alkemychallenge.dtos.GenreDTO;
import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import com.villo.alkemychallenge.entities.Genre;
import com.villo.alkemychallenge.repositories.GenreRepository;
import com.villo.alkemychallenge.utils.helpers.GenreHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
    @Qualifier("modelMapperGenres")
    private final ModelMapper modelMapper;
    private final GenreHelper genreHelper;
    private final FromPageToPageDTO<GenreDTO> fromPageToPageDTO;
    private final GenreRepository genreRepository;

    public GenreDTO create(final GenreDTO genreRequestDTO) {
        var genre = modelMapper.map(genreRequestDTO, Genre.class);
        return modelMapper.map(genreRepository.save(genre), GenreDTO.class);
    }

    public GenreDTO findById(final Long id) {
        return modelMapper.map(genreHelper.findGenreByIdOrThrow(id), GenreDTO.class);
    }

    public PageResponseDTO<GenreDTO> findAll(Pageable pageable) {
        return fromPageToPageDTO.convert(genreRepository.findAll(pageable)
                .map(genre -> modelMapper.map(genre, GenreDTO.class)));
    }
}
