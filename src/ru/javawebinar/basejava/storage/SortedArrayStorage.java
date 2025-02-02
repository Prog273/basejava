package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void insertElement(Resume[] resumes, Resume resume) {
        int insertPosition = Arrays.binarySearch(resumes, 0, size, resume);
        if (insertPosition < 0) {
            insertPosition = -(insertPosition + 1);
        }
        System.arraycopy(resumes, insertPosition, resumes,
                insertPosition + 1, size - insertPosition);
        resumes[insertPosition] = resume;
        size++;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

