package application; 
/* 
 * Projeto em andamento!!!!!!!!!!!!!!!!
 * DAO do curso do Nélio Alves
*/

import java.sql.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args) throws Exception {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        System.out.println("=== Teste 1 : seller findById: ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println();
        System.out.println("=== Teste 2 : seller findById: ===");
        List<Seller> sellers = sellerDao.findByIdDepartment(2);
        for(Seller s : sellers){
            System.out.println(s);
        }

        System.out.println();
        System.out.println("=== Teste 3 : seller findall ===");
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
        System.out.println("=== Teste 6 : Delete ===");
        sellerDao.deleteById(9);
    }
}
