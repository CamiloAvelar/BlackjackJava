// Classe para exibição de mensagens no layout.
public class Message {

    String message;
    String who;

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
