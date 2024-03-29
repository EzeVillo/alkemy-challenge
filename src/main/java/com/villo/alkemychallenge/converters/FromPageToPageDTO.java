package com.villo.alkemychallenge.converters;

import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class FromPageToPageDTO<T> implements Converter<Page<T>, PageResponseDTO<T>> {
    @Override
    public PageResponseDTO<T> convert(Page<T> source) {
        return PageResponseDTO.<T>builder()
                .content(source.getContent())
                .isEmpty(source.isEmpty())
                .isFirst(source.isFirst())
                .size(source.getSize())
                .isLast(source.isLast())
                .number(source.getNumber())
                .totalElements(source.getTotalElements())
                .totalPages(source.getTotalPages())
                .hasNext(source.hasNext())
                .hasPrevious(source.hasPrevious())
                .build();
    }
}
