//import java.util.*;
//
//
//interface IReport {
//    String generate();
//}
//
//
//class SalesReport implements IReport {
//    @Override
//    public String generate() {
//        return "Отчет о продажах: отображение данных о продажах...";
//    }
//}
//
//
//class UserReport implements IReport {
//    @Override
//    public String generate() {
//        return "Пользовательский отчет: отображение пользовательских данных...";
//    }
//}
//
//
//abstract class ReportDecorator implements IReport {
//    protected IReport report;
//
//    public ReportDecorator(IReport report) {
//        this.report = report;
//    }
//
//    @Override
//    public String generate() {
//        return report.generate();
//    }
//}
//
//
//class DateFilterDecorator extends ReportDecorator {
//    private Date startDate, endDate;
//
//    public DateFilterDecorator(IReport report, Date startDate, Date endDate) {
//        super(report);
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }
//
//    @Override
//    public String generate() {
//        return super.generate() + "\nОтфильтровано по датам: с " + startDate + " к " + endDate;
//    }
//}
//
//
//class AmountFilterDecorator extends ReportDecorator {
//    private double minAmount, maxAmount;
//
//    public AmountFilterDecorator(IReport report, double minAmount, double maxAmount) {
//        super(report);
//        this.minAmount = minAmount;
//        this.maxAmount = maxAmount;
//    }
//
//    @Override
//    public String generate() {
//        return super.generate() + "\nФильтрация по сумме продаж: от " + minAmount + " к " + maxAmount;
//    }
//}
//
//
//class SortingDecorator extends ReportDecorator {
//    private String criteria;
//
//    public SortingDecorator(IReport report, String criteria) {
//        super(report);
//        this.criteria = criteria;
//    }
//
//    @Override
//    public String generate() {
//        return super.generate() + "\nСортировать по: " + criteria;
//    }
//}
//
//
//class CsvExportDecorator extends ReportDecorator {
//    public CsvExportDecorator(IReport report) {
//        super(report);
//    }
//
//    @Override
//    public String generate() {
//        return super.generate() + "\nЭкспортируется в формате CSV.";
//    }
//}
//
//
//class PdfExportDecorator extends ReportDecorator {
//    public PdfExportDecorator(IReport report) {
//        super(report);
//    }
//
//    @Override
//    public String generate() {
//        return super.generate() + "\nЭкспортирован в формате PDF.";
//    }
//}
//
//
//public class ReportSystem {
//    public static void main(String[] args) {
//        IReport report = new SalesReport();
//
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Выберите опции для создания отчета:");
//
//        System.out.print("1. Применить фильтр по дате? (y/n): ");
//        if (scanner.nextLine().equalsIgnoreCase("y")) {
//            report = new DateFilterDecorator(report, new Date(2023, 1, 1), new Date(2023, 12, 31));
//        }
//
//        System.out.print("2. Применить фильтр по сумме продаж? (y/n): ");
//        if (scanner.nextLine().equalsIgnoreCase("y")) {
//            System.out.print("Введите минимальную сумму: ");
//            double minAmount = scanner.nextDouble();
//            System.out.print("Введите максимальную сумму: ");
//            double maxAmount = scanner.nextDouble();
//            scanner.nextLine();
//            report = new AmountFilterDecorator(report, minAmount, maxAmount);
//        }
//
//        System.out.print("3. Применить сортировку? (y/n): ");
//        if (scanner.nextLine().equalsIgnoreCase("y")) {
//            System.out.print("Введите критерий сортировки (например, date, amount): ");
//            String criteria = scanner.nextLine();
//            report = new SortingDecorator(report, criteria);
//        }
//
//        System.out.print("4. Экспорт в CSV? (y/n): ");
//        if (scanner.nextLine().equalsIgnoreCase("y")) {
//            report = new CsvExportDecorator(report);
//        }
//
//        System.out.print("5. Экспорт в PDF? (y/n): ");
//        if (scanner.nextLine().equalsIgnoreCase("y")) {
//            report = new PdfExportDecorator(report);
//        }
//
//
//        System.out.println("\nСформированный отчет:");
//        System.out.println(report.generate());
//
//        scanner.close();
//    }
//}






import java.util.*;
interface IInternalDeliveryService {
    void deliverOrder(String orderId);
    String getDeliveryStatus(String orderId);
    double calculateDeliveryCost(double distance, double weight);
}
class InternalDeliveryService implements IInternalDeliveryService {
    @Override
    public void deliverOrder(String orderId) {
        System.out.println("Доставка заказа " + orderId + " через внутренний сервис.");
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return "Внутренняя служба: Заказать " + orderId + " находится в пути.";
    }

    @Override
    public double calculateDeliveryCost(double distance, double weight) {
        return distance * weight * 0.5;
    }
}
class ExternalLogisticsServiceA {
    public void shipItem(int itemId) {
        System.out.println("Товар для доставки " + itemId + " через службу внешней логистики А.");
    }

    public String trackShipment(int shipmentId) {
        return "Услуга А: Доставка " + shipmentId + " находится в пути.";
    }

    public double getShippingCost(double distance, double weight) {
        return distance * weight * 0.8;
    }
}
class ExternalLogisticsServiceB {
    public void sendPackage(String packageInfo) {
        System.out.println("Отправка посылки " + packageInfo + " через внешнюю логистическую службу Б.");
    }

    public String checkPackageStatus(String trackingCode) {
        return "Услуга Б: Пакет " + trackingCode + " был отправлен.";
    }

    public double calculateCost(double distance, double weight) {
        return distance * weight * 0.7;
    }
}
class LogisticsAdapterA implements IInternalDeliveryService {
    private ExternalLogisticsServiceA serviceA;

    public LogisticsAdapterA(ExternalLogisticsServiceA serviceA) {
        this.serviceA = serviceA;
    }

    @Override
    public void deliverOrder(String orderId) {
        int itemId = Integer.parseInt(orderId);
        serviceA.shipItem(itemId);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        int shipmentId = Integer.parseInt(orderId);
        return serviceA.trackShipment(shipmentId);
    }

    @Override
    public double calculateDeliveryCost(double distance, double weight) {
        return serviceA.getShippingCost(distance, weight);
    }
}
class LogisticsAdapterB implements IInternalDeliveryService {
    private ExternalLogisticsServiceB serviceB;

    public LogisticsAdapterB(ExternalLogisticsServiceB serviceB) {
        this.serviceB = serviceB;
    }

    @Override
    public void deliverOrder(String orderId) {
        serviceB.sendPackage("Order: " + orderId);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return serviceB.checkPackageStatus(orderId);
    }

    @Override
    public double calculateDeliveryCost(double distance, double weight) {
        return serviceB.calculateCost(distance, weight);
    }
}
class ExternalLogisticsServiceC {
    public void dispatch(int orderRef) {
        System.out.println("Ссылка на заказ на отправку " + orderRef + " через внешнюю логистическую службу C.");
    }

    public String status(int orderRef) {
        return "Услуга C: Заказ " + orderRef + " Статус обрабатывается.";
    }

    public double costEstimate(double distance, double weight) {
        return distance * weight * 0.6;
    }
}
class LogisticsAdapterC implements IInternalDeliveryService {
    private ExternalLogisticsServiceC serviceC;

    public LogisticsAdapterC(ExternalLogisticsServiceC serviceC) {
        this.serviceC = serviceC;
    }

    @Override
    public void deliverOrder(String orderId) {
        int orderRef = Integer.parseInt(orderId);
        serviceC.dispatch(orderRef);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        int orderRef = Integer.parseInt(orderId);
        return serviceC.status(orderRef);
    }

    @Override
    public double calculateDeliveryCost(double distance, double weight) {
        return serviceC.costEstimate(distance, weight);
    }
}
class DeliveryServiceFactory {
    public static IInternalDeliveryService getDeliveryService(String serviceType) {
        switch (serviceType) {
            case "Internal":
                return new InternalDeliveryService();
            case "ServiceA":
                return new LogisticsAdapterA(new ExternalLogisticsServiceA());
            case "ServiceB":
                return new LogisticsAdapterB(new ExternalLogisticsServiceB());
            case "ServiceC":
                return new LogisticsAdapterC(new ExternalLogisticsServiceC());
            default:
                throw new IllegalArgumentException("");
        }
    }
}
public class LogisticsSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите тип службы доставки (Internal, ServiceA, ServiceB, ServiceC): ");
        String serviceType = scanner.nextLine();

        IInternalDeliveryService deliveryService = DeliveryServiceFactory.getDeliveryService(serviceType);

        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine();

        deliveryService.deliverOrder(orderId);

        System.out.println("Статус доставки: " + deliveryService.getDeliveryStatus(orderId));

        System.out.print("Введите расстояние для расчета стоимости: ");
        double distance = scanner.nextDouble();

        System.out.print("Введите вес для расчета стоимости: ");
        double weight = scanner.nextDouble();

        double cost = deliveryService.calculateDeliveryCost(distance, weight);
        System.out.println("Стоимость доставки: $" + cost);

        scanner.close();
    }
}
