package org.based.exceptions;

import org.jetbrains.annotations.NotNull;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(@NotNull String message) {
        super(message);
    }
}
