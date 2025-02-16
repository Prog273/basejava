package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    protected final Storage storage;
    protected static final int STORAGE_LIMIT = 10000;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);


    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clearClearsStorage() {
        storage.clear();
        assertEquals(0, storage.size(), "expected storage to be empty");
        Resume[] emptyArr = storage.getAll();
        boolean areAllElementsNull = Arrays.stream(emptyArr)
                .allMatch(element -> element == null);
        assertTrue(areAllElementsNull);
    }

    @Test
    void updateUpdatesResume() {
        Resume newResume = RESUME_3;
        storage.update(newResume);
        assertSame(newResume, storage.get(UUID_3));
    }

    @Test
    void updateNotExisting() {
        Resume newResume = RESUME_4;
        assertThrows(NotExistStorageException.class, () -> storage.update(newResume));
    }

//    @ParameterizedTest
    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    void getReturnsResume() {
        Resume newResume = RESUME_3;
        assertGet(newResume);
    }

    @Test
    void getNotExisting() {
        Resume newResume = RESUME_4;
        assertThrows(NotExistStorageException.class, () -> storage.get(newResume.getUuid()));
    }

    @Test
    void deleteDeletesResume() {
        /** Не стал тут переименовывать в deleteNotExisting, так как цель этого теста не проверить выброс исключения,
         а именно проверить, удалился ли элемент. Для этого и использую исключение. В таком случае, надо переименовать?
         */
        storage.delete(UUID_1);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1),
                "There must be no resume with UUID_1 after deleting");
    }

//    @ParameterizedTest
    void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test
    void deleteReducesSize() {
        storage.delete(UUID_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> assertGet(RESUME_1));
    }

    @Test
    void deleteNotExistingFromEmptyStorage() {
        storage.clear();
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_1));
    }

    @Test
    void deleteNotExistingResume() {
        Resume newResume = RESUME_4;
        assertThrows(NotExistStorageException.class, () -> storage.delete(newResume.getUuid()));
    }

    @Test
    void getAllReturnsResumes() {
        Resume[] allResumes = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        assertArrayEquals(allResumes, storage.getAll());
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void saveOverflow() {
        storage.clear();
        try {
            for (int i = 1; i <= STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            fail("The array overflow happened earlier than expected.");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("uuid" + (STORAGE_LIMIT + 1))));
    }

    @Test
    void saveExisting() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_2));
    }

    @Test
    void saveIncreasesSize() {
        storage.save(RESUME_4);
        assertSize(4);
    }

    @Test
    public void getNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"), "expected nothing to get");
    }

}