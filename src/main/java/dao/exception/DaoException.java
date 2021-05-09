package dao.exception;

public class DaoException extends RuntimeException{

    public DaoException(String message){
        super(message);
    }

    public DaoException(Throwable source) {
        super(source);
    }

    public DaoException(String message, Throwable source) {
        super(message,source);
    }

}