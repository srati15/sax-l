package dao;

import datatypes.Domain;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cao<ID, D extends Domain<ID>> {
    private final Map<ID, D> map = new ConcurrentHashMap<>();

    public D findById(ID id) {
        return map.get(id);
    }

    public void add(D object) {
        map.put(object.getId(), object);
    }

    public boolean hasKey(ID key){
        return map.containsKey(key);
    }

    public Collection<D> findAll() {
        return map.values();
    }

    public void delete(ID id) {
        map.remove(id);
    }
}
