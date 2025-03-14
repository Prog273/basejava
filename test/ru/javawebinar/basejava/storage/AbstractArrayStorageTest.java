package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void saveOverflow() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT - 1; i++) {
            storage.save(new Resume("uuid" + i, "Name" + i));
        }
        storage.save(new Resume("uuid_last", "Name Last"));
        assertThrows(StorageException.class, () -> storage.save(new Resume("overflow_uuid", "Overflow Name")));
    }
}