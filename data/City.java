import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class City extends Polygon{

    public final Point center;
    private String name;
    private boolean isPort;

    Set<Resource.Type> resources = new HashSet<>();

    List<Point> points = new ArrayList<>();
    public City(ArrayList<Point> poly, Point center, String name, double wall_length) {
        super(poly);
        this.center = center;
        this.name = name;
        points.add(new Point(center.x - (wall_length / 2), center.y - (wall_length / 2)));
        points.add(new Point(center.x - (wall_length / 2), center.y + (wall_length / 2)));
        points.add(new Point(center.x + (wall_length / 2), center.y - (wall_length / 2)));
        points.add(new Point(center.x + (wall_length / 2), center.y + (wall_length / 2)));
    }

    public String getName() {
        return name;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPort(boolean port) {
        isPort = port;
    }

    public void addResourcesInRange(ArrayList<Resource> list, double range){
        boolean ifFish = false;
        for (Resource resource : list) {
            if(resource.typ.equals(Resource.Type.Fish)){
                if(!isPort){
                    continue;
                }
            }
            double x1 = resource.point.x;
            double y1 = resource.point.y;
            double x2 = center.x;
            double y2 = center.y;
            if (Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)) <= range) {
                resources.add(resource.typ);
            }
        }
    }
}
