public class ListItemNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ListItemNotFoundException() {}

    public ListItemNotFoundException(String message) {
        super(message);
    }

}
