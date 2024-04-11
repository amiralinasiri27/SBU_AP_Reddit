import java.util.ArrayList;

public class DataBase {
    private ArrayList<Account> accounts;
    private ArrayList<Subreddit> subreddits;

    public DataBase() {
        this.accounts = new ArrayList<>();
        this.subreddits = new ArrayList<>();
    }

    // additional methods
    public void addAccount(Account account) {
        if (isAccountNew(account)) { this.accounts.add(account);}
    }

    private boolean isAccountNew(Account account) {
        for (Account account1 : this.accounts) {
            if (account.getID() == account1.getID()) {
                return false;
            }
        }
        return true;
    }
    public void addSubreddit(Subreddit subreddit) {
        if (isSubredditNew(subreddit)) { this.subreddits.add(subreddit);}
    }

    private boolean isSubredditNew(Subreddit subreddit) {
        for (Subreddit subreddit1 : this.subreddits) {
            if (subreddit1.getSubredditID() == subreddit.getSubredditID()) {
                return false;
            }
        }
        return true;
    }

    // getter methods
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
    public ArrayList<Subreddit> getSubreddits() {
        return this.subreddits;
    }

    public boolean isUsernameInDataBase(String username) {
        for (Account account : this.getAccounts()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmailInDataBase(String email) {
        for (Account account : this.getAccounts()) {
            if (account.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSubredditNameNew(String name) {
        for (Subreddit subreddit : this.subreddits) {
            if (subreddit.getTopic().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPostNameNew(String name) {
        for (Subreddit subreddit : this.subreddits) {
            for (Post post : subreddit.getPosts()) {
                if (post.getTitle().equals(name)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void viewAllSubreddits() {
        if (!this.subreddits.isEmpty()) {
            for (Subreddit subreddit : this.subreddits) {
                subreddit.showAllPosts();
            }
        }
        else {
            System.out.println("*** No Subreddits Yet ***");
        }
    }

    public void viewAllSubredditsTopics() {
        if (!this.subreddits.isEmpty()) {
            for (Subreddit subreddit : this.subreddits) {
                System.out.println(subreddit.getTopic());
            }
        }
        else {
            System.out.println("*** No Subreddits Yet ***");
        }
    }

    public ArrayList<Subreddit> getSubredditByName(String subredditName) {
        ArrayList<Subreddit> subreddits1 = new ArrayList<>();
        for (Subreddit subreddit : this.subreddits) {
            if (subreddit.getTopic().equals(subredditName)) {
                subreddits1.add(subreddit);
            }
        }
        return subreddits1;
    }

    public Account getAccountByUsername(String username) {
        for (Account account : this.accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public int searchByName(String name) {
        int subredditsFound = 0;
        int accountsFound = 0;

        System.out.println("##### Communities #####");
        for (Subreddit subreddit : this.subreddits) {
            if (subreddit.getTopic().equals(name)) {
                subredditsFound++;
                System.out.println("r/" + subreddit.getTopic());
            }
        }
        if (subredditsFound == 0) {
            System.out.println("No Communities Found");
        }

        System.out.println("##### Users #####");
        for (Account account : this.accounts) {
            if (account.getUsername().equals(name)) {
                accountsFound++;
                System.out.println("u/" + account.getUsername());
            }
        }
        if (accountsFound == 0) {
            System.out.println("No Users Found");
        }

        if (accountsFound == 0 && subredditsFound == 0) {
            return 0;
        }
        else if (accountsFound == 0 && subredditsFound != 0) {
            return 2;
        }
        else if (accountsFound != 0 && subredditsFound == 0) {
            return 3;
        }
        else {
            return 1;
        }
    }

    public ArrayList<Post> foundPostByTitle(String title) {
        ArrayList<Post> foundPosts = new ArrayList<>();
        for (Subreddit subreddit : this.subreddits) {
            for (Post post : subreddit.getPosts()) {
                if (post.getTitle().equals(title)) {
                    foundPosts.add(post);
                }
            }
        }
        return foundPosts;
    }
}
