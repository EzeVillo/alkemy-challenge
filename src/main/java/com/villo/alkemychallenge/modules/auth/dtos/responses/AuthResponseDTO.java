package com.villo.alkemychallenge.modules.auth.dtos.responses;

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
public class AuthResponseDTO {
    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiIsImlhdCI6MTcxMTA3NTE2NiwiZXhwIjoxNzExMDc2NjA2fQ.-_ZvT8q3MbEShCP9VjtrK5m3btfiosqmErcuwDO5uIU")
    private String token;
}
