import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class NonFoodProduct extends Product {
    Double[] prices;

    private NonFoodProduct(String name, Double[] prices) {
        super(name);
        this.prices = prices;
    }

    public static NonFoodProduct fromCsv(Path path) {
        String name;
        Double[] prices;

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli
            prices = Arrays.stream(scanner.nextLine().split(";")) // odczytuję kolejną linię i dzielę ją na tablicę
                    .map(value -> value.replace(",", ".")) // zamieniam polski znak ułamka dziesiętnego - przecinek na kropkę
                    .map(Double::valueOf) // konwertuję string na double
                    .toArray(Double[]::new); // dodaję je do nowo utworzonej tablicy

            scanner.close();

            return new NonFoodProduct(name, prices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getPrice(int year, int month) {
        if (month <= 12 && month >= 1) {
            LocalDate actual_date = LocalDate.of(year, month, 1);
            LocalDate lower = LocalDate.of(2010, 1, 1);
            LocalDate higher = LocalDate.of(2022, 3, 1);
            if (actual_date.isAfter(lower) && actual_date.isBefore(higher)) {
                int i = (year - 2010) * 12 + month - 1; //-1 bo przykładowo dla 2010.02 chcemy przeiterować 1 raz w przód a bez tego przeiterujemy 2 razy
                return prices[i];
            } else {
                throw new IndexOutOfBoundsException();
            }
        } else {
            throw new IndexOutOfBoundsException();
        }

    }
}
