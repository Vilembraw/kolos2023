import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Polygon {

    private List<Point> poly = new ArrayList<>();

    public Polygon(ArrayList<Point> points) {
        this.poly = points;
    }

    public boolean inside(Point point){
        int counter = 0;
        int length = poly.size();
        double a, b, d, x;
        Point pa;
        Point pb;
        boolean is_last;

        for(int i=0; i<length; ++i){
            is_last = false;
            if(i == length-1){
                pa = poly.getLast();
                pb = poly.getFirst();
                is_last = true;
            }
            else{
                pa = poly.get(i);
                pb = poly.get(i+1);
            }

            if(pa.y > pb.y){
                if(!is_last) {
                    Collections.swap(poly, i, i + 1);
                }
                else{
                    Collections.swap(poly, poly.size()-1, 0);
                }
                }
            if(pa.y < point.y && point.y < pb.y){
                d = pb.x - pa.x;
                if(d == 0){
                    x = pa.x;
                }
                else{
                    a = (pb.y - pa.y) / d;
                    b = pa.y - a * pa.x;
                    x = (point.y - b) / a;
                }
                if(x < point.x){
                    counter++;
                }
            }
        }
        if(counter % 2 == 0){
            return false;
        }
        else{
            return true;
        }
    }
}
