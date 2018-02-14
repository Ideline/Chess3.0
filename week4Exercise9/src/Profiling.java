public class Profiling implements IBank {

    private IBank decorated;

    public Profiling(IBank decorated){
        this.decorated = decorated;
    }

    @Override
    public void withdraw(int amount) {
        long start = System.currentTimeMillis();
        decorated.withdraw(amount);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Withdraw took " + elapsed + " ms");
    }

    @Override
    public void deposit(int amount) {
        long start = System.currentTimeMillis();
        decorated.deposit(amount);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Deposit took " + elapsed + " ms");
    }
}
