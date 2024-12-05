class IdleState implements State {
    private TicketMachine machine;

    public IdleState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Билет выбран. Ожидание оплаты.");
        machine.setState(machine.getWaitingForMoneyState());
    }

    @Override
    public void insertMoney(int amount) {
        System.out.println("Сначала выберите билет.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Билет не выбран.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Нет транзакции для отмены.");
    }
}

class WaitingForMoneyState implements State {
    private TicketMachine machine;

    public WaitingForMoneyState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Билет уже выбран. Ожидание оплаты.");
    }

    @Override
    public void insertMoney(int amount) {
        machine.addBalance(amount);
        System.out.println("Внесено: " + amount + ". Текущий баланс: " + machine.getCurrentBalance());

        if (machine.getCurrentBalance() >= 50) {
            System.out.println("Средств достаточно.");
            machine.setState(machine.getMoneyReceivedState());
        }
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Недостаточно средств. Пожалуйста, внесите полную сумму.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Транзакция отменена. Возврат в начальное состояние.");
        machine.resetBalance();
        machine.setState(machine.getTransactionCanceledState());
    }
}

class MoneyReceivedState implements State {
    private TicketMachine machine;

    public MoneyReceivedState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Билет уже выбран. Готово к выдаче.");
    }

    @Override
    public void insertMoney(int amount) {
        System.out.println("Средства уже внесены. Идет выдача билета.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Билет выдан.");
        machine.resetBalance();
        machine.setState(machine.getTicketDispensedState());
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Транзакция отменена. Возврат в начальное состояние.");
        machine.resetBalance();
        machine.setState(machine.getTransactionCanceledState());
    }
}

class TicketDispensedState implements State {
    private TicketMachine machine;

    public TicketDispensedState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Транзакция завершена. Начните новую.");
    }

    @Override
    public void insertMoney(int amount) {
        System.out.println("Транзакция завершена. Начните новую.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Билет уже выдан.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Транзакция завершена. Отмена невозможна.");
    }
}

class TransactionCanceledState implements State {
    private TicketMachine machine;

    public TransactionCanceledState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Транзакция отменена. Возврат в начальное состояние.");
        machine.setState(machine.getIdleState());
    }

    @Override
    public void insertMoney(int amount) {
        System.out.println("Транзакция отменена. Пожалуйста, выберите новый билет.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Транзакция отменена. Билет не выдан.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Транзакция уже отменена.");
    }
}

class TicketMachine {
    private State idleState;
    private State waitingForMoneyState;
    private State moneyReceivedState;
    private State ticketDispensedState;
    private State transactionCanceledState;

    private State currentState;
    private int currentBalance = 0;

    public TicketMachine() {
        idleState = new IdleState(this);
        waitingForMoneyState = new WaitingForMoneyState(this);
        moneyReceivedState = new MoneyReceivedState(this);
        ticketDispensedState = new TicketDispensedState(this);
        transactionCanceledState = new TransactionCanceledState(this);

        currentState = idleState;
    }

    public void selectTicket() {
        currentState.selectTicket();
    }

    public void insertMoney(int amount) {
        currentState.insertMoney(amount);
    }

    public void dispenseTicket() {
        currentState.dispenseTicket();
    }

    public void cancelTransaction() {
        currentState.cancelTransaction();
    }

    public void setState(State state) {
        currentState = state;
    }

    public State getIdleState() {
        return idleState;
    }

    public State getWaitingForMoneyState() {
        return waitingForMoneyState;
    }

    public State getMoneyReceivedState() {
        return moneyReceivedState;
    }

    public State getTicketDispensedState() {
        return ticketDispensedState;
    }

    public State getTransactionCanceledState() {
        return transactionCanceledState;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void addBalance(int amount) {
        currentBalance += amount;
    }

    public void resetBalance() {
        currentBalance = 0;
    }
}

public class Main {
    public static void main(String[] args) {
        TicketMachine ticketMachine = new TicketMachine();

        ticketMachine.selectTicket();
        ticketMachine.insertMoney(20);
        ticketMachine.insertMoney(30);
        ticketMachine.dispenseTicket();
    }
}
