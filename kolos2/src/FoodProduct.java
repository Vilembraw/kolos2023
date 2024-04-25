import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class FoodProduct extends Product {
    private List<Provinces> province = new ArrayList<>();

    private FoodProduct(String name, List<Provinces> province) {
        super(name);
        this.province = province;
    }

    public static FoodProduct fromCsv(Path path) {
        String name;
        List<Provinces> province = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa

            String[] provincesSplit = scanner.nextLine().split(";"); //zapisuje nazwe province

            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(";");
                Provinces provincePrices = new Provinces(tokens[0]);

                for (int i = 1; i < tokens.length; i++) {
                    Double price = Double.valueOf(tokens[i].replace(",", "."));
                    provincePrices.addPrice(price);
                }

                province.add(provincePrices);
            }

            scanner.close();

            return new FoodProduct(name, province);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getPrice(int year, int month) {
        double totalPrice = 0;
        int count = 0;
        for (Provinces provinces : province) {
            List<Double> prices = provinces.getPrices();
            int index = (year - 2010) * 12 + (month - 1);
            if (index < 0 || index >= prices.size()) {
                throw new IndexOutOfBoundsException("Invalid year or month");
            }
            totalPrice += prices.get(index);
            count++;
        }
        return totalPrice / count;
    }

    public double getPrice(int year, int month, String province) {
        for (Provinces provinces : this.province) {
            if (provinces.getProvince().equalsIgnoreCase(province)) {
                List<Double> prices = provinces.getPrices();
                int index = (year - 2010) * 12 + (month - 1);
                if (index < 0 || index >= prices.size()) {
                    throw new IndexOutOfBoundsException("Invalid year or month");
                }
                return prices.get(index);
            }
        }
        throw new IndexOutOfBoundsException("Invalid province");
    }

    private static class Provinces {
        private String province;
        private List<Double> prices;

        public Provinces(String province) {
            this.province = province;
            this.prices = new ArrayList<>();
        }

        public void addPrice(Double price) {
            prices.add(price);
        }

        public String getProvince() {
            return province;
        }

        public List<Double> getPrices() {
            return prices;
        }
    }
}
