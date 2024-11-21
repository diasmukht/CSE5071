import java.time.LocalDate;
import java.util.*;

class Author {
    private String name;
    private List<Book> books;

    public Author(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }
}

class Book {
    private String title;
    private String isbn;
    private List<Author> authors;
    private int publicationYear;
    private boolean availabilityStatus;

    public Book(String title, String isbn, List<Author> authors, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.availabilityStatus = true;
    }

    public void changeAvailability(boolean status) {
        this.availabilityStatus = status;
    }

    public boolean isAvailable() {
        return availabilityStatus;
    }

    public String getTitle() {
        return title;
    }
}

abstract class User {
    private String id;
    private String name;
    private String email;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }
}

class Reader extends User {
    public Reader(String id, String name, String email) {
        super(id, name, email);
    }

    public Loan borrowBook(Book book) {
        if (!book.isAvailable()) {
            throw new IllegalStateException("Книга недоступна");
        }
        book.changeAvailability(false);
        return new Loan(book, this, LocalDate.now());
    }
}

class Librarian extends User {
    public Librarian(String id, String name, String email) {
        super(id, name, email);
    }

    public void addBook(Library library, Book book) {
        library.addBook(book);
    }

    public Report generateReport(Library library) {
        return new Report(library);
    }
}

class Loan {
    private Book book;
    private Reader reader;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(Book book, Reader reader, LocalDate loanDate) {
        this.book = book;
        this.reader = reader;
        this.loanDate = loanDate;
    }

    public void returnBook() {
        book.changeAvailability(true);
        this.returnDate = LocalDate.now();
    }
}

class Library {
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }
}

class Report {
    private Library library;

    public Report(Library library) {
        this.library = library;
    }

    public String generatePopularityReport() {
        return "Отчет о популярности книг сформирован.";
    }

    public String generateActivityReport() {
        return "Отчет об активности пользователей сформирован.";
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        Author author1 = new Author("Автор Один");
        Author author2 = new Author("Автор Два");

        Book book1 = new Book("Книга Один", "1234567890", Arrays.asList(author1), 2023);
        Book book2 = new Book("Книга Два", "0987654321", Arrays.asList(author2), 2024);

        author1.addBook(book1);
        author2.addBook(book2);

        library.addBook(book1);
        library.addBook(book2);

        Reader reader = new Reader("1", "Читатель Один", "reader@example.com");
        Librarian librarian = new Librarian("2", "Библиотекарь Один", "librarian@example.com");

        library.addUser(reader);
        library.addUser(librarian);

        Loan loan = reader.borrowBook(book1);
        library.addLoan(loan);

        Report report = librarian.generateReport(library);

        System.out.println(report.generatePopularityReport());
        System.out.println(report.generateActivityReport());

        printClassDiagram();
    }

    public static void printClassDiagram() {
        System.out.println("""
            +------------------+         +------------------+
            |     Library      |<-------◆|      Book        |
            +------------------+         +------------------+
            | List<Book> books |         | title: String    |
            | List<User> users |         | isbn: String     |
            | List<Loan> loans |         | authors: List<Author> |
            | + addBook()      |         | publicationYear: int |
            | + addUser()      |         | isAvailable: Boolean |
            | + listBooks()    |         +------------------+
            +------------------+                ^
                      ^                      |
                      |                      |
            +------------------+         +------------------+
            |     Reader       |<--------◆|     Loan        |
            +------------------+         +------------------+
            | name: String     |         | book: Book       |
            | email: String    |         | reader: Reader   |
            | id: String       |         | loanDate: LocalDate |
            +------------------+         | returnDate: LocalDate |
                                       +------------------+
                      ^                      ^
                      |                      |
            +------------------+         +------------------+
            |   Librarian      |<--------○|     Author      |
            +------------------+         +------------------+
            | name: String     |         | name: String     |
            | email: String    |         | books: List<Book> |
            | id: String       |         +------------------+
            +------------------+""");
    }
}
