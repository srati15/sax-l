package manager;

import dao.Dao;
import enums.DaoType;

import java.util.Map;

class DaoFactory {
    private static Map<DaoType, Dao > map;
    static void initDaos(){

    }


    static <E extends Dao> E dispatch(DaoType daoType) {
        if (map.containsKey(daoType)) {
            assert map.get(daoType).getDaoType().equals(daoType);
            return (E) map.get(daoType);
        }
        throw new IllegalArgumentException("Dao not found for "+daoType.name());
    }
}
