public class Main {

    // Vi ska kunna Singleton, Decorator, Chain of command, Strategy

    public static void main(String[] args) {

        IBank bank = new Profiling(new Logging(new Bank()));

        bank.deposit(100);
        System.out.println();
        bank.withdraw(150);

    }


}
