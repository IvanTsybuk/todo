package org.based.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "path")
@ConstructorBinding
@AllArgsConstructor
@Getter
public class HostPathProperties {
    @NotNull
    private final String projectPath;
    @NotNull
    private final String userPath;
    @NotNull
    private final String taskPath;
}
