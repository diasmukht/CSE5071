// 1
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void markAsLoaned() {
        isAvailable = false;
    }

    public void markAsAvailable() {
        isAvailable = true;
    }

    @Override
    public String toString() {
        return title + " от " + author + " (ISBN: " + isbn + ")";
    }
}

class Reader {
    private int id;
    private String name;
    private String email;

    public Reader(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Читатель: " + name + " (Электронная почта: " + email + ")";
    }
}

class Loan {
    private Book book;
    private Reader reader;
    private Date loanDate;
    private Date returnDate;

    public Loan(Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
        this.loanDate = new Date();
        this.returnDate = null;
    }

    public void completeLoan() {
        this.returnDate = new Date();
        book.markAsAvailable();
    }

    @Override
    public String toString() {
        return "Выдача: " + book.getTitle() + " для " + reader.getName() + " на " + loanDate
                + (returnDate != null ? ", возвращена " + returnDate : ", пока не возвращена");
    }
}

class Librarian {
    private List<Book> books = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void issueLoan(Book book, Reader reader) {
        if (book.isAvailable()) {
            Loan loan = new Loan(book, reader);
            loans.add(loan);
            book.markAsLoaned();
        }
    }

    public void listAvailableBooks() {
        System.out.println("Доступные книги:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book);
            }
        }
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Librarian librarian = new Librarian();

        Book book1 = new Book("Война и мир", "Лев Толстой", "123456");
        Book book2 = new Book("Преступление и наказание", "Федор Достоевский", "654321");

        librarian.addBook(book1);
        librarian.addBook(book2);

        Reader reader = new Reader(1, "Иван Иванов", "ivan@example.com");

        librarian.issueLoan(book1, reader);

        librarian.listAvailableBooks();
    }
}






// 2
import java.util.ArrayList;
import java.util.List;

interface IUserService {
    User register(String username, String password);
    User login(String username, String password);
}

interface IProductService {
    List<Product> getProducts();
    Product addProduct(Product product);
}

interface IOrderService {
    Order createOrder(int userId, List<Integer> productIds);
    Order getOrderStatus(int orderId);
}

interface IPaymentService {
    boolean processPayment(int orderId, double amount);
}

interface INotificationService {
    void sendNotification(int userId, String message);
}

class User {
    private int id;
    private String username;
    private String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }
}

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}

class Order {
    private int id;
    private int userId;
    private List<Product> products;
    private String status;

    public Order(int userId, List<Product> products) {
        this.userId = userId;
        this.products = products;
        this.status = "Created";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}

class OrderService implements IOrderService {
    private final IProductService productService;
    private final IPaymentService paymentService;
    private final INotificationService notificationService;

    public OrderService(IProductService productService, IPaymentService paymentService, INotificationService notificationService) {
        this.productService = productService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    @Override
    public Order createOrder(int userId, List<Integer> productIds) {
        List<Product> products = new ArrayList<>();
        for (Product product : productService.getProducts()) {
            if (productIds.contains(product.getId())) {
                products.add(product);
            }
        }

        if (products.isEmpty()) {
            throw new RuntimeException("No products found.");
        }

        Order order = new Order(userId, products);
        double totalAmount = products.stream().mapToDouble(Product::getPrice).sum();
        if (paymentService.processPayment(order.getId(), totalAmount)) {
            order.setStatus("Paid");
            notificationService.sendNotification(userId, "Your order is successfully paid.");
        } else {
            order.setStatus("Payment Failed");
            notificationService.sendNotification(userId, "Payment failed. Please try again.");
        }

        return order;
    }

    @Override
    public Order getOrderStatus(int orderId) {
        return new Order(orderId, new ArrayList<>());
    }
}

class ProductService implements IProductService {
    private List<Product> products = new ArrayList<>();

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }
}

class PaymentService implements IPaymentService {
    @Override
    public boolean processPayment(int orderId, double amount) {
        // Simulate payment processing
        return true;
    }
}

class NotificationService implements INotificationService {
    @Override
    public void sendNotification(int userId, String message) {
        System.out.println("Notification to user " + userId + ": " + message);
    }
}

public class OrderManagementSystem {
    public static void main(String[] args) {
        IProductService productService = new ProductService();
        IPaymentService paymentService = new PaymentService();
        INotificationService notificationService = new NotificationService();
        IOrderService orderService = new OrderService(productService, paymentService, notificationService);

        Product product1 = new Product(1, "Product 1", 100);
        Product product2 = new Product(2, "Product 2", 150);
        productService.addProduct(product1);
        productService.addProduct(product2);

        User user = new User(1, "user1", "password");

        List<Integer> productIds = new ArrayList<>();
        productIds.add(1);
        productIds.add(2);

        Order order = orderService.createOrder(user.getId(), productIds);
        System.out.println("Order Status: " + order.getStatus());
    }
}
