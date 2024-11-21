class HotelService {
    void searchHotels() {
        System.out.println("Ищем отели.");
    }
}

class BookingService {
    void bookHotel() {
        System.out.println("Бронирование отеля.");
    }
}

class PaymentService {
    void processPayment() {
        System.out.println("Обработка платежа.");
    }
}

class NotificationService {
    void sendNotification() {
        System.out.println("Отправка уведомления.");
    }
}

class UserManagementService {
    void manageUser() {
        System.out.println("Управляющий пользователь.");
    }
}

class UIComponent {
    HotelService hotelService = new HotelService();
    BookingService bookingService = new BookingService();
    PaymentService paymentService = new PaymentService();
    NotificationService notificationService = new NotificationService();
    UserManagementService userManagementService = new UserManagementService();

    void displayDiagram() {
        System.out.println("""
                +----------------------+           +--------------------+
                |    UI Component      |---------->|  HotelService      |
                +----------------------+           +--------------------+
                        |                               |
                        v                               v
                +----------------------+           +--------------------+
                |  BookingService      |---------->|  PaymentService    |
                +----------------------+           +--------------------+
                        |
                        v
                +----------------------+           +--------------------+
                | NotificationService   |<--------->| UserManagementSvc  |
                +----------------------+           +--------------------+
                """);
    }

    void performActions() {
        hotelService.searchHotels();
        bookingService.bookHotel();
        paymentService.processPayment();
        notificationService.sendNotification();
        userManagementService.manageUser();
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        UIComponent ui = new UIComponent();
        System.out.println("\nДиаграмма компонентов:");
        ui.displayDiagram();
        System.out.println("\nДемонстрация работы компонентов:");
        ui.performActions();
    }
}
