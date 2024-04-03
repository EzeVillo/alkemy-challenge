package com.villo.alkemychallenge.modules.image.controllers;

import com.villo.alkemychallenge.modules.image.dtos.ImageDTO;
import com.villo.alkemychallenge.modules.image.services.ImageService;
import com.villo.alkemychallenge.modules.image.utils.annotations.existfile.ExistFile;
import com.villo.alkemychallenge.modules.image.utils.annotations.validextension.ValidExtension;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.errors.custom.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping(Constants.IMAGE_PATH)
@RequiredArgsConstructor
public class ImageController {
    private static final String UPLOAD_A = "Upload a ";
    private static final String IMAGE_WITH_DOT = "image.";
    private static final String UPLOAD_A_IMAGE = UPLOAD_A + IMAGE_WITH_DOT;
    private static final String IMAGE = "image";
    private static final String OBTAIN_A_IMAGE = Constants.OBTAIN_A + IMAGE_WITH_DOT;
    public static final String RESOURCE_SUCCESSFULLY_UPLOADED = "Resource uploaded successfully.";
    private static final String REGEX_NAME = "^[a-zA-Z0-9-]{1,50}";
    private static final String REGEX_NAME_WITHOUT_EXTENSION = REGEX_NAME + "$";
    private static final String REGEX_NAME_WITHOUT_EXTENSION_MESSAGE = Constants.THE_NAME + " can only contain letters, numbers, " +
            "and hyphens";
    private static final String REGEX_NAME_WITH_EXTENSION = REGEX_NAME + "\\.(jpeg|png)$";
    private static final String REGEX_NAME_WITH_EXTENSION_MESSAGE = REGEX_NAME_WITHOUT_EXTENSION_MESSAGE +
            ", and the extension must be JPEG or PNG";
    private static final String PATH_PARAM = "{name:.+}";

    private final HttpServletRequest httpServletRequest;
    private final ImageService imageService;

    @Operation(summary = UPLOAD_A_IMAGE, responses = {
            @ApiResponse(responseCode = Constants.CREATED, description = RESOURCE_SUCCESSFULLY_UPLOADED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ImageDTO.class))},
                    links = @Link(name = HttpHeaders.LOCATION, operationId = OBTAIN_A_IMAGE,
                            parameters = @LinkParameter(name = IMAGE, expression = IMAGE))),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDTO> create(
            @ValidExtension
            @RequestPart final MultipartFile file,
            @Pattern(regexp = REGEX_NAME_WITHOUT_EXTENSION, message = REGEX_NAME_WITHOUT_EXTENSION_MESSAGE)
            @Schema(example = Constants.IMAGE_NAME_EXAMPLE)
            @RequestPart final String name) {
        var image = imageService.createImage(file, name);
        return ResponseEntity.created(URI.create(Constants.IMAGE_PATH + "/" + image.getNameWithExtension())).body(image);
    }

    @Operation(summary = OBTAIN_A_IMAGE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_FETCHED_SUCCESSFULLY,
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema),
                            @Content(mediaType = MediaType.IMAGE_JPEG_VALUE,
                                    schema = @Schema(example = "Binary file")),
                            @Content(mediaType = MediaType.IMAGE_PNG_VALUE,
                                    schema = @Schema(example = "Binary file"))}),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(PATH_PARAM)
    public ResponseEntity<Resource> findByName(
            @Pattern(regexp = REGEX_NAME_WITH_EXTENSION, message = REGEX_NAME_WITH_EXTENSION_MESSAGE)
            @ExistFile(hasRoot = false)
            @Schema(example = Constants.IMAGE_NAME_WITH_EXTENSION_EXAMPLE)
            @PathVariable final String name) throws IOException {
        var file = imageService.findByName(name);
        var contentType = httpServletRequest.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        contentType == null ?
                                MediaType.APPLICATION_OCTET_STREAM_VALUE :
                                contentType))
                .body(file);
    }

    @Operation(summary = Constants.DELETE_A + IMAGE_WITH_DOT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.NO_CONTENT, description = Constants.RESOURCE_SUCCESSFULLY_DELETED),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping(PATH_PARAM)
    public ResponseEntity<Void> delete(
            @Pattern(regexp = REGEX_NAME_WITH_EXTENSION, message = REGEX_NAME_WITH_EXTENSION_MESSAGE)
            @ExistFile(hasRoot = false)
            @Schema(example = Constants.IMAGE_NAME_WITH_EXTENSION_EXAMPLE)
            @PathVariable final String name) {
        imageService.delete(name, false);
        return ResponseEntity.noContent().build();
    }
}
