package domain;

import java.util.List;

public class Car {

    public Integer numberOfStreets;
    public List<Street> streetList;

    public Car(int numberOfStreets, List<Street> streetList) {
        this.numberOfStreets = numberOfStreets;
        this.streetList = streetList;
    }
}
