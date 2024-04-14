public class PostVote extends Vote{
    private Post post;

    public PostVote(String karmaVote, Account setter, Account getter, Post post) {
        super(karmaVote, setter, getter);
        this.post = post;
    }

    public Post getPost() {
        return this.post;
    }
}
