package ir.bank.qh.common.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entity, Object id) {
        super("%s با شناسه %s یافت نشد / %s with id %s was not found".formatted(entity, id, entity, id));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
