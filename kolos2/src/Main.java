import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
// Dodaj produkty z plików CSV
        Product.addProducts(NonFoodProduct::fromCsv, Path.of("data/nonfood"));
        Product.addProducts(FoodProduct::fromCsv, Path.of("data/food"));

        // Przypadek 1: Brak produktów pasujących do prefiksu
        try {
            Product.getProducts("Abc");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException: " + e.getMessage());
        }

        // Przypadek 2: Dokładnie jeden produkt pasujący do prefiksu
        try {
            Product product = Product.getProducts("Bu");
            System.out.println("Znaleziony produkt: " + product.getName());
        } catch (AmbigiousProductException e) {
            System.out.println("AmbiguousProductException: " + e.getMessage());
        }

        // Przypadek 3: Więcej niż jeden produkt pasujący do prefiksu
        try {
            Product.getProducts("Ja");
        } catch (AmbigiousProductException e) {
            System.out.println("AmbiguousProductException: " + e.getMessage());
        }
    }
}
//
//        // Testowanie dwuargumentowej metody getPrice
//        testTwoArgumentGetPrice();
//
//        // Testowanie trójargumentowej metody getPrice
//       testThreeArgumentGetPrice();
//    }











//    public static void testTwoArgumentGetPrice() {
//        // Średnia cena dla miesiąca marzec 2010
//        double averagePrice = FoodProduct.fromCsv(Paths.get("C:\\Users\\Vilembraw\\IdeaProjects\\kolos2\\src\\data\\food\\jablka.csv")).getPrice(2010, 1);
//        System.out.println("Średnia cena dla miesiąca marzec 2010: " + averagePrice);
//    }
//
//    public static void testThreeArgumentGetPrice() {
//        // Cena jajek w województwie Mazowieckim w marcu 2010
//        double priceInMazowieckie = FoodProduct.fromCsv(Paths.get("C:\\Users\\Vilembraw\\IdeaProjects\\kolos2\\src\\data\\food\\jablka.csv")).getPrice(2010, 1, "DOLNOŚLĄSKIE");
//        System.out.println("Cena jajek w województwie Mazowieckim w marcu 2010: " + priceInMazowieckie);
//    }

