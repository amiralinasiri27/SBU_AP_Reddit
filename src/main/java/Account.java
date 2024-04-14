import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
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
    private ArrayList<ChatBox> chatBoxes;
    private ArrayList<Account> followers;
    private ArrayList<Account> followings;
    private ArrayList<Notification> notifications;

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
        this.chatBoxes = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.notifications = new ArrayList<>();
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

    public ArrayList<ChatBox> getChatBoxes() {
        return this.chatBoxes;
    }

    public ArrayList<Account> getFollowers() {
        return this.followers;
    }

    public ArrayList<Account> getFollowings() {
        return this.followings;
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

    public void addChatBox(ChatBox chatBox) {
        this.chatBoxes.add(chatBox);
    }

    public void addPost(Post post) {
        if (isPostNew(post)) { this.posts.add(post); }
    }

    private void addFollower(Account account) {
        this.followers.add(account);
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

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
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

    private void showAllFollowers() {
        if (!this.followers.isEmpty()) {
            System.out.println("Followers : " + this.followers.size());
            System.out.println("~~~~~~ Followers ~~~~~~");
            for (Account account : this.followers) {
                System.out.println(account.getUsername());
            }
        }
        else {
            System.out.println("@@ " + this.username + " Have No Followers @@");
        }
    }

    private void showAllFollowings() {
        if (!this.followings.isEmpty()) {
            System.out.println("Followings : " + this.followings.size());
            System.out.println("~~~~~~ Followings ~~~~~~");
            for (Account account : this.followings) {
                System.out.println(account.getUsername());
            }
        }
        else {
            System.out.println("@@ " + this.username + " Do Not Follow Any Body @@");
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
        this.showAllFollowers();
        this.showAllFollowings();
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
        this.displayNotifications();
        System.out.println("Options ---------->> ");
        System.out.println("1) View All Subreddits For Follow a Subreddit"); //
        System.out.println("2) View All Subreddits that you are admin of them for manage them"); //
        System.out.println("3) View All Subreddits To Leaving Comment"); //
        System.out.println("4) View All Subreddits that you Follow them (Your TimeLine)"); //
        System.out.println("5) View All Posts You Have Saved"); //
        System.out.println("6) Create a New Subreddit"); //
        System.out.println("7) Add an Admin to one of your Subreddits"); //
        System.out.println("8) Create a Post"); //
        System.out.println("9) Search"); //
        System.out.println("10) Change Your Information"); //
        System.out.println("11) View Your Profile"); //
        System.out.println("12) View All Votes You Set For Other Users And Change them"); //
        System.out.println("13) View All Your Chat Boxes"); //
        System.out.println("14) Follow a User"); //
        System.out.println("15) UnSave a Post");
        System.out.println("16) Log out"); //
        System.out.println("17) Exit Program"); //
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

    public void showAllChatBoxes(DataBase dataBase, Scanner scanner) {
        if (!this.chatBoxes.isEmpty()) {
            for (ChatBox chatBox : this.chatBoxes) {
                chatBox.showParticipants();
            }
            System.out.print("Do You Want To Chat in one of Your Chat Boxes of Create a new one? 1) new one - 2) This ChatBoxes - 0) Back To Menu %$#--->: ");
            String input = scanner.nextLine();
            if (input.equals("1")){
                this.createNewChatBox(dataBase, scanner);
            }
            else if (input.equals("2")) {
                System.out.print("Enter The Name of Second Participant %$#--->: ");
                String name = scanner.nextLine();
                boolean isFound = false;
                for (ChatBox chatBox : this.chatBoxes) {
                    if (chatBox.getSecondParticipant(this).getUsername().equals(name)) {
                        isFound = true;
                        chatBox.showBody();
                        chatBox.writeMessage(scanner, this, dataBase);
                    }
                }
                if (!isFound) {
                    System.out.println("User Not Found");
                }
            }
            else if (input.equals("0")) {
                // Do Noting For Back To Menu
            }
            else {
                System.out.println("Invalid Order");
            }
        }
        else {
            System.out.println("*** You Do Not Have Chat With Any User ***");
            System.out.print("Do You Want To Start Chat With Somebody? 1) Yes - 2) Back To Menu %$#--->: ");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                this.createNewChatBox(dataBase, scanner);
            }
            else if (input.equals("2")){
                // do noting to back to menu
            }
            else {
                System.out.println("Invalid order");
            }
        }
    }

    public void followUsers(DataBase dataBase, Scanner scanner) {
        for (Account account : dataBase.getAccounts()) {
            if (!account.getUsername().equals(this.username)) {
                System.out.println(account.getUsername());
            }
        }
        System.out.print("Enter a UserName To Follow %$#--->: ");
        String inputName = scanner.nextLine();
        boolean isUserFound = false;
        boolean isItThis = false;

        for (Account account1 : dataBase.getAccounts()) {
            if (account1.getUsername().equals(inputName)) {
                isUserFound = true;
                if (!account1.getUsername().equals(this.username)) {
                    isItThis = false;
                    if (this.isFollowingUserNew(account1)) {
                        this.followings.add(account1);
                        account1.addFollower(this);
                        Notification notification = new Notification("follower");
                        account1.addNotification(notification);
                        System.out.println("You Follow " + account1.getUsername() + " From Now");
                    }
                    else {
                        System.out.println("You Follow " + account1.getUsername() + " Before");
                    }
                }
                else {
                    isItThis = true;
                }
            }
        }
        if (isItThis) {
            System.out.println("You Cant Follow Your Self :) ");
        }
        if (!isUserFound) {
            System.out.println("User Do Not Exist");
        }
    }

    private boolean isFollowingUserNew(Account account) {
        for (Account account1 : this.followings) {
            if (account1.getUsername().equals(account.getUsername())) {
                return false;
            }
        }
        return true;
    }

    private void createNewChatBox(DataBase dataBase, Scanner scanner) {
        for (Account account : dataBase.getAccounts()) {
            if (!account.getUsername().equals(this.username)) {
                System.out.println(account.getUsername());
            }
        }
        System.out.print("Enter a UserName To Start Chat With %$#--->: ");
        String inputName = scanner.nextLine();
        boolean isUserFound = false;
        boolean isItThis = false;

        for (Account account1 : dataBase.getAccounts()) {
            if (account1.getUsername().equals(inputName)) {
                isUserFound = true;
                if (!account1.getUsername().equals(this.username)) {
                    isItThis = false;
                    if (!this.haveChatBoxWith(account1)) {
                        ChatBox chatBox = new ChatBox(this, account1);
                        this.addChatBox(chatBox);
                        account1.addChatBox(chatBox);
                        System.out.println("Chat Box Created");
                    }
                    else {
                        System.out.println("You Have a ChatBox With " + account1.getUsername() + " From Past");
                    }
                }
                else {
                    isItThis = true;
                }
            }
        }
        if (isItThis) {
            System.out.println("You Cant Chat With Your Self :) ");
        }
        if (!isUserFound) {
            System.out.println("User Do Not Exist");
        }
    }

    private boolean haveChatBoxWith(Account account) {
        if (!this.chatBoxes.isEmpty()) {
            for (ChatBox chatBox : this.chatBoxes) {
                if (chatBox.getSecondParticipant(this).getUsername().equals(account.getUsername())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void showAllFollowingsPosts() {
        int counter = 0;
        if (!this.followings.isEmpty()) {
            System.out.println("### Posts of Users That You Follow Them ###");
            for (Account account : this.followings) {
                for (Post post : account.getPosts()) {
                    counter++;
                    System.out.println("Title: " + post.getTitle());
                    System.out.println("Author: " + post.getAuthor().getUsername());
                    System.out.println("Post_Karma = " + post.getKarma().getKarmaPoint());
                    System.out.println(post.getContent());
                    post.showAllTags();
                    post.showAllComments();
                    System.out.println("----------------------------------------------------");
                }
            }
            if (counter == 0) {
                System.out.println("*** No Posts Yet ***");
            }
        }
    }

    public void displayNotifications() {
        if (!this.notifications.isEmpty()) {
            for (Notification notification : this.notifications) {
                if (!notification.getSeen()) {
                    System.out.println(notification);
                    notification.userSeenNotification();
                }
            }
        }
    }

    public void unSavePost(String title) {
        boolean isPostFound = false;
        for (Post post : this.savedPosts) {
            if (post.getTitle().equals(title)) {
                isPostFound = true;
                this.savedPosts.remove(post);
                System.out.println("Successfully UnSaved");
                break;
            }
        }
        if (!isPostFound) {
            System.out.println("Post Not Found!!!");
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
