import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.IntFunction;

public class FoodProduct extends Product{

    Double[][] prices;
    String[] provinces;

    public FoodProduct(String name, Double[][] prices, String[] provinces) {
        super(name);
        this.prices = prices;
        this.provinces = provinces;
    }


    public static FoodProduct fromCsv(Path path) {
        String name;
        Double[][] prices = new Double[16][200];
        String[] provinces = new String[16];

        try {
            //Scanner scanner = new Scanner(path);
            //name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            //scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli
            FileReader file = new FileReader(String.valueOf(path));
            BufferedReader read = new BufferedReader(file);
            name = read.readLine();
            read.readLine();
            for(int i=0; i<16; ++i) {
                String[] splitter = read.readLine().split(";");
                int inter = splitter.length;
                provinces[i] = splitter[0];
                for(int j=0; j<inter; ++j){
                    if(j != 0) {
                        String part = splitter[j].replaceAll(",", ".");
                        prices[i][j] = Double.parseDouble(part);
                    }
                    }
                //prices[i] =Arrays.stream(scanner.nextLine().split(";")) // odczytuję kolejną linię i dzielę ją na tablicę
                       // .map(value -> value.replace(",", ".")) // zamieniam polski znak ułamka dziesiętnego - przecinek na kropkę
                      //  .map(Double::valueOf) // konwertuję string na double
                      //  .toArray(Double[]::new); // dodaję je do nowo utworzonej tablicy

                //scanner.close();
            }

            return new FoodProduct(name, prices, provinces);

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
                double result = 0;
                for (int j = 0; j < 16; ++j) {
                    result += prices[j][i];
                }
                result /= 16;
                return result;
            } else {
                throw new IndexOutOfBoundsException();
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

        public double getPrice(int year, int month, String province){
            if (month <= 12 && month >= 1) {
                LocalDate actual_date = LocalDate.of(year, month, 1);
                LocalDate lower = LocalDate.of(2010, 1, 1);
                LocalDate higher = LocalDate.of(2022, 3, 1);
                if (actual_date.isAfter(lower) && actual_date.isBefore(higher)) {
                    boolean flag = false;
                    int province_pos = -1;
                    province = province.toUpperCase();
                    for(int i=0; i<16; ++i){
                        ++province_pos;
                        if(province.equals(provinces[i])){
                            flag = true;
                            break;
                        }
                    }
                    if(flag) {
                        int i = (year - 2010) * 12 + month - 1; //-1 bo przykładowo dla 2010.02 chcemy przeiterować 1 raz w przód a bez tego przeiterujemy 2 razy
                        return prices[province_pos][i];
                    } else{
                        throw new IndexOutOfBoundsException();
                    }
                } else {
                    throw new IndexOutOfBoundsException();
                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        }


}

