package org.based.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import java.io.File;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@Log4j2
public class JacksonWriter<T> implements Writer<T> {
    @NotNull
    private final ObjectMapper jacksonMapper;
    @NotNull
    private final File configuredFile;
    @NotNull
    private final Class<T> typeClass;
    public JacksonWriter(@NotNull ObjectMapper jacksonMapper,
                         @NotNull File configuredFile,
                         @NotNull Class<T> type) {
        log.debug("JacksonWriter initialization");
        this.jacksonMapper = jacksonMapper;
        this.configuredFile = configuredFile;
        this.typeClass = type;
    }
    @Override
    @SneakyThrows
    public void writeToFile(@NotNull final Map<String, T> map) {
        log.debug(String.format("Method writeToFile was called with arguments: arg1 - %s. "
                + "File for saving- %s", map, configuredFile));
        jacksonMapper.writeValue(configuredFile, map);
    }
    @Override
    @SneakyThrows
    @NotNull
    public Map<String, T> readFile() {
        log.debug(String.format("Method readFile was called with file for reading: %s",
                configuredFile.getName()));
        final MapType mapType = jacksonMapper.getTypeFactory().constructMapType(Map.class,
                String.class, typeClass);
        return jacksonMapper.readValue(configuredFile, mapType);
    }
}
