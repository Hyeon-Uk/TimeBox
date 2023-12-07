package TIAB.timebox.exception;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException() {
        super("메세지가 없습니다.");
    }
}
