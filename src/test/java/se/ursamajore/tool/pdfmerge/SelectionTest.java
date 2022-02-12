package se.ursamajore.tool.pdfmerge;


import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SelectionTest {
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
        assertEquals(expectedFileName, saveFile.getAbsolutePath());
    }

}