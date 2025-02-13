package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for resumes
 */

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (size == 0 || index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            fillEmptySpace(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    public final void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполнено, нельзя сохранить резюме.", resume.getUuid());
        } else {
            int index = getIndex(resume.getUuid());
            if (index > 0) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                insertElement(index, resume);
                size++;
            }
        }
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void fillEmptySpace(int index);

    protected abstract int getIndex(String uuid);

}
