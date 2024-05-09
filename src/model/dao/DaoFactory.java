package model.dao;

import DB.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao(String url, String user, String password){
        return new SellerDaoJDBC(DB.getConnection(url, user, password));
    }
}
