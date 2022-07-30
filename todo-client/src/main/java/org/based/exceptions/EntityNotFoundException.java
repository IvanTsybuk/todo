package org.based.exceptions;

import org.jetbrains.annotations.NotNull;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(@NotNull String message) {
        super(message);
    }
}
