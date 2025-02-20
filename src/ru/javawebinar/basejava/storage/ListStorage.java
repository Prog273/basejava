package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public final void save(Resume resume) {
        saveElement(resume);
    }

    @Override
    protected Resume getElement(int index) {
        return storage.get(index);
    }

    @Override
    public void delete(String uuid) {
        deleteElement(uuid);
        storage.remove(size() - 1);
    }

    @Override
    public Resume[] getAll() {
        return storage.subList(0, size()).toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void insertElement(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void fillEmptySpace(int index) {
        storage.set(index, storage.get(size() - 1));
    }
}
