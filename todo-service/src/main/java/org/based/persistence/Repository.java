package org.based.persistence;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface Repository<T> {
    void save(@NotNull final T entity);
    @NotNull
    List<T> findAll();
    void deleteByName(@NotNull final String name);
    @NotNull
    Optional<T> findByName(@NotNull final String name);
    void update(@NotNull final T entity);
}
