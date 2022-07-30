package org.based.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@Log4j2
public class CachingRepository<T extends Entity> implements FileRepository<T> {
    @NotNull
    private final Map<String, T> repositoryMap;
    @NotNull
    private final Writer<T> writer;
    public CachingRepository(@NotNull Writer<T> writer) {
        log.debug("CachingRepository initialization");
        this.writer = writer;
        this.repositoryMap = writer.readFile();
    }
    public void save(@NotNull final T entity) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", entity));
        repositoryMap.put(entity.getName(), entity);
    }
    @NotNull
    public List<T> findAll() {
        log.debug("Method findAll was called");
        return new ArrayList<>(repositoryMap.values());
    }
    public void deleteByName(@NotNull final String name) {
        log.debug(String.format("Method deleteByName was called with arguments: arg1 - %s", name));
        repositoryMap.remove(name);
    }
    @NotNull
    public Optional<T> findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        return Optional.ofNullable(repositoryMap.get(name));
    }
    public void update(@NotNull final T entity) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s", entity));
        repositoryMap.put(entity.getName(), entity);
    }
    public void saveToFile() {
        writer.writeToFile(repositoryMap);
    }
}
