package se.ursamajore.tool.pdfmerge;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.io.File;

public class PDFFileView extends FileView {
    private static ImageIcon pdfIcon = loadIcon("/adobe-pdf-icon.png");

    private static ImageIcon loadIcon(String filename) {
        ImageIcon imageIcon = new ImageIcon(PDFFileView.class.getResource(filename));
        Image image = imageIcon.getImage(); // transform it
        return new ImageIcon(
                image.getScaledInstance(
                        32,
                        32,
                        java.awt.Image.SCALE_SMOOTH
                )
        ); // scale it the smooth way
    }

    /**
     * The name of the file. Normally this would be simply
     * <code>f.getName()</code>.
     *
     * @param f a {@code File} object
     * @return a {@code String} representing the name of the file
     */
    @Override
    public String getName(File f) {
        return super.getName(f);
    }

    /**
     * A human readable description of the file. For example,
     * a file named <i>jag.jpg</i> might have a description that read:
     * "A JPEG image file of James Gosling's face".
     *
     * @param f a {@code File} object
     * @return a {@code String} containing a description of the file or
     * {@code null} if it is not available.
     */
    @Override
    public String getDescription(File f) {
        try {
            PDDocument document = PDDocument.load(f);
            PDDocumentInformation information = document.getDocumentInformation();
            if (information != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Title: ").append(information.getTitle()).append(System.lineSeparator());
                sb.append("Author:").append(information.getAuthor()).append(System.lineSeparator());
                sb.append("Pages: ").append(document.getNumberOfPages());
            }
        } catch (Exception e) {
            return null;
        }
        return super.getDescription(f);
    }

    /**
     * A human readable description of the type of the file. For
     * example, a <code>jpg</code> file might have a type description of:
     * "A JPEG Compressed Image File"
     *
     * @param f a {@code File} object
     * @return a {@code String} containing a description of the type of the file
     * or {@code null} if it is not available   .
     */
    @Override
    public String getTypeDescription(File f) {
        if ("pdf".equals(getExtension(f))) {
            return "PDF document";
        }
        return null;
    }

    /**
     * The icon that represents this file in the <code>JFileChooser</code>.
     *
     * @param f a {@code File} object
     * @return an {@code Icon} which represents the specified {@code File} or
     * {@code null} if it is not available.
     */
    @Override
    public Icon getIcon(File f) {
        if ("pdf".equals(getExtension(f))) {
            return pdfIcon;
        }
        return null;
    }

    /**
     * Whether the directory is traversable or not. This might be
     * useful, for example, if you want a directory to represent
     * a compound document and don't want the user to descend into it.
     *
     * @param f a {@code File} object representing a directory
     * @return {@code true} if the directory is traversable,
     * {@code false} if it is not, and {@code null} if the
     * file system should be checked.
     * @see javax.swing.filechooser.FileSystemView#isTraversable
     */
    @Override
    public Boolean isTraversable(File f) {
        return null;
    }

    private static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
