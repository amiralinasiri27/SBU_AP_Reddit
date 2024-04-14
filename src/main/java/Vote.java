public abstract class Vote {
    private String karmaVote;
    private Account setter;
    private Account getter;

    public Vote(String karmaVote, Account setter, Account getter) {
        this.karmaVote = karmaVote;
        this.setter = setter;
        this.getter = getter;
    }

    public String getKarmaVote() {
        return this.karmaVote;
    }

    public Account getSetter() {
        return this.setter;
    }

    public Account getGetter() {
        return this.getter;
    }

    public void setKarmaVote(String str) {
        this.karmaVote = str;
    }
}
