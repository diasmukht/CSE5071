import java.util.*;
import java.time.LocalDate;

public interface IHotelService {
    List<Hotel> findHotelsByCriteria(String location, String roomType, double priceRange);
    Hotel getHotelInfo(String hotelId);
}

public interface IBookingService {
    boolean checkAvailability(String hotelId, String roomType, LocalDate startDate, LocalDate endDate);
    Booking bookRoom(String hotelId, String userId, String roomType, LocalDate startDate, LocalDate endDate);
}

public interface IPaymentService {
    boolean processPayment(String userId, double amount, String paymentMethod);
    boolean verifyPayment(String paymentId);
}

public interface INotificationService {
    void sendBookingConfirmation(String userId, Booking booking);
    void sendReminder(String userId, Booking booking);
}

public interface IUserManagementService {
    boolean registerUser(User user);
    boolean loginUser(String username, String password);
}

public class HotelService implements IHotelService {
    private Map<String, Hotel> hotelsDatabase;

    @Override
    public List<Hotel> findHotelsByCriteria(String location, String roomType, double priceRange) {
        return hotelsDatabase.values().stream()
            .filter(hotel -> hotel.getLocation().equals(location) &&
                             hotel.getRoomType().equals(roomType) &&
                             hotel.getPrice() <= priceRange)
            .collect(Collectors.toList());
    }

    @Override
    public Hotel getHotelInfo(String hotelId) {
        return hotelsDatabase.get(hotelId);
    }
}

public class BookingService implements IBookingService {
    private Map<String, Booking> bookingsDatabase;

    @Override
    public boolean checkAvailability(String hotelId, String roomType, LocalDate startDate, LocalDate endDate) {
        return bookingsDatabase.values().stream()
            .noneMatch(booking -> booking.getHotelId().equals(hotelId) &&
                                  booking.getRoomType().equals(roomType) &&
                                  booking.getStartDate().isBefore(endDate) &&
                                  booking.getEndDate().isAfter(startDate));
    }

    @Override
    public Booking bookRoom(String hotelId, String userId, String roomType, LocalDate startDate, LocalDate endDate) {
        if (checkAvailability(hotelId, roomType, startDate, endDate)) {
            Booking booking = new Booking(userId, hotelId, roomType, startDate, endDate);
            bookingsDatabase.put(booking.getId(), booking);
            return booking;
        }
        return null;
    }
}

public class PaymentService implements IPaymentService {
    private Map<String, Payment> paymentsDatabase;

    @Override
    public boolean processPayment(String userId, double amount, String paymentMethod) {
        Payment payment = new Payment(userId, amount, paymentMethod);
        paymentsDatabase.put(payment.getId(), payment);
        return true;
    }

    @Override
    public boolean verifyPayment(String paymentId) {
        Payment payment = paymentsDatabase.get(paymentId);
        return payment != null && payment.isVerified();
    }
}

public class NotificationService implements INotificationService {
    @Override
    public void sendBookingConfirmation(String userId, Booking booking) {
        System.out.println("Бронирование подтверждено для пользователя: " + userId + ", детали бронирования: " + booking);
    }

    @Override
    public void sendReminder(String userId, Booking booking) {
        System.out.println("Напоминание для пользователя: " + userId + " для бронирования: " + booking);
    }
}

public class UserManagementService implements IUserManagementService {
    private Map<String, User> usersDatabase;

    @Override
    public boolean registerUser(User user) {
        if (!usersDatabase.containsKey(user.getUsername())) {
            usersDatabase.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    @Override
    public boolean loginUser(String username, String password) {
        User user = usersDatabase.get(username);
        return user != null && user.getPassword().equals(password);
    }
}

public class Main {
    public static void main(String[] args) {
        UIComponent ui = new UIComponent();
        ui.start();
    }
}

class Hotel {
    private String id;
    private String location;
    private String roomType;
    private double price;

    public String getId() { return id; }
    public String getLocation() { return location; }
    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
}

class Booking {
    private String id;
    private String userId;
    private String hotelId;
    private String roomType;
    private LocalDate startDate;
    private LocalDate endDate;

    public Booking(String userId, String hotelId, String roomType, LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() { return id; }
    public String getHotelId() { return hotelId; }
    public String getRoomType() { return roomType; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
}

class Payment {
    private String id;
    private String userId;
    private double amount;
    private String paymentMethod;
    private boolean verified;

    public Payment(String userId, double amount, String paymentMethod) {
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public String getId() { return id; }
    public boolean isVerified() { return verified; }
}

class User {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}

class UIComponent {
    public void start() {
        //
    }
}
