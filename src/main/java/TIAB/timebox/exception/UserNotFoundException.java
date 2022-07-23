package TIAB.timebox.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("사용자 정보가 없습니다.");
    }
}
