public class Resource {
    public enum Type{Coal, Wood, Fish};
    public final Point point;
    Type typ;

    public Resource(Point point, Type typ) {
        this.point = point;
        this.typ = typ;
    }
}
