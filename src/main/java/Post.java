import java.util.ArrayList;
import java.util.UUID;

public class Post {
    private Karma karma;
    private Account author;
    private String title;
    private String content;
    private Subreddit subreddit;
    private UUID postID;
    private ArrayList<Comment> comments;
    private ArrayList<String> tags;

    public Post(Account author, String title, String content) {
        this.karma = new Karma();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postID = UUID.randomUUID();
        this.comments = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    // getter methods
    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Account getAuthor() {
        return this.author;
    }

    public Subreddit getSubreddit() {
        return this.subreddit;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public Karma getKarma() {
        return this.karma;
    }

    public UUID getPostID() {
        return this.postID;
    }

    public String getPostStringUUID() {
        return this.postID.toString();
    }

    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    // setter methods
    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void addComment(Comment comment) {
        if (isCommentNew(comment)) { this.comments.add(comment); }
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    private boolean isCommentNew(Comment comment) {
        for (Comment comment1 : this.comments) {
            if (comment1.getCommentID() == comment.getCommentID()) {
                return false;
            }
        }
        return true;
    }

    public void showAllComments() {
        if (!this.comments.isEmpty()) {
            System.out.println("Comments: ");
            for (Comment comment : this.comments) {
                System.out.println(comment.getAuthor().getUsername() + " say: " + comment.getText());
                System.out.println("Comment_Karma = " + comment.getKarma().getKarmaPoint());
            }
        }
        else {
            System.out.println("*** No Comments Yet ***");
        }
    }

    public void showAllCommentsWithIDForAdmin() {
        if (!this.comments.isEmpty()) {
            System.out.println("Comments: ");
            for (Comment comment : this.comments) {
                System.out.println("CommentID: " + comment.getCommentID());
                System.out.println("Comment_Karma = " + comment.getKarma());
                System.out.println(comment.getAuthor().getUsername() + " say: " + comment.getText());
            }
        }
        else {
            System.out.println("*** No Comments Yet ***");
        }
    }

    public void showAllTags() {
        if (!this.tags.isEmpty()) {
            for (String tag : this.tags) {
                System.out.print("#" + tag + "  ");
            }
            System.out.println();
        }
        else {
            System.out.println("### No Tags ###");
        }
    }
}
