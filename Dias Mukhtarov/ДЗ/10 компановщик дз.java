//composite

import java.util.List;
import java.util.ArrayList;

interface FileSystemComponent {
    void display();
    int getSize();
}

class File implements FileSystemComponent {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void display() {
        System.out.println("File: " + name + ", Size: " + size + " KB");
    }

    @Override
    public int getSize() {
        return size;
    }
}

class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void addComponent(FileSystemComponent component) {
        if (!components.contains(component)) {
            components.add(component);
        }
    }

    public void removeComponent(FileSystemComponent component) {
        components.remove(component);
    }

    @Override
    public void display() {
        System.out.println("Directory: " + name);
        for (FileSystemComponent component : components) {
            component.display();
        }
    }

    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}

public class FileSystemDemo {
    public static void main(String[] args) {
        File file1 = new File("file1.txt", 100);
        File file2 = new File("file2.jpg", 200);
        File file3 = new File("file3.pdf", 150);

        Directory dir1 = new Directory("Documents");
        Directory dir2 = new Directory("Pictures");

        dir1.addComponent(file1);
        dir1.addComponent(file3);

        dir2.addComponent(file2);

        Directory rootDir = new Directory("Root");
        rootDir.addComponent(dir1);
        rootDir.addComponent(dir2);

        rootDir.display();
        System.out.println("Total size of Root directory: " + rootDir.getSize() + " KB");
    }
}
