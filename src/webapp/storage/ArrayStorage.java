package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (size == 0) {
            errorMessageForEmptyBase();
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].compareResumeUuids(resume)) {
                storage[i] = resume;
                return;
            }
        }
        System.out.println("Ошибка. Резюме с UUID " + resume.getUuid() + " нет.");
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Хранилище переполнено, нельзя сохранить резюме.");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].compareResumeUuids(resume)) {
                System.out.println("Ошибка. Резюме с UUID " + resume.getUuid() + " уже есть.");
                return;
            }
        }
        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        errorMessageForNotExistingUuid(uuid);
        return null;
    }

    public void delete(String uuid) {
        //следующая проверка - чтобы не было ArrayIndexOutOfBoundsException:
        if (size == 0) {
            errorMessageForEmptyBase();
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                return;
            }
        }

        errorMessageForNotExistingUuid(uuid);
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

    public void errorMessageForEmptyBase() {
        System.out.println("Ошибка. База данных пуста.");
    }

    public void errorMessageForNotExistingUuid(String uuid) {
        System.out.println("Ошибка. Резюме с UUID " + uuid + " нет в базе.");
    }
}
