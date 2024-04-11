public class Notification {
    private String text;
    private String type;
    private boolean seen;

    public Notification(String type) {
        this.type = type;
        this.seen = false;

        if (this.type.equals("follower")) {
            this.text = "&&&&&&&&&&&&&& You Have New Follower &&&&&&&&&&&&&&";
        }
        else if (this.type.equals("message")) {
            this.text = "&&&&&&&&&&&&&& You Have New Message &&&&&&&&&&&&&&";
        }
        else {
            this.text = "";
        }
    }

    public void userSeenNotification() {
        this.seen = true;
    }

    public boolean getSeen() {
        return this.seen;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
