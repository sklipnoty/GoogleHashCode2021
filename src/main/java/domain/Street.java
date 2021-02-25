package domain;

import java.util.Objects;

public class Street {
    public String name;
    public Integer time;
    public TrafficLight trafficLight;
    protected Intersection start;
    protected Intersection end;

    public Street(String nameOfStreet, int timeToPassStreet) {
        this.name = nameOfStreet;
        this.time = timeToPassStreet;
    }

    public Intersection getStart() {
        return start;
    }

    public void setStart(Intersection start) {
        this.start = start;
    }

    public Intersection getEnd() {
        return end;
    }

    public void setEnd(Intersection end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return name.equals(street.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
