import java.util.ArrayList;
import java.util.List;

class Book {
    String title, author, isbn;
    boolean isAvailable = true;

    Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
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
        } else {
            System.out.println("Книга недоступна.");
        }
    }
}

class Library {
    List<Book> books = new ArrayList<>();

    void addBook(Book book) {
        books.add(book);
    }

    void listBooks() {
        books.forEach(book -> System.out.println(book.title + " к " + book.author));
    }

    void displayDiagram() {
        System.out.println("""
                +------------------+         +------------------+
                |     Library      |<-------◆|      Book        |
                +------------------+         +------------------+
                | List<Book> books |         | title: String    |
                |                  |         | author: String   |
                | + addBook()      |         | isbn: String     |
                | + listBooks()    |         | isAvailable: Boolean |
                +------------------+         +------------------+
                      ^
                      |
                +------------------+         +------------------+
                |     Reader       |<--------○|    Librarian     |
                +------------------+         +------------------+
                | name: String     |         | name: String     |
                | List<Book> rented|         +------------------+
                +------------------+""");
    }
}

public class LibraryApp {
    public static void main(String[] args) {
        Library library = new Library();
        Book book1 = new Book("1984", "Джордж Оруэлл", "123");
        library.addBook(book1);

        Reader reader = new Reader("Диас");
        reader.rentBook(book1);

        library.listBooks();
        System.out.println("\nДиаграмма классов:");
        library.displayDiagram();
    }
}
