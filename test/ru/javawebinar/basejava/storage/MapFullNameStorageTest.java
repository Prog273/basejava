package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapFullNameStorageTest extends AbstractStorageTest {
    public MapFullNameStorageTest(Storage storage) {
        super(new MapFullNameStorage());
    }

    @Test
    void getAllSorted() {
        List<Resume> allResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        allResumes.sort(RESUME_COMPARATOR);

        List<Resume> sortedResumes = storage.getAllSorted();
        assertEquals(allResumes, sortedResumes);
    }
}
