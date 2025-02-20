package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public abstract void clear();

    @Override
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(index, resume);
        }
    }

    protected abstract void updateElement(int index, Resume resume);

    @Override
    public abstract void save(Resume resume);

    protected final void saveElement(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertElement(index, resume);
        }
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(index);
    }

    protected abstract Resume getElement(int index);

    @Override
    public abstract void delete(String uuid);

    protected final void deleteElement(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            fillEmptySpace(index);
        }
    }

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void fillEmptySpace(int index);

    protected abstract int getIndex(String uuid);
}
