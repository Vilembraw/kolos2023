import java.util.List;

public class AmbigiousProductException extends Exception{
    private List<String> list;

    public AmbigiousProductException(List<String> list) {
        this.list = list;
    }

    @Override
    public String getMessage(){
        String error = "Error Occured!\n";
        for(String element: list){
            error += element + "\n";
        }
        return error;
    }
}
