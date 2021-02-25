package domain;

import java.util.AbstractMap;
import java.util.List;

/**
 * List of pairs street + duration
 */
public class TrafficLightSchedule {
    public List<AbstractMap.SimpleEntry<Street, Long>> scheduleList;
}
