package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("Ошибка. Резюме с UUID " + resume.getUuid() + " уже есть в базе.");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено, нельзя сохранить резюме.");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

