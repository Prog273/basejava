package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.FullNameGenerator;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String NAME_1 = FullNameGenerator.generateFullName();
    private static final String NAME_2 = FullNameGenerator.generateFullName();
    private static final String NAME_3 = FullNameGenerator.generateFullName();
    private static final String NAME_4 = FullNameGenerator.generateFullName();

    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, NAME_1);
        RESUME_2 = new Resume(UUID_2, NAME_2);
        RESUME_3 = new Resume(UUID_3, NAME_3);
        RESUME_4 = new Resume(UUID_4, NAME_4);
    }


    public AbstractStorageTest(Storage storage) {
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
    void clear() {
        storage.clear();
        assertEquals(0, storage.size(), "expected storage to be empty");
        List<Resume> emptyList = storage.getAllSorted();
        assertEquals(emptyList, new ArrayList<Resume>());
    }

    @Test
    void update() {
        storage.update(RESUME_3);
        assertSame(RESUME_3, storage.get(UUID_3));
    }

    @Test
    void updateNotExisting() {
        assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
    }

    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    void assertGetTest() {
        assertGet(RESUME_1);
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void getNotExisting() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }

    void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> assertGet(RESUME_1));
        storage.clear();
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_1));
    }

    @Test
    void deleteNotExistingResume() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    void getAll() {
        List<Resume> resumes = new ArrayList<>();
        resumes.add(RESUME_1);
        resumes.add(RESUME_2);
        resumes.add(RESUME_3);
        assertEquals(resumes, storage.getAllSorted());
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void saveExisting() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_2));
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertSize(4);
    }
}
