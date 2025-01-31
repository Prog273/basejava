package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) > 0) {
            System.out.println("Ошибка. Резюме с UUID " + resume.getUuid() + " уже есть в базе.");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено, нельзя сохранить резюме.");
        } else {
            insertAndSort(storage, resume);
            size++;
        }
    }

    private void insertAndSort(Resume[] resumes, Resume resume) {
        int insertPosition = Arrays.binarySearch(resumes, 0, size, resume);
        if (insertPosition < 0) {
            insertPosition = -(insertPosition + 1);
        }
        System.arraycopy(resumes, insertPosition, resumes,
                insertPosition + 1, size - insertPosition);
        resumes[insertPosition] = resume;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

