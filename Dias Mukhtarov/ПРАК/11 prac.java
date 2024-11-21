import java.util.ArrayList;
import java.util.List;

class Book {
    String title, author, genre, isbn;
    boolean isAvailable = true;

    Book(String title, String author, String genre, String isbn) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }
}

class Reader {
    String name;
    List<Book> rentedBooks = new ArrayList<>();

    Reader(String name) {
        this.name = name;
    }

    void rentBook(Book book) {
        if (book.isAvailable) {
            rentedBooks.add(book);
            book.isAvailable = false;
            System.out.println(name + " взял книгу: " + book.title);
        } else {
            System.out.println("Книга " + book.title + " недоступна.");
        }
    }
}

class Librarian {
    String name;

    Librarian(String name) {
        this.name = name;
    }

    void issueBook(Book book, Reader reader) {
        if (book.isAvailable) {
            reader.rentBook(book);
            System.out.println(name + " выдал книгу: " + book.title + " читателю: " + reader.name);
        } else {
            System.out.println("Книга " + book.title + " уже выдана.");
        }
    }
}

class Library {
    List<Book> books = new ArrayList<>();

    void addBook(Book book) {
        books.add(book);
    }

    void listBooks() {
        books.forEach(book -> System.out.println(book.title + " - " + book.author + " [" + (book.isAvailable ? "Доступна" : "Выдана") + "]"));
    }

    void displayDiagram() {
        System.out.println("""
                +------------------+         +------------------+
                |     Library      |<-------◆|      Book        |
                +------------------+         +------------------+
                | List<Book> books |         | title: String    |
                |                  |         | author: String   |
                | + addBook()      |         | genre: String    |
                | + listBooks()    |         | isbn: String     |
                +------------------+         | isAvailable: Boolean |
                      ^                     +------------------+
                      |
                +------------------+         +------------------+
                |     Reader       |         |    Librarian     |
                +------------------+         +------------------+
                | name: String     |         | name: String     |
                | List<Book> rented|         | + issueBook()    |
                +------------------+         +------------------+""");
    }
}

public class LibraryApp {
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book("1984", "Джордж Оруэлл", "Фантастика", "123");
        Book book2 = new Book("Мастер и Маргарита", "Михаил Булгаков", "Роман", "456");
        library.addBook(book1);
        library.addBook(book2);

        Reader reader = new Reader("Диас");
        Librarian librarian = new Librarian("Алиса");

        librarian.issueBook(book1, reader);
        librarian.issueBook(book2, reader);

        System.out.println("\nСписок книг в библиотеке:");
        library.listBooks();

        System.out.println("\nДиаграмма классов:");
        library.displayDiagram();
    }
}
