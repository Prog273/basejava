/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        if (size == 0) return;
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("Хранилище переполнено, нельзя сохранить резюме");
        }
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (var resume : getAll()) {
            if (resume.uuid.equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        //следующая проверка - чтобы не было ArrayIndexOutOfBoundsException:
        if (size == 0) return;

        int indexOfDeletedResume = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                indexOfDeletedResume = i;
                storage[indexOfDeletedResume] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResumes = new Resume[size];
        for (int i = 0; i < allResumes.length; i++) {
            allResumes[i] = storage[i];
        }
        return allResumes;
    }

    int size() {
        return size;
    }
}
