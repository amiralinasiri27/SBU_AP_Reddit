public class CommentVote extends Vote{
    private Comment comment;
    private int status;

    public CommentVote(String karmaVote, Account setter, Account getter, Comment comment) {
        super(karmaVote, setter, getter);
        this.comment = comment;
        this.status = 0;
    }

    public Comment getComment() {
        return this.comment;
    }

    public void setStatus() {
        this.status = 1;
    }

    public int getStatus() {
        return this.status;
    }

    public void retractCommentVote(String vote) {
        if (vote.equals("+")) {
            this.comment.getKarma().deductKarma();
            this.getGetter().getCommentsKarma().deductKarma();
            System.out.println("Retract Was Successfully Applied You Can Vote To This Post Later");
        }
        else if (vote.equals("-")) {
            this.comment.getKarma().addKarma();
            this.getGetter().getCommentsKarma().addKarma();
            System.out.println("Retract Was Successfully Applied You Can Vote To This Post Later");
        }
    }

    public void backRetract() {
        this.getComment().getKarma().getKarmaSetters().remove(this.getSetter());
        this.getSetter().getCommentVotes().remove(this);
    }
}
