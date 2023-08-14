package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;


public class Main {
    public static void main(String[] args) {
        String catalogName = "src";
        File catalog = new File(catalogName);
        searchInFile(catalog);

        Path path = Path.of(catalogName);
        searchInFile(path);
    }

    public static void searchInFile(File file) {
        if (file.listFiles() != null) {
            File[] catalog = file.listFiles();
            for (File path : catalog) {
                if (path.isFile()) {
                    System.out.println(path);
                    readFile(path);
                    System.out.println("File size: " + file.length() + "bytes");
                    System.out.println("=".repeat(10));
                } else if (path.isDirectory()) {
                    searchInFile(path);
                }
            }
        }
    }

    public static void searchInFile(Path path) {
            try (DirectoryStream<Path> catalog = Files.newDirectoryStream(path)) {
                for (Path currentPath : catalog) {
                    if (Files.isDirectory(currentPath)) {
                        searchInFile(currentPath);
                    } else {
                        System.out.println(currentPath);
                        readFile(currentPath);
                        System.out.println("File size: " + Files.size(currentPath) + "bytes");
                        System.out.println("=".repeat(10));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private static void readFile(File file) {
        String name = file.getName();
        System.out.println("File name: " + name);
        System.out.print("File context: \n\"");
        try (FileReader reader = new FileReader(file)) {
            while (reader.ready()) {
                System.out.print((char) reader.read());
            }
            System.out.print("\"");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println();
        System.out.println("File extension: " + name.substring(name.indexOf('.')));
    }

    private static void readFile(Path path) throws IOException {
        String fileName = path.toString();
        readFile(new File(fileName));
    }
}
