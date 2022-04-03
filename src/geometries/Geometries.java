package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries extends Intersectable{
    final private List<Intersectable> geometriesList;

    public Geometries()
    {
        geometriesList = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... geometries){
        this.geometriesList = List.of(geometries);
    }

    public void add(Intersectable... geometries){
        for (Intersectable g : geometries){
            this.geometriesList.add(g);
        }
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        ArrayList<GeoPoint> resultList = new ArrayList<GeoPoint>();
        for (Intersectable g : geometriesList) {
            List<Point> l = g.findIntersections(ray);
            if (l != null)
                for (Point p : l) {
                    resultList.add(new GeoPoint((Geometry) g,p));
                }
        }
        if(resultList.size() != 0){
            return  resultList;
        }
        return null;
    }
}
