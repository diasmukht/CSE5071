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
