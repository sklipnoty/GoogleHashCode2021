package domain;

import java.util.List;

public class Car {

    public Integer numberOfStreets;
    public List<Street> streetList;
    public Street startingStreet;

    public Car(int numberOfStreets, List<Street> streetList, Street startingStreet) {
        this.numberOfStreets = numberOfStreets;
        this.streetList = streetList;
        this.startingStreet = startingStreet;
    }
}
