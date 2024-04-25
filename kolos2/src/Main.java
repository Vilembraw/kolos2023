import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {



       Product.addProducts(NonFoodProduct::fromCsv, Paths.get("C:\\Users\\Vilembraw\\IdeaProjects\\kolos2\\src\\data\\nonfood"));
        Product.addProducts(FoodProduct::fromCsv, Paths.get("C:\\Users\\Vilembraw\\IdeaProjects\\kolos2\\src\\data\\food"));
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
}}
