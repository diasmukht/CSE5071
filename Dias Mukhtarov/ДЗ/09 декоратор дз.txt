import java.util.Scanner;

interface Beverage {
    double getCost();
    String getDescription();
}
class Espresso implements Beverage {
    @Override
    public double getCost() {
        return 50.0;
    }

    @Override
    public String getDescription() {
        return "Экспрессо";
    }
}
class Tea implements Beverage {
    @Override
    public double getCost() {
        return 30.0;
    }

    @Override
    public String getDescription() {
        return "Индийский чай";
    }
}

class Latte implements Beverage {
    @Override
    public double getCost() {
        return 60.0;
    }

    @Override
    public String getDescription() {
        return "Ләтте";
    }
}

class Mocha implements Beverage {
    @Override
    public double getCost() {
        return 70.0;
    }

    @Override
    public String getDescription() {
        return "Mocha(мотчха)";
    }
}

abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
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

class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) {
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

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) {
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

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 15.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Взбитые сливки";
    }
}

class Syrup extends BeverageDecorator {
    public Syrup(Beverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 12.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Сироп";
    }
}

public class CafeOrderSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Beverage beverage = null;

        System.out.println("Выберите напиток:");
        System.out.println("1 - Экспресс, 2 - Индийский чай, 3 - Ләтте, 4 - Mocha(мочха)");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> beverage = new Espresso();
            case 2 -> beverage = new Tea();
            case 3 -> beverage = new Latte();
            case 4 -> beverage = new Mocha();
            default -> System.out.println("error");
        }
        if (beverage != null) {
            boolean addMore = true;
            while (addMore) {
                System.out.println("Выберите добавку:");
                System.out.println("1 - Молоко, 2 - Сахар, 3 - Взбитые сливки, 4 - Сироп, 5 - Закончить добавление");

                int addChoice = scanner.nextInt();
                switch (addChoice) {
                    case 1 -> beverage = new Milk(beverage);
                    case 2 -> beverage = new Sugar(beverage);
                    case 3 -> beverage = new WhippedCream(beverage);
                    case 4 -> beverage = new Syrup(beverage);
                    case 5 -> addMore = false;
                    default -> System.out.println("error");
                }
            }
            System.out.println("Итоговый заказ: " + beverage.getDescription());
            System.out.println("Общая стоимость: " + beverage.getCost());
        }
        scanner.close();
    }
}
