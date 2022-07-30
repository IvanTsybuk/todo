package org.based.persistence;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface Writer<T> {
    static <T> WriterBuilder<T> builder() {
        return new WriterBuilder<>();
    }
    void writeToFile(@NotNull Map<String, T> map);
    @NotNull Map<String, T> readFile();
}
