public class Message {

    String message = "empty_for_now";
    String who = "nobody_for_now";

    public Message(String m, String w) {
        this.message = m;
        this.who = w;
    }

    public String getMessage() {
        return this.message;
    }

    public String getWho() {
        return this.who;
    }

}
