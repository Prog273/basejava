/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < this.size(); i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[this.size()] = r;
    }

    Resume get(String uuid) {
        Resume necessaryResume = new Resume();
        for (var resume : this.getAll()) {
            if (resume.uuid.equals(uuid)) {
                necessaryResume = resume;
                break;
            }
        }
        return necessaryResume;
    }

    void delete(String uuid) {
        int indexOfDeletedResume = 0;
        for (int i = 0; i < this.size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                indexOfDeletedResume = i;
            }
        }
        var allResumes = this.getAll();
        for (int i = indexOfDeletedResume; i < allResumes.length - 1; i++) {
            storage[i] = allResumes[i + 1];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResumes = new Resume[this.size()];
        for (int i = 0; i < allResumes.length; i++) {
            allResumes[i] = storage[i];
        }
        return allResumes;
    }

    int size() {
        int size = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                size++;
            } else break;
        }
        return size;
    }
}
