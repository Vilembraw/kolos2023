import java.util.List;

public class AmbigiousProductException extends RuntimeException {
    private List<String> products;

    public AmbigiousProductException(List<String> products) {
        this.products = products;
    }

    @Override
    public String getMessage() {
        String message = "Ambiguous product exception. Products:";
        for (String product : products) {
            message += "\n- " + product;
        }
        return message;
    }
}