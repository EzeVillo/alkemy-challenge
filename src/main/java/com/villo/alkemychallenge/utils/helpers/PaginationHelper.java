package com.villo.alkemychallenge.utils.helpers;

import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class PaginationHelper {
    private PaginationHelper() {
        // Constructor privado para ocultar el constructor público implícito
    }

    public static String createHeaderLink(final PageResponseDTO<?> page, final String uri) {
        final var baseUriWithoutPagination = UriComponentsBuilder.fromUriString(uri)
                .replaceQueryParam("page", (Object[]) null)
                .replaceQueryParam("size", (Object[]) null)
                .build().encode().toUriString();

        final var headerLink = new StringBuilder();

        if (page.isHasNext())
            appendHeaderLink(headerLink, "next", page.getNumber() + 1, page.getSize(), baseUriWithoutPagination);

        if (page.isHasPrevious())
            appendHeaderLink(headerLink, "prev", page.getNumber() - 1, page.getSize(), baseUriWithoutPagination);

        if (!page.isFirst())
            appendHeaderLink(headerLink, "first", 0, page.getSize(), baseUriWithoutPagination);

        if (!page.isLast())
            appendHeaderLink(headerLink, "last", page.getTotalPages() - 1, page.getSize(), baseUriWithoutPagination);

        return headerLink.toString();
    }

    public static void appendHeaderLink(final StringBuilder headerLink, final String rel, final int totalPages, final int size,
                                        final String uri) {
        if (!headerLink.isEmpty())
            headerLink.append(", ");

        headerLink.append("<")
                .append(UriComponentsBuilder.fromUri(URI.create(uri))
                        .replaceQueryParam("page", totalPages)
                        .replaceQueryParam("size", size)
                        .build().encode().toUriString())
                .append(">; rel=\"").append(rel).append("\"");
    }

}