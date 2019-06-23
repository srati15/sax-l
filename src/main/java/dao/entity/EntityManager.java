package dao.entity;

public class EntityManager {
    private static EntityManager ourInstance = new EntityManager();

    public static EntityManager getInstance() {
        return ourInstance;
    }

    private EntityPersister persister;
    private EntityFinder finder;
    private EntityManager() {
        finder = new EntityFinder();
        persister = new EntityPersister();
    }

    public EntityPersister getPersister() {
        return persister;
    }

    public EntityFinder getFinder() {
        return finder;
    }
}
