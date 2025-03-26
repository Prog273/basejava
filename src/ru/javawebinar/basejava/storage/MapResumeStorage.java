package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(((Resume)searchKey).getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> doGetAll() {
        return null;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(map.values());
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }
}
