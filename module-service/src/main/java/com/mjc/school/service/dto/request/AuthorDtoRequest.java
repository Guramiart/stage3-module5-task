package com.mjc.school.service.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record AuthorDtoRequest(
        @NotNull
        @Size(min = 3, max = 15, message = "Author name length should be between from 3 to 15 chars")
        String name
) {
}
