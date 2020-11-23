package matcha.exception.db;


public class InsertDBException extends RuntimeException {
    public InsertDBException() {
        super("Ошибка добавления в базу");
    }
}
