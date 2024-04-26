import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public abstract class Country {
    private final String name;
    private static String confirmed_cases_path, deaths_path;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getMessage(String country){ return String.format("ERROR: %s\n(%s not found)", new CountryNotFoundException(country), country); }
    public abstract int getConfirmedCases(LocalDate date);
    public abstract int getDeaths(LocalDate date);
    public static void setFiles(String confirmed_cases_path, String deaths_path) throws FileNotFoundException {
        File file = new File(confirmed_cases_path);
        if(file.exists()) {
            Country.confirmed_cases_path = confirmed_cases_path;
        }
        else{
            throw new FileNotFoundException();
        }
        file = new File(deaths_path);
        if(file.exists()) {
            Country.deaths_path = deaths_path;
        }
        else{
            throw new FileNotFoundException();
        }
    }


    public static Country fromCsv(String country) throws IOException, CountryNotFoundException {
        File cases_file = new File(Country.confirmed_cases_path);
        File deaths_file = new File(Country.deaths_path);
        BufferedReader cases_read = new BufferedReader(new FileReader(cases_file));
        BufferedReader deaths_read = new BufferedReader(new FileReader(deaths_file));

        CountryColumns cc = getCountryColumns(cases_read.readLine(), cases_read.readLine() , country);
        deaths_read.readLine(); //zczytanie bo w wywołaniu cc robimy to samo a więc trzeba wyrównać
        deaths_read.readLine();

        if(cc.getColumnCount() == 458){
            CountryWithProvinces cwp = new CountryWithProvinces(new ArrayList<Element>(), country);
            String line_cases = cases_read.readLine(), line_deaths = deaths_read.readLine();
            while(!(line_cases == null)){
                String[] parse_cases = line_cases.split(";");
                String[] parse_deaths = line_deaths.split(";");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/d/yy");
                LocalDate time = LocalDate.parse(parse_cases[0], format);
                cwp.addDailyStatistic(time, cc.getFirstColumnIndex(), cc.getFirstColumnIndex());
                line_cases = cases_read.readLine();
                line_deaths = deaths_read.readLine();

            }
            cases_read.close();
            deaths_read.close();
            return cwp;
        }
        else {
            CountryWithoutProvinces cwip = new CountryWithoutProvinces((country));
            String line_cases = cases_read.readLine(), line_deaths = deaths_read.readLine();
            while(!(line_cases == null)){
                String[] parse_cases = line_cases.split(";");
                String[] parse_deaths = line_deaths.split(";");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/d/yy");
                LocalDate time = LocalDate.parse(parse_cases[0], format);
                cwip.addDailyStatistic(time, cc.getFirstColumnIndex(), cc.getFirstColumnIndex());
                line_cases = cases_read.readLine();
                line_deaths = cases_read.readLine();
            }
            cases_read.close();
            deaths_read.close();
            return cwip;
        }


    }


    public static void fromCsv(String[] countries) throws CountryNotFoundException, IOException {
        for(String country : countries){
            try {
                Country.fromCsv(country);
            }
            catch(CountryNotFoundException e){
                System.err.println("Error: " + Country.getMessage(country) + "Not found!");
            }
        }
    }

    public static Element record(String name, LocalDate day) throws IOException, CountryNotFoundException {
        File cases_file = new File(Country.confirmed_cases_path);
        File deaths_file = new File(Country.deaths_path);
        BufferedReader cases_read = new BufferedReader(new FileReader(cases_file));
        BufferedReader deaths_read = new BufferedReader(new FileReader(deaths_file));
        String line1 = cases_read.readLine();
        String line2 = deaths_read.readLine();
        CountryColumns cc = getCountryColumns(cases_read.readLine(), cases_read.readLine() , name);
        boolean found = false;
        while(true) {
            line1 = cases_read.readLine();
            line2 = deaths_read.readLine();
            String[] st = line1.split(";");
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/d/yy");
            LocalDate time = LocalDate.parse(st[0], format);
            if (time.isEqual(day)) {
                found = true;
                break;
            } else if(line1.equals("null") || line2.equals("null")) {
                break;
            }
            else{
                continue;
            }
        }
        if(found) {
            String[] st = line1.split(";");
            String[] st2 = line2.split(";");
            return new Element(Integer.parseInt(st[cc.getFirstColumnIndex()]), Integer.parseInt(st2[cc.getFirstColumnIndex()]), day);
        }
        else{
            return new Element(-1, -1, day);
        }
        }

    private static class CountryColumns{
        public final int firstColumnIndex;
        public final int columnCount;

        public CountryColumns(int firstColumnIndex, int columnCount) {
            this.firstColumnIndex = firstColumnIndex;
            this.columnCount = columnCount;
        }

        public int getColumnCount() {
            return columnCount;
        }

        public int getFirstColumnIndex() {
            return firstColumnIndex;
        }
    }

    public static class Element{
        int deaths, diseases;
        LocalDate day;

        public Element(int deaths, int diseases, LocalDate day) {
            this.deaths = deaths;
            this.diseases = diseases;
            this.day = day;
        }

        public int getDeaths() {
            return deaths;
        }

        public int getDiseases() {
            return diseases;
        }

        public LocalDate getDay() {
            return day;
        }

        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }

        public void setDiseases(int diseases) {
            this.diseases = diseases;
        }

        public void setDay(LocalDate day) {
            this.day = day;
        }
    }

    private static CountryColumns getCountryColumns(String first_line, String second_line, String country) throws CountryNotFoundException {
        StringTokenizer st = new StringTokenizer(first_line, ";");
        StringTokenizer st2 = new StringTokenizer(second_line, ";");
        String column = "";
        int firstcolumnIndex = 0;
        boolean flag = false;
        while(st.hasMoreTokens()) {
            column = st2.nextToken();
            if (country.equals(st.nextToken())) {
                flag = true;
                break;
            } else {
                firstcolumnIndex++;
            }
        }

            if(!flag){
                throw new CountryNotFoundException(country);
            }
            else if(flag && column.equals("nan")) {
                return new CountryColumns(firstcolumnIndex, 457);
            }
            else{
                return new CountryColumns(firstcolumnIndex, 458);
            }

    }


}
