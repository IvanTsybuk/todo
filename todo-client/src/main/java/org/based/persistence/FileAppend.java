package org.based.persistence;

import lombok.Getter;

@Getter
public enum FileAppend {
    JSON("{}"),
    XML("<HashMap></HashMap>");
    private final String appendType;
    FileAppend(String appendType) {
        this.appendType = appendType;
    }
}
