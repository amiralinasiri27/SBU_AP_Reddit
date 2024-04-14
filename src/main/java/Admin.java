import java.util.ArrayList;

public class Admin extends Account{
    public Admin(String username, String password, String email) {
        super(username, password, email);
    }

    public void addSubredditsFromAccountToAdmin(Account account) {
        for (Subreddit subreddit : account.getSubreddits()) {
            this.addSubreddit(subreddit);
        }
    }

    public void displayDeletingMenu() {
        System.out.println("~~~Which One Do You Want To Delete~~~");
        System.out.print("1) Subreddit - 2) Post - 3) Comment - 4) Participant (0 For Back To Menu) %$#--->: ");
    }

    @Override
    public void showUserSubreddits() {
        System.out.println("Subreddits That " + this.getUsername() + " is admin of them: ");
        if (!this.getSubreddits().isEmpty()) {
            for (Subreddit subreddit : this.getSubreddits()) {
                subreddit.showAllPostsWithIDForAdmin();
            }
        }
        else {
            System.out.println("*** No Subreddits Yet ***");
        }
    }
}
