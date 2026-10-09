package cat.paucasesnoves.unitat2.exception;

/** Absència del recurs; el controlador d'errors la tradueix a HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
