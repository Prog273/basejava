package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    protected final Storage storage;
    protected static final int STORAGE_LIMIT = 10000;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    @DisplayName("clear() очищает массив с Resume")
    void clearShouldClearStorage() {
        storage.clear();
        assertEquals(0, storage.size(), "expected storage to be empty");
    }

    @Test
    @DisplayName("update() заменяет Resume при наличии UUID")
    void updateShouldUpdateResume() {
        Resume oldResume = storage.get(UUID_3);
        Resume newResume = new Resume(UUID_3);
        storage.update(newResume);
        assertEquals(oldResume, storage.get(UUID_3));
    }

    @Test
    @DisplayName("update() выбрасывает NotExistStorageException при несуществующем Resume")
    void updateShouldThrowException() {
        Resume newResume = new Resume("uuid4");
        assertThrows(NotExistStorageException.class, () -> storage.update(newResume));
    }

    @Test
    @DisplayName("get() возвращает Resume при наличии UUID")
    void getShouldReturnResume() {
        Resume newResume = new Resume(UUID_3);
        assertEquals(newResume, storage.get(UUID_3));
    }

    @Test
    @DisplayName("get() выбрасывает NotExistStorageException при несуществующем Resume")
    void getShouldThrowException() {
        Resume newResume = new Resume("uuid4");
        assertThrows(NotExistStorageException.class, () -> storage.get(newResume.getUuid()));
    }

    @Test
    @DisplayName("delete() удаляет резюме из хранилища")
    void deleteShouldDeleteResume() {
        storage.delete(UUID_1);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1),
                "Резюме с UUID1 должно отсутствовать после удаления");
    }

    @Test
    @DisplayName("delete() уменьшает размер базы данных резюме")
    void deleteShouldReduceSize() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test
    @DisplayName("delete() выбрасывает NotExistStorageException при пустом хранилище")
    void deleteShouldThrowException1() {
        storage.clear();
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_1));
    }

    @Test
    @DisplayName("delete() выбрасывает NotExistStorageException при отсутствии Resume в хранилище")
    void deleteShouldThrowException2() {
        Resume newResume = new Resume("uuid4");
        assertThrows(NotExistStorageException.class, () -> storage.delete(newResume.getUuid()));
    }

    @Test
    @DisplayName("getAll() возвращает массив со всеми резюме")
        //не работает!
    void getAllShouldReturnResumes() {
        Resume[] allResumes = new Resume[]{storage.get(UUID_1), storage.get(UUID_2), storage.get(UUID_3)};
        assertEquals(allResumes, storage.getAll());
    }

    @Test
    @DisplayName("size() возвращает размер базы данных резюме")
    void size() {
        assertEquals(3, storage.size());
    }

    @Test
    @DisplayName("save() выбрасывает StorageException при переполнении")
    void saveShouldThrowStorageException() {
        try {
            for (int i = 4; i <= STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            fail("Переполнение массива произошло раньше времени");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("uuid" + (STORAGE_LIMIT + 1))));
    }

    @Test
    @DisplayName("save() выбрасывает ExistStorageException при попытке сохранить уже имеющееся резюме")
    void saveShouldThrowExistStorageException() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_2)));
    }

    @Test
    @DisplayName("save() увеличивает размер базы данных резюме")
    void saveShouldIncreaseSize() {
        storage.save(new Resume("uuid4"));
        assertEquals(4, storage.size());
    }

    @Test
    public void getNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"), "expected nothing to get");
    }

}