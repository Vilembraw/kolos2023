import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

abstract class Product {
    private String name;
    private static List<Product> products = new ArrayList<>();

    public static void clearProducts(){
        products.clear();
    }



    public static Product getProducts(String prefix) {
        List<String> matchingProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.getName().startsWith(prefix)) {
                matchingProducts.add(product.getName());
            }
        }

        int count = matchingProducts.size();
        if (count == 0) {
            throw new IndexOutOfBoundsException("No products found with prefix: " + prefix);
        } else if (count == 1) {
            // Zwrócenie jednoznacznego produktu
            return products.get(0);
        } else {

            throw new AmbigiousProductException(matchingProducts);
        }
    }

    public static void addProducts(Function<Path, Product> fromCsv, Path directoryPath) {
        try {
            Files.walk(directoryPath)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        if (file.getFileName().toString().endsWith(".csv")) {
                            System.out.printf("%s\n",file.toString());
                            Product product = fromCsv.apply(file);
                            products.add(product);
                        }

                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double getPrice(int year, int month);
}