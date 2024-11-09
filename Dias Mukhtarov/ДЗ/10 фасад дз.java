//facade
class TV {
    void on() { System.out.println("TV включен."); }
    void off() { System.out.println("TV выключен."); }
    void setChannel(int channel) { System.out.println("Канал установлен на " + channel); }
}


class AudioSystem {
    void on() { System.out.println("Аудиосистема включена."); }
    void off() { System.out.println("Аудиосистема выключена."); }
    void setVolume(int level) { System.out.println("Громкость установлена на " + level); }
}


class DVDPlayer {
    void play() { System.out.println("Воспроизведение DVD."); }
    void pause() { System.out.println("DVD на паузе."); }
    void stop() { System.out.println("Воспроизведение DVD остановлено."); }
}


class GameConsole {
    void on() { System.out.println("Игровая консоль включена."); }
    void startGame(String game) { System.out.println("Запуск игры: " + game); }
}


class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audio;
    private DVDPlayer dvd;
    private GameConsole console;

    HomeTheaterFacade(TV tv, AudioSystem audio, DVDPlayer dvd, GameConsole console) {
        this.tv = tv;
        this.audio = audio;
        this.dvd = dvd;
        this.console = console;
    }


    void watchMovie() {
        System.out.println("\nПодготовка системы для просмотра фильма...");
        tv.on();
        audio.on();
        audio.setVolume(15);
        dvd.play();
        System.out.println("Приятного просмотра фильма!\n");
    }


    void shutdown() {
        System.out.println("\nВыключение всей системы...");
        dvd.stop();
        tv.off();
        audio.off();
        console.on();
        System.out.println("Система выключена.\n");
    }


    void playGame(String game) {
        System.out.println("\nПодготовка системы для игры...");
        tv.on();
        console.on();
        console.startGame(game);
        System.out.println("Приятной игры!\n");
    }


    void listenToMusic() {
        System.out.println("\nПодготовка системы для прослушивания музыки...");
        tv.on();
        audio.on();
        audio.setVolume(20);
        System.out.println("Приятного прослушивания музыки!\n");
    }


    void setVolume(int level) {
        audio.setVolume(level);
        System.out.println("Громкость изменена на " + level);
    }
}


public class MultimediaCenterDemo {
    public static void main(String[] args) {
        TV tv = new TV();
        AudioSystem audio = new AudioSystem();
        DVDPlayer dvd = new DVDPlayer();
        GameConsole console = new GameConsole();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, audio, dvd, console);


        homeTheater.watchMovie();
        homeTheater.setVolume(10);
        homeTheater.playGame("Super Mario");
        homeTheater.listenToMusic();
        homeTheater.shutdown();
    }
}




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
