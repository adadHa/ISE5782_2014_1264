package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represent a list Geometries together
 */
public class Geometries extends Intersectable{
    final private List<Intersectable> geometriesList;

    /**
     * default constructor of Geometries, without parameters, initialize an empty list
     */
    public Geometries()
    {
        geometriesList = new ArrayList<Intersectable>();
    }

    /**
     * constructor of Geometries, with parameters, the list contain the parameters
     * @param geometries
     */
    public Geometries(Intersectable... geometries){
        this.geometriesList = List.of(geometries);
    }

    /**
     * add more Geometries to the current list
     * @param geometries
     */
    public void add(Intersectable... geometries){
        for (Intersectable g : geometries){
            this.geometriesList.add(g);
        }
    }

    /**
     * find the intersections between the ray and the Geometries
     * @param ray
     * @param maxDistance
     * @return list of geoPoints, the intersections of the given ray with the geometries, if there, else return null
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        ArrayList<GeoPoint> resultList = new ArrayList<GeoPoint>();
        for (Intersectable g : geometriesList) {
            List<GeoPoint> l = g.findGeoIntersections(ray,maxDistance);
            if (l != null)
                for (GeoPoint gp : l) {
                    resultList.add(gp);
                }
        }
        if(resultList.size() != 0){
            return resultList;
        }
        return null;
    }

}
