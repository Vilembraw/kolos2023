public class CityRuntimeException extends RuntimeException{
    private String cityName;
    public CityRuntimeException(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String getMessage(){
        return cityName;
    }

}
