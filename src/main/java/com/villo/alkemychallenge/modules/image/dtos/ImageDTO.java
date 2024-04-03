package com.villo.alkemychallenge.modules.image.dtos;

import com.villo.alkemychallenge.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImageDTO {
    @Schema(example = Constants.IMAGE_NAME_WITH_EXTENSION_EXAMPLE)
    private String nameWithExtension;
}
