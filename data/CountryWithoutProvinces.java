import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CountryWithoutProvinces extends Country{

    private ArrayList<Element> cases_n_deaths;

    public CountryWithoutProvinces(String name) {
        super(name);
    }

    public void get_cases_n_deaths(LocalDate day) throws CountryNotFoundException, IOException {
        cases_n_deaths.add(Country.record(this.getName(), day));
    }

    public void addDailyStatistic(LocalDate date, int deaths, int diseases){
        int index = 0;
        while(date.isEqual(cases_n_deaths.get(index).getDay())){
            index++;
        }
        cases_n_deaths.get(index).setDeaths(cases_n_deaths.get(index).getDeaths() + deaths);
        cases_n_deaths.get(index).setDiseases(cases_n_deaths.get(index).getDiseases() + diseases);
    }

    @Override
    public int getConfirmedCases(LocalDate date) {
        int index = 0;
        while(date.isEqual(cases_n_deaths.get(index).getDay())){
            index++;
        }
        return cases_n_deaths.get(index).getDiseases();
    }

    @Override
    public int getDeaths(LocalDate date) {
        int index = 0;
        while(date.isEqual(cases_n_deaths.get(index).getDay())){
            index++;
        }
        return cases_n_deaths.get(index).getDeaths();
    }
}
