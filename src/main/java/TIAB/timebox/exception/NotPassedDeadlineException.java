package TIAB.timebox.exception;

public class NotPassedDeadlineException extends RuntimeException{
    public NotPassedDeadlineException() {
        super("시간이 지나지 않았습니다.");
    }
}
