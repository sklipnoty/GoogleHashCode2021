package domain;

import java.util.AbstractMap;
import java.util.List;

/**
 * List of pairs street + duration
 */
public class TrafficLightSchedule {
    public Intersection intersection;
    public Street incomingStreet;
    public Integer duration;

    public TrafficLightSchedule(Intersection intersection, Street incomingStreet, Integer duration) {
        this.intersection = intersection;
        this.incomingStreet = incomingStreet;
        this.duration = duration;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public Street getIncomingStreet() {
        return incomingStreet;
    }

    public void setIncomingStreet(Street incomingStreet) {
        this.incomingStreet = incomingStreet;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "TrafficLightSchedule{" +
                "intersection=" + intersection +
                ", incomingStreet=" + incomingStreet +
                ", duration=" + duration +
                '}';
    }
}
