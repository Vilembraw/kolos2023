import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\User\\Desktop\\lekcje online\\Programowanie Obiektowe\\Kolos_2022\\src\\buraki.csv");
        FoodProduct buraki = FoodProduct.fromCsv(path);
        Double full_price = buraki.getPrice(2012, 4);
        Double opolskie_price = buraki.getPrice(2013, 5, "Opolskie");
        System.out.println(buraki.getName() + "\nPełna średnia cena: " + full_price + "\nPełna cena z Opolskiego: " + opolskie_price);

        path = Paths.get("data/nonfood");
        Product.addProducts(NonFoodProduct::fromCsv, path);
        path = Paths.get("data/food");
        Product.addProducts(FoodProduct::fromCsv, path);
    }

    }