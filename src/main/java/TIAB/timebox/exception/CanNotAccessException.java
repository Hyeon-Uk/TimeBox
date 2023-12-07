package TIAB.timebox.exception;

public class CanNotAccessException extends RuntimeException {
    public CanNotAccessException() {
        super("메세지에 접근할 수 없습니다.");
    }
}
