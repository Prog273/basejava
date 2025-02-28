package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    void getAll() {
        Resume[] allResumes = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Resume[] sortedResumes = storage.getAll();
        Arrays.sort(sortedResumes);
        assertArrayEquals(allResumes, sortedResumes);
    }
}
