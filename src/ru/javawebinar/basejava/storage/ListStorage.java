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
    protected void doUpdate(Resume resume, Object searchKey) {
        int index = (Integer) searchKey;
        storage.set(index, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int index = (Integer) searchKey;
        return storage.get(index);
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = (Integer) searchKey;
        storage.remove(index);
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
    protected Integer getSearchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected boolean isExist(Object searchKey) {
        if (!(searchKey instanceof Integer)) {
            throw new IllegalArgumentException("Search key must be an integer");
        }
        return (Integer) searchKey >= 0;  // Если индекс >= 0, значит, элемент существует
    }
}
