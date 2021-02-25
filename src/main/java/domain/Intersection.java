package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Intersection {
    public Integer id;
    public List<Street> incoming = new ArrayList<>();
    public List<Street> outgoing = new ArrayList<>();

    public Intersection(int startIntersection) {
        this.id = startIntersection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intersection that = (Intersection) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
