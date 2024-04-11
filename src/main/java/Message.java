public class Message {
    private Account author;
    private String text;

    public Message(Account author, String text) {
        this.text = text;
        this.author = author;
    }

    @Override
    public String toString() {
        return this.author.getUsername() + " : " + this.text;
    }
}
