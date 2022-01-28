package se.ursamajore.tool.pdfmerge;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SelectionTest {
    @Test
    public void testSaveFile() {
        File saveFile = new File("c:/users/user/filename");
        if (!saveFile.getName().toLowerCase().endsWith(".pdf")) {
            saveFile = new File(saveFile.getAbsolutePath()+".pdf");
        }
        String separator = File.separator;
        if (separator == "\\") {
            separator= File.separator+File.separator;
        }
        String expectedFileName = new StringBuilder("c:")
                .append(separator)
                .append("users")
                .append(separator)
                .append("user")
                .append(separator)
                .append("filename.pdf").toString();
        Assert.assertEquals(expectedFileName, saveFile.getAbsolutePath());
    }

}