package se.ursamajore.tool.pdfmerge;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class PDFFilePreview extends JComponent implements PropertyChangeListener {
    private final JFileChooser fileChooser;
    private ImageIcon thumbnail = null;
    private File file = null;

    public PDFFilePreview(final JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
        setPreferredSize(new Dimension(100, 200));
        fileChooser.addPropertyChangeListener(this);
    }
    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        boolean update;
        String property = evt.getPropertyName();
        switch (property) {
            case JFileChooser.DIRECTORY_CHANGED_PROPERTY:
                file = null;
                update = true;
                break;
            case JFileChooser.SELECTED_FILE_CHANGED_PROPERTY:
                file = (File) evt.getNewValue();
                update = true;
                break;
            default:
                update = false;
                break;
        }
        if (update) {
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }

    private void loadImage() {
        if (file == null) {
            thumbnail = null;
            return;
        }
        try (PDDocument document = PDDocument.load(file)) {
            if (document.getNumberOfPages() == 0) {
                thumbnail = null;
                return;
            }
            PDFRenderer renderer = new PDFRenderer(document);
            PDPage page = document.getPage(0);
            if (page != null) {
                BufferedImage bufferedImage = renderer.renderImageWithDPI(0, 25, ImageType.RGB);
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                Image image = imageIcon.getImage();
                thumbnail = new ImageIcon(
                        image.getScaledInstance(
                                100,
                                155,
                                Image.SCALE_SMOOTH
                        )
                ); // scale it the smooth way
            }
        } catch (Exception e) {
            thumbnail = null;
        }
    }

    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoke super's implementation you must honor the opaque property, that is
     * if this component is opaque, you must completely fill in the background
     * in an opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identify transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see ComponentUI
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (thumbnail == null) {
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth() / 2 - thumbnail.getIconWidth() / 2;
            int y = getHeight() / 2 - thumbnail.getIconHeight() / 2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
        }
    }
}
