package org.based.exceptions;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    @NotNull
    private String message;
    @NotNull
    private LocalDateTime timestamp;
}
