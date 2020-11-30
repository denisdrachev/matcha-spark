package matcha.exception.db;


import matcha.exception.BaseException;

public class DeleteDBException extends BaseException {
    public DeleteDBException() {
        super("Ошибка удаления из базы");
    }
}
