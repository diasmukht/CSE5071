//Facade
class RoomBookingSystem {
    public void bookRoom() {
        System.out.println("Номер забронирован.");
    }
    public void checkAvailability() {
        System.out.println("Доступность номеров проверена.");
    }
    public void cancelRoomBooking() {
        System.out.println("Бронирование номера отменено.");
    }
}

class RestaurantSystem {
    public void reserveTable() {
        System.out.println("Столик в ресторане забронирован.");
    }
    public void orderFood() {
        System.out.println("Еда заказана.");
    }
    public void orderTaxi() {
        System.out.println("Такси вызвано.");
    }
}

class EventManagementSystem {
    public void bookEventHall() {
        System.out.println("Конференц-зал забронирован.");
    }
    public void orderEquipment() {
        System.out.println("Оборудование для мероприятия заказано.");
    }
}

class CleaningService {
    public void scheduleCleaning() {
        System.out.println("Расписание уборки установлено.");
    }
    public void performCleaning() {
        System.out.println("Уборка выполнена.");
    }
}

class HotelFacade {
    private RoomBookingSystem roomBooking;
    private RestaurantSystem restaurant;
    private EventManagementSystem eventManagement;
    private CleaningService cleaning;

    public HotelFacade() {
        roomBooking = new RoomBookingSystem();
        restaurant = new RestaurantSystem();
        eventManagement = new EventManagementSystem();
        cleaning = new CleaningService();
    }

    public void bookRoomWithServices() {
        roomBooking.bookRoom();
        restaurant.orderFood();
        cleaning.scheduleCleaning();
    }

    public void organizeEvent() {
        eventManagement.bookEventHall();
        eventManagement.orderEquipment();
        roomBooking.bookRoom();
    }

    public void reserveTableWithTaxi() {
        restaurant.reserveTable();
        restaurant.orderTaxi();
    }

    public void cancelRoom() {
        roomBooking.cancelRoomBooking();
    }

    public void requestCleaning() {
        cleaning.performCleaning();
    }
}

public class HotelManagementSystem {
    public static void main(String[] args) {
        HotelFacade hotel = new HotelFacade();

        hotel.bookRoomWithServices();
        System.out.println();

        hotel.organizeEvent();
        System.out.println();

        hotel.reserveTableWithTaxi();
        System.out.println();

        hotel.cancelRoom();
        hotel.requestCleaning();
    }
}



//composite
import java.util.ArrayList;
import java.util.List;

abstract class OrgComponent {
    String name;

    OrgComponent(String name) {
        this.name = name;
    }

    void add(OrgComponent component) { }
    void remove(OrgComponent component) { }

    abstract void display(int level);
    abstract int getEmployeeCount();
    abstract double getTotalSalary();
}

class Employee extends OrgComponent {
    double salary;

    Employee(String name, double salary) {
        super(name);
        this.salary = salary;
    }

    @Override
    void display(int level) {
        System.out.println(" ".repeat(level) + "Сотрудник: " + name + ", зарплата: " + salary);
    }

    @Override
    int getEmployeeCount() {
        return 1;
    }

    @Override
    double getTotalSalary() {
        return salary;
    }
}


class Department extends OrgComponent {
    private List<OrgComponent> components = new ArrayList<>();

    Department(String name) {
        super(name);
    }

    @Override
    void add(OrgComponent component) {
        components.add(component);
    }

    @Override
    void remove(OrgComponent component) {
        components.remove(component);
    }

    @Override
    void display(int level) {
        System.out.println(" ".repeat(level) + "Отдел: " + name);
        for (OrgComponent component : components) {
            component.display(level + 2);
        }
    }

    @Override
    int getEmployeeCount() {
        int count = 0;
        for (OrgComponent component : components) {
            count += component.getEmployeeCount();
        }
        return count;
    }

    @Override
    double getTotalSalary() {
        double total = 0;
        for (OrgComponent component : components) {
            total += component.getTotalSalary();
        }
        return total;
    }
}


public class CorporateStructureDemo {
    public static void main(String[] args) {
        Department headOffice = new Department("Главный офис");
        Employee emp1 = new Employee("Анна", 50000);
        Employee emp2 = new Employee("Иван", 55000);

        headOffice.add(emp1);
        headOffice.add(emp2);

        Department branch = new Department("Филиал");
        Employee emp3 = new Employee("Мария", 40000);
        Employee emp4 = new Employee("Алексей", 45000);

        branch.add(emp3);
        branch.add(emp4);
        headOffice.add(branch);

        System.out.println("Структура компании:");
        headOffice.display(1);

        System.out.println("\nОбщее количество сотрудников: " + headOffice.getEmployeeCount());
        System.out.println("Общий бюджет: " + headOffice.getTotalSalary());
    }
}
