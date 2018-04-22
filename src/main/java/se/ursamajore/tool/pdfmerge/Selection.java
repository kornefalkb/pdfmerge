package se.ursamajore.tool.pdfmerge;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Selection extends JPanel {
	private static final long serialVersionUID = 3107028628725865567L;
	JList<String> list;
	List<File> fileList = new ArrayList<File>();

	DefaultListModel<String> model;

	public Selection() {
		setLayout(new BorderLayout());
		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane pane = new JScrollPane(list);
		final JButton addButton = new JButton("Add PDF file(s)");
		final JButton removeButton = new JButton("Remove selected PDF file");
		final JButton mergeButton = new JButton("Merge PDF files into one");
		final JButton clearButton = new JButton("Remove all PDF files");
		final JButton exitButton = new JButton("Quit");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File[] files = chooseFiles();
				if ((files != null) && (files.length > 0)) {
					for (File file : files) {
						model.addElement(file.getAbsolutePath());
						fileList.add(file);
					}
					removeButton.setEnabled(true);
					clearButton.setEnabled(true);
					mergeButton.setEnabled(fileList.size() > 1);
				}
			}
		});
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getSize() > 0) {
					int selected = list.getSelectedIndex();
					if (selected != -1) {
						model.removeElementAt(selected);
						fileList.remove(selected);
						if (fileList.size() > 0) {
							clearButton.setEnabled(false);
							mergeButton.setEnabled(fileList.size() > 1);
						}
						removeButton.setEnabled(!fileList.isEmpty());
					}
				}
			}
		});
		mergeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (fileList.isEmpty())
					return;
				File saveFile = getSaveAsFile();
				if (saveFile == null)
					return;
				PDFmerge merge = new PDFmerge();
	    		merge.pdfFiles(saveFile, fileList);
			}
		});
		clearButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fileList.clear();
				while (!model.isEmpty()) {
					model.remove(0);
				}
				clearButton.setEnabled(false);
				mergeButton.setEnabled(false);
				removeButton.setEnabled(false);
			}
		});
		exitButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		clearButton.setEnabled(false);
		mergeButton.setEnabled(false);
		removeButton.setEnabled(false);
		add(pane, BorderLayout.CENTER);
		JPanel buttomPanel = new JPanel();
		buttomPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		buttomPanel.add(addButton, c);
		++c.gridx;
		buttomPanel.add(removeButton, c);
		++c.gridx;
		buttomPanel.add(clearButton, c);
		++c.gridx;
		buttomPanel.add(mergeButton, c);
		++c.gridx;
		buttomPanel.add(exitButton, c);
		add(buttomPanel, BorderLayout.SOUTH);
	}

	private File[] chooseFiles() {
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter filter = new FileNameExtensionFilter("PDF files", "pdf");
		fc.addChoosableFileFilter(new FileNameExtensionFilter("PDF files", "pdf"));
		fc.setFileFilter(filter);
		fc.setDialogTitle("Select direcorty to merge PDF files");
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFiles();
		}
		return null;
	}

	private static File getSaveAsFile() {
    	JFileChooser fc = new JFileChooser();
    	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	fc.setDialogTitle("Save files PDF files as");
    	FileFilter filter = new FileNameExtensionFilter("PDF files", "pdf");
    	fc.setFileFilter(filter);
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		return null;
	}
}
