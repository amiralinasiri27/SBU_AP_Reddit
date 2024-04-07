import java.util.UUID;

public class Comment {
    private Karma karma;
    private String text;
    private Account author;
    private Post post;
    private UUID commentID;

    public Comment(String text, Account author, Post post) {
        this.karma = new Karma();
        this.text = text;
        this.author = author;
        this.post = post;
        this.commentID = UUID.randomUUID();
    }

    public void changeText(String newText) {
        this.text = newText;
    }

    public String getText() {
        return this.text;
    }

    public Account getAuthor() {
        return this.author;
    }

    public Post getPost() {
        return this.post;
    }

    public Karma getKarma() {
        return this.karma;
    }

    public UUID getCommentID() {
        return this.commentID;
    }

    public String getCommentStringUUID() {
        return this.commentID.toString();
    }
}
