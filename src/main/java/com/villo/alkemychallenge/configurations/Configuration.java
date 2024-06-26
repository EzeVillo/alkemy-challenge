package com.villo.alkemychallenge.configurations;

import com.villo.alkemychallenge.dtos.CharacterDTO;
import com.villo.alkemychallenge.dtos.MovieDTO;
import com.villo.alkemychallenge.dtos.GenreDTO;
import com.villo.alkemychallenge.entities.Character;
import com.villo.alkemychallenge.entities.Movie;
import com.villo.alkemychallenge.entities.Genre;
import com.villo.alkemychallenge.integrations.decoders.CustomErrorBaseFormatDecoder;
import feign.codec.ErrorDecoder;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

@org.springframework.context.annotation.Configuration
@EnableAsync
public class Configuration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapper modelMapperCharacters() {
        var modelMapper = new ModelMapper();
        modelMapper.typeMap(Movie.class, MovieDTO.class).addMappings(newMapping -> newMapping.skip(MovieDTO::setCharacters));
        modelMapper.typeMap(Genre.class, GenreDTO.class).addMappings(newMapping -> newMapping.skip(GenreDTO::setMovies));
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperMovies() {
        var modelMapper = new ModelMapper();
        modelMapper.typeMap(Character.class, CharacterDTO.class).addMappings(newMapping -> newMapping.skip(CharacterDTO::setMovies));
        modelMapper.typeMap(Genre.class, GenreDTO.class).addMappings(newMapping -> newMapping.skip(GenreDTO::setMovies));
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperGenres() {
        var modelMapper = new ModelMapper();
        modelMapper.typeMap(Character.class, CharacterDTO.class).addMappings(newMapping -> newMapping.skip(CharacterDTO::setMovies));
        modelMapper.typeMap(Movie.class, MovieDTO.class).addMappings(newMapping -> newMapping.skip(MovieDTO::setGenres));
        return modelMapper;
    }

    @Bean
    public Validator validator(final AutowireCapableBeanFactory autowireCapableBeanFactory) {
        var validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorBaseFormatDecoder();
    }
}
