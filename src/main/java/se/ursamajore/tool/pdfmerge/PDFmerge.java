package se.ursamajore.tool.pdfmerge;

import java.util.List;

import javax.swing.JOptionPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

class PDFmerge {

	private PDDocument document = null;
	private int nrPDF=0;
	private List<PDDocument> pdfFiles = new ArrayList<>();
	private StringBuilder sb = new StringBuilder();
	void pdfFiles(File saveFile, List<File> listOfFiles) {
		for (File file : listOfFiles) {
			String name = file.getName();
			if (name.substring(name.length()-3).toLowerCase().equals("pdf")) {
				try {
					merge(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (document != null) {
			try {
				if (document.getNumberOfPages() > 0) {
					sb
							.append("Saved ")
							.append(document.getNumberOfPages())
							.append(" pages from ")
							.append(nrPDF)
							.append(" files\n");
					document.save(saveFile);
				}
				document.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (PDDocument pdf : pdfFiles) {
			try {
				pdf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String message;
		String title;
		int type;
		if ((document == null) || (document.getNumberOfPages() == 0)) {
			message = "Could not find any PDF files";
			type = JOptionPane.WARNING_MESSAGE;
			title = "Error";
		} else {
			message = sb.toString();
			type = JOptionPane.INFORMATION_MESSAGE;
			title = "PDF merge";
		}
		JOptionPane.showMessageDialog(null, message, title,type);
	}

	private void merge(File file) throws IOException {
		if (document == null) {
			document = new PDDocument();
		}
		PDDocument pdf = PDDocument.load(file);
		if (pdf == null) {
			return;
		}
		++nrPDF;
		for (PDPage page : pdf.getPages()) {
			document.addPage(page);
		}
		sb
				.append("Added ")
				.append(String.format("%3d", pdf.getNumberOfPages()))
				.append(" pages from ")
				.append(file.getName())
				.append("\n");
		pdfFiles.add(pdf);
	}

}
