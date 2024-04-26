import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Product {
    private String name;
    private static List<Product> list = new ArrayList<>();

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double getPrice(int year, int month);

    public static void clearProducts(){
        list.clear();
    }

    public static void addProducts(Function<Path, Product> fromCsvFunction, Path path){
            Product product = fromCsvFunction.apply(path);
            list.add(product);
    }
}
