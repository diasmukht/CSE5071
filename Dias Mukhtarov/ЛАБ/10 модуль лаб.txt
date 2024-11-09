//facade
class AudioSystem {
    public void turnOn() {
        System.out.println("Аудиосистема включена.");
    }

    public void setVolume(int level) {
        System.out.println("Громкость аудио установлена на " + level + ".");
    }

    public void turnOff() {
        System.out.println("Аудиосистема выключена.");
    }
}

class VideoProjector {
    public void turnOn() {
        System.out.println("Видеопроектор включен.");
    }

    public void setResolution(String resolution) {
        System.out.println("Разрешение видео установлено на " + resolution + ".");
    }

    public void turnOff() {
        System.out.println("Видеопроектор выключен.");
    }
}

class LightingSystem {
    public void turnOn() {
        System.out.println("Освещение включено.");
    }

    public void setBrightness(int level) {
        System.out.println("Яркость света установлена на " + level + ".");
    }

    public void turnOff() {
        System.out.println("Освещение выключено.");
    }
}

class HomeTheaterFacade {
    private AudioSystem audioSystem;
    private VideoProjector videoProjector;
    private LightingSystem lightingSystem;

    public HomeTheaterFacade(AudioSystem audioSystem, VideoProjector videoProjector, LightingSystem lightingSystem) {
        this.audioSystem = audioSystem;
        this.videoProjector = videoProjector;
        this.lightingSystem = lightingSystem;
    }

    public void startMovie() {
        System.out.println("Подготовка к началу фильма...");
        lightingSystem.turnOn();
        lightingSystem.setBrightness(5);
        audioSystem.turnOn();
        audioSystem.setVolume(8);
        videoProjector.turnOn();
        videoProjector.setResolution("HD");
        System.out.println("Фильм начался.");
    }

    public void endMovie() {
        System.out.println("Завершение фильма...");
        videoProjector.turnOff();
        audioSystem.turnOff();
        lightingSystem.turnOff();
        System.out.println("Фильм завершен.");
    }
}




//composite
5.	import java.util.ArrayList;
import java.util.List;

abstract class FileSystemComponent {
    protected String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract void display(int depth);

    public void add(FileSystemComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(FileSystemComponent component) {
        throw new UnsupportedOperationException();
    }

    public FileSystemComponent getChild(int index) {
        throw new UnsupportedOperationException();
    }
}

class File extends FileSystemComponent {
    public File(String name) {
        super(name);
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(depth) + " Файл: " + name);
    }
}

class Directory extends FileSystemComponent {
    private List<FileSystemComponent> children = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    @Override
    public void add(FileSystemComponent component) {
        children.add(component);
    }

    @Override
    public void remove(FileSystemComponent component) {
        children.remove(component);
    }

    @Override
    public FileSystemComponent getChild(int index) {
        return children.get(index);
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(depth) + " Директория: " + name);
        for (FileSystemComponent component : children) {
            component.display(depth + 2);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Directory root = new Directory("Корневая Директория");
        File file1 = new File("Файл1.txt");
        File file2 = new File("Файл2.txt");

        Directory subDir = new Directory("Поддиректория");
        File subFile1 = new File("Подфайл1.txt");

        root.add(file1);
        root.add(file2);
        subDir.add(subFile1);
        root.add(subDir);

        root.display(1);
    }
}




