package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (size == 0) {
            System.out.println("Ошибка. База данных пуста.");
            return;
        }

        int indexOfNecessaryResume = findIndex(resume.getUuid());
        if (indexOfNecessaryResume == -1) {
            System.out.println("Ошибка. Резюме с UUID " + resume.getUuid() + " нет в базе.");
            return;
        }

        storage[indexOfNecessaryResume] = resume;
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Хранилище переполнено, нельзя сохранить резюме.");
            return;
        }

        int indexOfNecessaryResume = findIndex(resume.getUuid());
        if (indexOfNecessaryResume == -1) {
            storage[size] = resume;
            size++;
            return;
        }

        System.out.println("Ошибка. Резюме с UUID " + resume.getUuid() + " уже есть в базе.");
    }

    public Resume get(String uuid) {
        int indexOfNecessaryResume = findIndex(uuid);
        if (indexOfNecessaryResume == -1) {
            System.out.println("Ошибка. Резюме с UUID " + uuid + " нет в базе.");
            return null;
        }
        return storage[indexOfNecessaryResume];
    }

    public void delete(String uuid) {
        //следующая проверка - чтобы не было ArrayIndexOutOfBoundsException:
        if (size == 0) {
            System.out.println("Ошибка. База данных пуста.");
            return;
        }

        int indexOfNecessaryResume = findIndex(uuid);
        if (indexOfNecessaryResume == -1) {
            System.out.println("Ошибка. Резюме с UUID " + uuid + " нет в базе.");
            return;
        }

        storage[indexOfNecessaryResume] = storage[size - 1];
        storage[size - 1] = null;
        size--;
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

    public int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
