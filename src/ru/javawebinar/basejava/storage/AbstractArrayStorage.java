package ru.javawebinar.basejava.storage;

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

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Ошибка. Резюме с UUID " + resume.getUuid() + " нет в базе.");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Ошибка. Резюме с UUID " + uuid + " нет в базе.");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        //следующая проверка - чтобы не было ArrayIndexOutOfBoundsException:
        if (size == 0) {
            System.out.println("Ошибка. База данных пуста.");
        } else if (index < 0) {
            System.out.println("Ошибка. Резюме с UUID " + uuid + " нет в базе.");
        } else {
            storage[index] = storage[size - 1];
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

    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено, нельзя сохранить резюме.");
        } else if (getIndex(resume.getUuid()) > 0) {
            System.out.println("Ошибка. Резюме с UUID " + resume.getUuid() + " уже есть в базе.");
        } else {
            insertElement(storage, resume);
        }
    }

    protected abstract void insertElement(Resume[] resumes, Resume resume);

    protected abstract int getIndex(String uuid);

}
