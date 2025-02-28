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
            Object searchKey = getNotExistingSearchKey(resume.getUuid());
            doSave(resume, searchKey);
            size++;
        }
    }

    @Override
    public final void delete(String uuid) {
        if (size == 0) {
            throw new NotExistStorageException(uuid);
        } else {
            Object searchKey = getExistingSearchKey(uuid);
            doDelete(searchKey);
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
    protected void doUpdate(Resume resume, Object searchKey) {
        int index = (Integer) searchKey;
        storage[index] = resume;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int index = (Integer) searchKey;
        return storage[index];
    }

    protected boolean isExist(Object searchKey) {
        int index;
        if (searchKey instanceof Integer) {
            index = (Integer) searchKey;
        } else {
            throw new IllegalArgumentException("Search key must be integer");
        }

        if (index >= 0 && index < size) {
            return true;
        } else {
            return false;
        }
    }
}
