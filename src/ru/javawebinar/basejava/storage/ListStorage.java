package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        int index = searchKey;
        storage.set(index, resume);
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        int index = searchKey;
        return storage.get(index);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        int index = searchKey;
        storage.remove(index);
    }

    @Override
    public List<Resume> doGetAll() {
        List<Resume> resumes = new ArrayList<>(storage);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;  // Если индекс >= 0, значит, элемент существует
    }
}
