import java.util.ArrayList;
import java.util.UUID;

public class Subreddit {
    private String topic;
    private UUID subredditID;
    private ArrayList<Account> participants;
    private ArrayList<Post> posts;
    private ArrayList<Admin> admins;

    public Subreddit(String topic, Admin admin) {
        this.topic = topic;
        this.subredditID = UUID.randomUUID();
        this.participants = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.admins.add(admin);
    }

    public ArrayList<Account> getParticipants() {
        return this.participants;
    }

    public UUID getSubredditID() {
        return this.subredditID;
    }

    public String getSubredditStringUUID() {
        return this.subredditID.toString();
    }

    public String getTopic() {
        return this.topic;
    }

    public ArrayList<Post> getPosts() {
        return this.posts;
    }

    public ArrayList<Admin> getAdmins() {
        return this.admins;
    }

    public void addParticipant(Account account) {
        if (isParticipantNew(account)) { this.participants.add(account); }
    }

    private boolean isParticipantNew(Account account) {
        for (Account account1 : this.participants) {
            if (account1.getID() == account.getID()) {
                return false;
            }
        }
        return true;
    }

    public void addPost(Post post) {
        if (isPostNew(post)) { this.posts.add(post); }
    }

    private boolean isPostNew(Post post) {
        for (Post post1 : this.posts) {
            if (post1.getPostID() == post.getPostID()) {
                return false;
            }
        }
        return true;
    }

    public void addAdmin(Admin admin) {
        if (isAdminNew(admin)) { this.admins.add(admin); }
    }

    private boolean isAdminNew(Admin admin) {
        for (Admin admin1 : this.admins) {
            if (admin1.getID() == admin.getID()) {
                return false;
            }
        }
        return true;
    }

    public void showAllPosts() {
        System.out.println("Topic: " + this.topic);
        System.out.println("Number Of Members: " + this.participants.size());
        if (!this.posts.isEmpty()) {
            System.out.println("----------posts----------");
            for (Post post : this.posts) {
                System.out.println("Title: " + post.getTitle());
                System.out.println("Author: " + post.getAuthor().getUsername());
                System.out.println("Post_Karma = " + post.getKarma().getKarmaPoint());
                System.out.println(post.getContent());
                post.showAllTags();
                post.showAllComments();
                System.out.println("----------------------------------------------------");
            }
        }
        else {
            System.out.println("*** No Posts Yet ***");
        }
    }

    public void showAllPostsWithIDForAdmin() {
        System.out.println("Topic: " + this.topic);
        System.out.println("SubredditID: " + this.getSubredditID());
        System.out.println("Number Of Members: " + this.participants.size());
        if (!this.posts.isEmpty()) {
            System.out.println("----------posts----------");
            for (Post post : this.posts) {
                System.out.println("Title: " + post.getTitle());
                System.out.println("PostID: " + post.getPostID());
                System.out.println("Author: " + post.getAuthor().getUsername());
                System.out.println("Post_Karma = " + post.getKarma().getKarmaPoint());
                System.out.println(post.getContent());
                post.showAllTags();
                post.showAllCommentsWithIDForAdmin();
                System.out.println("----------------------------------------------------");
            }
        }
        else {
            System.out.println("*** No Posts Yet ***");
        }
        System.out.println("~~~Users~~~");
        for (Account account : this.participants) {
            System.out.println("Username: " + account.getUsername());
            System.out.println("AccountID: " + account.getID());
        }
    }

}
