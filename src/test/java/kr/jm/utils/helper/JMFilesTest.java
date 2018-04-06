package kr.jm.utils.helper;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JMFilesTest {

    @Test
    public void createEmptyFile() {
        File file = new File("test" + System.currentTimeMillis() + "/a.txt");
        File parentDir = file.getParentFile();
        JMPathOperation.deleteAll(parentDir.toPath());
        System.out.println(parentDir.exists());
        assertFalse(parentDir.exists());
        assertTrue(JMFiles.createEmptyFile(file));
        assertFalse(JMFiles.createEmptyFile(file));
        JMPathOperation.deleteAll(parentDir.toPath());
    }
}