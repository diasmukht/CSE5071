// 1 декоратор
public interface IBeverage {
    double getCost();
    String getDescription();
}


class Coffee implements IBeverage {
    @Override
    public double getCost() {
        return 50.0;
    }

    @Override
    public String getDescription() {
        return "Кофе";
    }
}


abstract class BeverageDecorator implements IBeverage {
    private final IBeverage beverage;

    public BeverageDecorator(IBeverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double getCost() {
        return beverage.getCost();
    }

    @Override
    public String getDescription() {
        return beverage.getDescription();
    }
}


class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 10.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Молоко";
    }
}


class SugarDecorator extends BeverageDecorator {
    public SugarDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 5.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Сахар";
    }
}


class ChocolateDecorator extends BeverageDecorator {
    public ChocolateDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 15.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Чиколад хачуу";
    }
}


class CafeOrderSystem {
    public static void main(String[] args) {
        IBeverage beverage = new Coffee();
        printBeverage(beverage);
        beverage = new MilkDecorator(beverage);
        printBeverage(beverage);
        beverage = new SugarDecorator(beverage);
        printBeverage(beverage);
        beverage = new ChocolateDecorator(beverage);
        printBeverage(beverage);
    }


    private static void printBeverage(IBeverage beverage) {
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());
    }
}





















// 2 адаптер
public interface IPaymentProcessor {
    void processPayment(double amount);
    void refundPayment(double amount);
}


class InternalPaymentProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Обработка платежа на сумму " + amount + " через внутреннюю систему.");
    }

    @Override
    public void refundPayment(double amount) {
        System.out.println("Возврат оплаты " + amount + " через внутреннюю систему.");
    }
}

class ExternalPaymentSystemA {
    public void makePayment(double amount) {
        System.out.println("Произведение оплаты " + amount + " через внешнюю платежную систему А.");
    }

    public void makeRefund(double amount) {
        System.out.println("Оформление возврата " + amount + " через внешнюю платежную систему А.");
    }
}

class ExternalPaymentSystemB {
    public void sendPayment(double amount) {
        System.out.println("Отправка платежа на сумму " + amount + " через внешнюю платежную систему B.");
    }

    public void processRefund(double amount) {
        System.out.println("Обработка возврата " + amount + " через внешнюю платежную систему B");
    }
}

class PaymentAdapterA implements IPaymentProcessor {
    private final ExternalPaymentSystemA externalSystemA;

    public PaymentAdapterA(ExternalPaymentSystemA externalSystemA) {
        this.externalSystemA = externalSystemA;
    }

    @Override
    public void processPayment(double amount) {
        externalSystemA.makePayment(amount);
    }

    @Override
    public void refundPayment(double amount) {
        externalSystemA.makeRefund(amount);
    }
}

class PaymentAdapterB implements IPaymentProcessor {
    private final ExternalPaymentSystemB externalSystemB;

    public PaymentAdapterB(ExternalPaymentSystemB externalSystemB) {
        this.externalSystemB = externalSystemB;
    }

    @Override
    public void processPayment(double amount) {
        externalSystemB.sendPayment(amount);
    }

    @Override
    public void refundPayment(double amount) {
        externalSystemB.processRefund(amount);
    }
}

class PaymentSystemDemo {
    public static void main(String[] args) {
        IPaymentProcessor internalProcessor = new InternalPaymentProcessor();
        internalProcessor.processPayment(100.0);
        internalProcessor.refundPayment(50.0);
        ExternalPaymentSystemA externalSystemA = new ExternalPaymentSystemA();
        IPaymentProcessor adapterA = new PaymentAdapterA(externalSystemA);
        adapterA.processPayment(200.0);
        adapterA.refundPayment(100.0);
        ExternalPaymentSystemB externalSystemB = new ExternalPaymentSystemB();
        IPaymentProcessor adapterB = new PaymentAdapterB(externalSystemB);
        adapterB.processPayment(300.0);
        adapterB.refundPayment(150.0);
    }
}

