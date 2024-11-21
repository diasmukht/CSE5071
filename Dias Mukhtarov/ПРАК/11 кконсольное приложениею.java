
public class Book {
    private String title;
    private String author;
    private String genre;
    private String isbn;

    public Book(String title, String author, String genre, String isbn) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Название: " + title + ", Автор: " + author + ", Жанр: " + genre + ", ISBN: " + isbn;
    }
}


public class Reader {
    private String firstName;
    private String lastName;
    private String ticketNumber;

    public Reader(String firstName, String lastName, String ticketNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ticketNumber = ticketNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }
}


public class Librarian {
    public void issueBook(Book book, Reader reader, AccountSystem accountSystem) {
        accountSystem.issueBook(book, reader);
        System.out.println("Книга выдана: " + book + " читателю " + reader.getFullName());
    }

    public void returnBook(Book book, Reader reader, AccountSystem accountSystem) {
        accountSystem.returnBook(book, reader);
        System.out.println("Книга возвращена: " + book + " от читателя " + reader.getFullName());
    }
}


import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private List<Book> books;

    public Catalog() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByGenre(String genre) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                result.add(book);
            }
        }
        return result;
    }

    public void printCatalog() {
        for (Book book : books) {
            System.out.println(book);
        }
    }
}

import java.util.HashMap;
import java.util.Map;

public class AccountSystem {
    private Map<String, Book> issuedBooks;

    public AccountSystem() {
        issuedBooks = new HashMap<>();
    }

    public void issueBook(Book book, Reader reader) {
        issuedBooks.put(reader.getTicketNumber(), book);
    }

    public void returnBook(Book book, Reader reader) {
        issuedBooks.remove(reader.getTicketNumber());
    }

    public boolean isBookIssued(Book book) {
        return issuedBooks.containsValue(book);
    }
}


public interface CatalogService {
    void addBook(Book book);
    List<Book> searchByAuthor(String author);
    List<Book> searchByTitle(String title);
    List<Book> searchByGenre(String genre);
}

public interface AccountService {
    void issueBook(Book book, Reader reader);
    void returnBook(Book book, Reader reader);
    boolean isBookIssued(Book book);
}

public class LibraryApp {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        AccountSystem accountSystem = new AccountSystem();
        Librarian librarian = new Librarian();

        Book book1 = new Book("Java Programming", "John Doe", "Programming", "978-3-16-148410-0");
        Book book2 = new Book("Effective Java", "Joshua Bloch", "Programming", "978-0-13-468599-1");
        catalog.addBook(book1);
        catalog.addBook(book2);

        Reader reader1 = new Reader("Алиса", "Смит", "R001");
        Reader reader2 = new Reader("Боб", "Джонсон", "R002");

        catalog.printCatalog();

        librarian.issueBook(book1, reader1, accountSystem);
        librarian.returnBook(book1, reader1, accountSystem);
        
        List<Book> booksByAuthor = catalog.searchByAuthor("Joshua Bloch");
        System.out.println("Книги Джошуа Блоха:");
        for (Book book : booksByAuthor) {
            System.out.println(book);
        }
        
        List<Book> booksByTitle = catalog.searchByTitle("Java Programming");
        System.out.println("Книги с названием 'Java Programming':");
        for (Book book : booksByTitle) {
            System.out.println(book);
        }

        List<Book> booksByGenre = catalog.searchByGenre("Programming");
        System.out.println("Книги жанра 'Programming':");
        for (Book book : booksByGenre) {
            System.out.println(book);
        }
    }
}
