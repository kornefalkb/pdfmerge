# pdfmerge
Merge PDF documents - example of use PDFbox.
Simple usage of Java Swing to select PDF files and concatenate them into a long PDF file.

# building

mvn clean package

# running

## Windows
Start `pdfmerge.exe` in the `target` directory
Alternativly execute `javaw -jar target/pdfmerge-1.0.0-SNAPSHOT.jar`.

## Unix
Execute `java -jar target/pdfmerge-1.0.0-SNAPSHOT.jar` (requires X-Windows).
