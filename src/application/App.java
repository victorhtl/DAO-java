package application;

import java.sql.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import DB.DB;

public class App {
    public static void main(String[] args) throws Exception {
        SellerDao sellerDao = DaoFactory.createSellerDao(
                "jdbc:postgresql://localhost:5432/estudos_java",
                "postgres",
                "0000"
        );
        
        System.out.println("=== Teste 1 : get seller by id: ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println();
        System.out.println("=== Teste 2 : get sellers by department id: ===");
        List<Seller> sellers = sellerDao.findByIdDepartment(2);
        for(Seller s : sellers){
            System.out.println(s);
        }

        System.out.println();
        System.out.println("=== Teste 3 : get all sellers ===");
        sellers = sellerDao.findAll();
        for(Seller s : sellers){
            System.out.println(s);
        }

        System.out.println();
        System.out.println("=== Teste 4 : Seller Insert ===");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(100000), 4000., new Department(2, null));
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id " + newSeller.getId());

        System.out.println();
        System.out.println("=== Teste 5 : Seller Update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Update complete");

        System.out.println();
        System.out.println("=== Teste 6 : Seller Delete ===");
        sellerDao.deleteById(9);

        DB.closeConnection();
    }
}
