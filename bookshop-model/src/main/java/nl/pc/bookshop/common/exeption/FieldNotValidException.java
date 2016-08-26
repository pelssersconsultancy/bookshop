package nl.pc.bookshop.common.exeption;


public class FieldNotValidException extends RuntimeException {

    private static final long serialVersionUID = 1242250931936633304L;

    private final String fieldName;

    public FieldNotValidException( String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return "FieldNotValidException{" +
                "fieldName='" + fieldName + '\'' +
                '}';
    }
}
