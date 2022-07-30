package org.based.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.based.persistence.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Entity {
    private long id;
    @NotNull
    private String name;
    @Nullable
    private String description;
    @JsonCreator
    public Project(@JsonProperty("name") @NotNull String name,
                   @JsonProperty("description") @Nullable String description) {
        this.name = name;
        this.description = description;
    }
}
