package matcha.exception.db;


import matcha.exception.BaseException;

public class InsertDBException extends BaseException {
    public InsertDBException() {
        super("Ошибка добавления в базу");
    }
}
