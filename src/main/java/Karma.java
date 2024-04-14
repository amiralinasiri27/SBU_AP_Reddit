import java.util.ArrayList;

public class Karma {
     private int karma;
     private ArrayList<Account> karmaSetters;

     public Karma() {
         this.karma = 0;
         this.karmaSetters = new ArrayList<>();
     }

    public int getKarmaPoint() {
        return this.karma;
    }

    public void addKarma() {
        this.karma += 1;
    }

    public void deductKarma() {
        this.karma -= 1;
    }

    public void addKarmaSetter(Account account) {
         if (isKarmaSetterNew(account)) {
             this.karmaSetters.add(account);
         }
    }

    public boolean isKarmaSetterNew(Account account) {
        for (Account account1 : this.karmaSetters) {
            if (account1.getID() == account.getID()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Account> getKarmaSetters() {
         return this.karmaSetters;
    }
}
