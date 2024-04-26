import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CountryWithProvinces extends Country{
    private ArrayList<Element> cases_n_deaths;

    public CountryWithProvinces(ArrayList<Element> lista, String name) {
        super(name);
        this.cases_n_deaths = new ArrayList<>();
        this.cases_n_deaths.addAll(lista);
    }

    public void addDailyStatistic(LocalDate day, int deaths, int diseases){
        int index = 0;
        while(day.isEqual(cases_n_deaths.get(index).getDay())){
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

    @Override //i dunno o co chodzi z tymi prowincjami dlatego jest kopiuj wklej z CountryWithoutPrivinces
    public int getDeaths(LocalDate date) {
        int index = 0;
        while(date.isEqual(cases_n_deaths.get(index).getDay())){
            index++;
        }
        return cases_n_deaths.get(index).getDeaths();
    }
}
