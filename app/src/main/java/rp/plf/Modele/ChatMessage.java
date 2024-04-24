package rp.plf.Modele;

public class ChatMessage {
    private String sender,receiver, message;
    private Boolean isseen;



    public ChatMessage(String sender, String receiver, String message, Boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;

    }

    public ChatMessage(){

    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsseen() {
        return isseen;
    }

    public void setIsseen(Boolean isseen) {
        this.isseen = isseen;
    }
}
