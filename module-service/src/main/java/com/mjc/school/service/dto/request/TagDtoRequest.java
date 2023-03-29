package com.mjc.school.service.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record TagDtoRequest(
        @NotNull
        @Size(min = 3, max = 15, message = "Tag name length should be between from 3 to 15 chars")
        String name
) {
}
