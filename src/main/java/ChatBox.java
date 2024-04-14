import java.util.ArrayList;
import java.util.Scanner;

public class ChatBox {
    private Account host;
    private Account guest;
    private ArrayList<Message> messages;

    public ChatBox(Account host, Account guest) {
        this.guest = guest;
        this.host = host;
        this.messages = new ArrayList<>();
    }

    public Account getGuest() {
        return this.guest;
    }

    public Account getHost() {
        return this.host;
    }

    public void showBody() {
        System.out.println("ChatBox of " + this.host.getUsername() + " And " + this.guest.getUsername());
        if (!this.messages.isEmpty()) {
            for (Message message : this.messages) {
                System.out.println(message);
            }
        }
        else {
            System.out.println("*** ChatBox is Empty ***");
        }
    }

    public Account getSecondParticipant(Account account) {
        if (this.guest.getUsername().equals(account.getUsername())) {
            return this.host;
        }
        else if (this.host.getUsername().equals(account.getUsername())) {
            return this.guest;
        }
        return null;
    }

    public void showParticipants() {
        System.out.println(this.host.getUsername() + " And " + this.guest.getUsername());
    }
public void writeMessage(Scanner scanner, Account account, DataBase dataBase) {
    System.out.println("!!!! if You Want To Share a Post Enter {/pPostTitle}");
    System.out.println("$$$ Enter 0 For Back To Menu $$$");
    while (true) {
        System.out.print("Enter Your Message : ");
        String inputText = scanner.nextLine();
        if (inputText.equals("0")){
            break;
        }
        else if (inputText.charAt(0) == '/' && inputText.charAt(1) == 'p') {
            ArrayList<Post> posts = dataBase.foundPostByTitle(inputText.substring(2));
            if (!posts.isEmpty()) {
                for (Post post : posts) {
                    Message message = new Message(account, toString(post));
                    this.messages.add(message);
                    Notification notification = new Notification("message");
                    Account getter = this.getSecondParticipant(account);
                    getter.addNotification(notification);
                }
            }
        }
        else {
            Message message = new Message(account, inputText);
            this.messages.add(message);
            Notification notification = new Notification("message");
            Account getter = this.getSecondParticipant(account);
            getter.addNotification(notification);
        }
    }
}
    public String toString(Post post) {
        String bodyOfPost = "\n";
        bodyOfPost += "@@@ Post @@@ \n";
        bodyOfPost += "Title: " + post.getTitle() + "\n";
        bodyOfPost += "Author: " + post.getAuthor().getUsername() + "\n";
        bodyOfPost += "Post_Karma = " + post.getKarma().getKarmaPoint() + "\n";
        bodyOfPost += post.getContent() + "\n";
        if (!post.getComments().isEmpty()) {
            bodyOfPost += "Comments: \n";
            for (Comment comment : post.getComments()) {
                bodyOfPost += comment.getAuthor().getUsername() + " say: " + comment.getText() + "\n";
                bodyOfPost += "Comment_Karma = " + comment.getKarma().getKarmaPoint() + "\n";
            }
        }
        else {
            bodyOfPost += "*** No Comments Yet *** \n";
        }
        if (!post.getTags().isEmpty()) {
            for (String tag : post.getTags()) {
                bodyOfPost += "#" + tag + "  ";
            }
            bodyOfPost += "\n";
        }
        else {
            bodyOfPost += "### No Tags ### \n";
        }
        return bodyOfPost;
    }

}
