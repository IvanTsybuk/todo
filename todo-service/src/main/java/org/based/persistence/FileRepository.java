package org.based.persistence;

public interface FileRepository<T> extends Repository<T> {
    void saveToFile();
}
