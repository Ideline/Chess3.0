public class Logging implements IBank {


    private IBank decorated;

    public Logging(IBank decorated){
        this.decorated = decorated;
    }

    @Override
    public void withdraw(int amount) {
        System.out.println("Logging: Whitdraw " + amount);
        decorated.withdraw(amount);
    }

    @Override
    public void deposit(int amount) {
        System.out.println("Logging: Deposit " + amount);
        decorated.deposit(amount);
    }
}
