package se.ursamajore.tool.pdfmerge;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Main class
 */
public class App 
{
    public static void main( String[] args )
    {
    	System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
    	URL url = App.class.getResource("/if_pdf_332631.png");
    	ImageIcon img = new ImageIcon(url);  	
    	JFrame frame = new JFrame("Merge PDF files");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Selection());
        frame.setSize(720, 600);
        if (img.getImage() != null) {
        	frame.setIconImage(img.getImage());
        }
        frame.setVisible(true);
    }

}
