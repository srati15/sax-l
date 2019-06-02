package manager;


import dao.Dao;
import enums.DaoType;

public class DaoManager {
    public DaoManager(){
        DaoFactory.initDaos();
    }
    public  <E extends Dao> E getDao(DaoType daoType){
        return DaoFactory.dispatch(daoType);
    }
}
