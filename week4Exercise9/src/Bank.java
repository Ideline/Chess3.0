public class Bank implements IBank {


    public Bank(){
    }

    @Override
    public void withdraw(int amount) {
        System.out.println("Bank Withdraw " + amount);
    }

    @Override
    public void deposit(int amount) {
        System.out.println("Bank Deposit " + amount);
    }

}
