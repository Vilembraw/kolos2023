import java.util.ArrayList;
import java.util.List;

public class Land extends Polygon{

    private List<City> cities = new ArrayList<>();
    public Land(ArrayList<Point> points) {
        super(points);
    }

    public void addCity(City city){
        if(!city.inside(city.center)) {
            cities.add(city);
        } else{
            throw new CityRuntimeException(city.getName());
        }
    }

    public void set_isPort(City city){
        int counter = 0;
        for(int i=0; i<4; ++i) {
            if(!city.inside(city.getPoints().get(i))){
                counter++;
            }
        }
        if(counter < 4){
            city.setPort(true);
        }
        else{
            city.setPort(false);
        }
    }

}
