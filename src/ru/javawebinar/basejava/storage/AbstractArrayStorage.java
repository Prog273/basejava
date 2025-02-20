package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveElement(resume);
            size++;
        }

    }

    @Override
    protected Resume getElement(int index) {
        return storage[index];
    }

    @Override
    public final void delete(String uuid) {
        if (size == 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteElement(uuid);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage[index] = resume;
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void fillEmptySpace(int index);

    protected abstract int getIndex(String uuid);
}
