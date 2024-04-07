import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

public class Account implements AccountManagement {
    private int totalKarma;
    private Karma postsKarma;
    private Karma commentsKarma;
    private String username;
    private String password;
    protected UUID accountID;
    private String email;
    private boolean displayEmailInProfile;
    private ArrayList<Subreddit> subreddits;
    private ArrayList<Subreddit> followSubreddits;
    private ArrayList<Post> posts;
    private ArrayList<Post> savedPosts;
    private ArrayList<PostVote> postVotes;
    private ArrayList<CommentVote> commentVotes;

    public Account(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.displayEmailInProfile = false;
        this.accountID = UUID.randomUUID();
        this.password = hashPassword(password);
        this.subreddits = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.postVotes = new ArrayList<>();
        this.commentVotes = new ArrayList<>();
        this.followSubreddits = new ArrayList<>();
        this.savedPosts = new ArrayList<>();
        this.totalKarma = 0;
        this.postsKarma = new Karma();
        this.commentsKarma = new Karma();
    }

    // getter methods
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public UUID getID() {
        return this.accountID;
    }

    public String getStringUUID() {
        return  this.accountID.toString();
    }

    public ArrayList<Subreddit> getFollowSubreddits() {
        return this.followSubreddits;
    }

    public ArrayList<Subreddit> getSubreddits() {
        return this.subreddits;
    }

    public ArrayList<Post> getPosts() {
        return this.posts;
    }

    public ArrayList<Post> getSavedPosts() {
        return this.savedPosts;
    }

    public Karma getPostsKarma() {
        return this.postsKarma;
    }

    public Karma getCommentsKarma() {
        return this.commentsKarma;
    }

    public ArrayList<PostVote> getPostVotes() {
        return this.postVotes;
    }

    public ArrayList<CommentVote> getCommentVotes() {
        return this.commentVotes;
    }

    public int calculateTotalKarma() {
        this.totalKarma = 0;
        this.totalKarma += this.commentsKarma.getKarmaPoint();
        this.totalKarma += this.postsKarma.getKarmaPoint();
        return this.totalKarma;
    }

    public void setEmailDisplayOnProfile(boolean canDisplay) {
        this.displayEmailInProfile = canDisplay;
    }

    // other methods
    public void addSubreddit(Subreddit subreddit) {
        if (isSubredditNew(subreddit)) {
            this.subreddits.add(subreddit);
        }
    }

    private boolean isSubredditNew(Subreddit subreddit) {
        for (Subreddit subreddit1 : this.subreddits) {
            if (subreddit1.getSubredditID() == subreddit.getSubredditID()) {
                return false;
            }
        }
        return true;
    }

    public void addSavedPost(Post post) {
        if (isSavedPostNew(post)) { this.savedPosts.add(post); }
    }

    public void addPost(Post post) {
        if (isPostNew(post)) { this.posts.add(post); }
    }

    private boolean isSavedPostNew(Post post) {
        for (Post post1 : this.savedPosts) {
            if (post1.getPostID() == post.getPostID()) {
                return false;
            }
        }
        return true;
    }
    private boolean isPostNew(Post post) {
        for (Post post1 : this.posts) {
            if (post1.getPostID() == post.getPostID()) {
                return false;
            }
        }
        return true;
    }

    public void addFollowSubreddit(Subreddit subreddit) {
        if (isFollowSubredditNew(subreddit)) { this.followSubreddits.add(subreddit); }
    }

    private boolean isFollowSubredditNew(Subreddit subreddit) {
        for (Subreddit subreddit1 : this.followSubreddits) {
            if (subreddit1.getSubredditID() == subreddit.getSubredditID()) {
                return false;
            }
        }
        return true;
    }

    public void addPostVote(PostVote postVote) {
        this.postVotes.add(postVote);
    }

    public void addCommentVote(CommentVote commentVote) {
        this.commentVotes.add(commentVote);
    }

    @Override
    public void changePassword(String newPassword) {
        this.password = hashPassword(newPassword);
    }

    @Override
    public void changeUsername(String newUsername) {
        this.username = newUsername;
    }

    @Override
    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    @Override
    public boolean validatePassword(String enteredPassword) {
        return this.password.equals(hashPassword(enteredPassword));
    }

    public void showUserSubreddits() {
        System.out.println("Subreddits That " + this.username + " is admin of them: ");
        if (!this.subreddits.isEmpty()) {
            for (Subreddit subreddit : this.subreddits) {
                subreddit.showAllPosts();
            }
        }
        else {
            System.out.println("*** No Subreddits Yet ***");
        }
    }

    public void showUserFollowSubreddits() {
        System.out.println("Subreddits that You Follow Them: ");
        if (!this.followSubreddits.isEmpty()) {
            for (Subreddit subreddit : this.followSubreddits) {
                subreddit.showAllPosts();
            }
        }
        else {
            System.out.println("*** You Do Not Follow Any Subreddits ***");
        }
    }

    public void showUserSubredditsTopics() {
        if (!this.subreddits.isEmpty()) {
            for (Subreddit subreddit : this.subreddits) {
                System.out.println(subreddit.getTopic());
            }
        }
        else {
            System.out.println("*** No Subreddits Yet ***");
        }
    }

    private void showUserFollowSubredditsTopic() {
        if (!this.followSubreddits.isEmpty()) {
            System.out.println("%% Subreddits that " + this.username + " Follow them %%");
            for (Subreddit subreddit : this.followSubreddits) {
                System.out.println(subreddit.getTopic());
            }
        }
        else {
            System.out.println(this.username + " do not Follow any Subreddits");
        }
    }

    private void showAllPostUserHaveMade() {
        if (!this.posts.isEmpty()) {
            System.out.println("@@ Posts that " + this.username + " Create them @@");
            for (Post post : this.posts) {
                System.out.println("Title: " + post.getTitle());
                System.out.println("Subreddit: " + post.getSubreddit().getTopic());
                System.out.println("Post_Karma = " + post.getKarma().getKarmaPoint());
                System.out.println(post.getContent());
                post.showAllTags();
                post.showAllComments();
                System.out.println("------------------------------------");
            }
        }
        else {
            System.out.println(this.username + " do not Create any Posts");
        }
    }

    public void showUserProfile() {
        System.out.println("^^^^^^^^^^^ Profile ^^^^^^^^^^^");
        System.out.println("Username: " + this.username);
        System.out.println("Total_Karma = " + this.calculateTotalKarma());
        this.calculateTotalKarma();
        System.out.println("Posts_Karma = " + this.postsKarma.getKarmaPoint());
        System.out.println("Comments_Karma = " + this.commentsKarma.getKarmaPoint());
        this.showUserFollowSubredditsTopic();
        if (this.displayEmailInProfile) {
            System.out.println("Email: " + this.email);
        }
        this.showAllPostUserHaveMade();
    }

    public void showAllSavedPosts() {
        if (!this.savedPosts.isEmpty()) {
            System.out.println("@@ Posts You Have Saved @@");
            for (Post post : this.savedPosts) {
                System.out.println("Title: " + post.getTitle());
                System.out.println("Subreddit: " + post.getSubreddit().getTopic());
                System.out.println("Author: " + post.getAuthor().getUsername());
                System.out.println("Post_Karma = " + post.getKarma().getKarmaPoint());
                System.out.println(post.getContent());
                post.showAllTags();
                post.showAllComments();
                System.out.println("------------------------------------");
            }
        }
        else {
            System.out.println("*** You do not save any posts ***");
        }
    }

    public void displayChangeInformationMenu() {
        System.out.println("$$$ What do You Want to Change $$$");
        System.out.print("1) username - 2) password - 3) email - 4) Show Your Email in Your Profile %$#--->: ");
    }

    public void displayMenuAfterLogIn() {
        System.out.println("********** Hello again " + this.username + " **********");
        System.out.println("Options ---------->> ");
        System.out.println("1) View All Subreddits For Follow a Subreddit"); //
        System.out.println("2) View All Subreddits that you are admin of them for manage them"); //
        System.out.println("3) View All Subreddits To Leaving Comment"); //
        System.out.println("4) View All Subreddits that you Follow them"); //
        System.out.println("5) View All Posts You Have Saved");
        System.out.println("6) Create a New Subreddit"); //
        System.out.println("7) Add an Admin to one of your Subreddits"); //
        System.out.println("8) Create a Post"); //
        System.out.println("9) Search"); //
        System.out.println("10) Change Your Information");
        System.out.println("11) View Your Profile"); //
        System.out.println("12) View All Votes You Set For Other Users And Change them");
        System.out.println("13) Log out"); //
        System.out.println("14) Exit Program"); //
        System.out.print("Your Choice %$#--->: ");
    }

    public void showAllVotes() {
        if (!this.postVotes.isEmpty()) {
            for (PostVote postVote : this.postVotes) {
                System.out.println("Title: " + postVote.getPost().getTitle());
                System.out.println("Author: " + postVote.getGetter().getUsername());
                System.out.println("Post_Karma = " + postVote.getPost().getKarma().getKarmaPoint());
                System.out.println(postVote.getPost().getContent());
                postVote.getPost().showAllTags();
                postVote.getPost().showAllComments();
                System.out.println("Your Vote : " + postVote.getKarmaVote());
                System.out.println("-----------------------------");
            }
        }
        else if (!this.commentVotes.isEmpty()) {
            for (CommentVote commentVote : this.commentVotes) {
                if (commentVote.getStatus() != 1) {
                    System.out.println("Author : " + commentVote.getComment().getAuthor().getUsername());
                    System.out.println(commentVote.getComment().getText());
                    System.out.println("Comment_Karma = " + commentVote.getComment().getKarma().getKarmaPoint());
                    System.out.println("Your Vote : " + commentVote.getKarmaVote());
                    System.out.println("-----------------------------");
                }
            }
        }
        else {
            System.out.println("*** You Do Not Vote For No Body ***");
        }
    }

    private String hashPassword(String password) {  // create hash of a password
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = messageDigest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (byte i : hashedPassword) {
                stringBuilder.append(String.format("%02x", i));
            }

            return stringBuilder.toString();
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
