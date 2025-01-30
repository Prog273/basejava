package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Ошибка. Резюме с UUID " + resume.getUuid() + " нет в базе.");
        } else {
            storage[index] = resume;
        }
    }

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

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
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
        } else if (index == -1) {
            System.out.println("Ошибка. Резюме с UUID " + uuid + " нет в базе.");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
