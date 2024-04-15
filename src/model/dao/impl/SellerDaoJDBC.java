package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DB.DB;
import DB.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;

        try {
            // O argumento RETURN_GENERATED_KEYS serve para retornar o id gerado para esta inserção
            st = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBithDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected Error! No rows Affected");
            }

        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ?");
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBithDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());

            st.executeUpdate();
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    private Department instanciateDepartment(ResultSet rs) throws SQLException{
        return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
    }

    private Seller instanciateSeller(ResultSet rs, Department dp) throws SQLException{
        return new Seller(
            rs.getInt("Id"),
            rs.getString("Name"),
            rs.getString("Email"),
            rs.getDate("Birthdate"),
            rs.getDouble("BaseSalary"),
            dp
        );
    }

    @Override
    public Seller findById(Integer id){
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
			st = conn.prepareStatement("Select seller.*, department.Name as DepName from seller inner join department "
                                            + "on seller.DepartmentId = department.Id where seller.Id = ?");

            st.setInt(1, id);
			
			rs = st.executeQuery();
            if(rs.next()){
                Department department = instanciateDepartment(rs);
                Seller obj = instanciateSeller(rs, department);
                return obj;
            }
            return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> listSellers = new ArrayList<>();

        // Este map é para evitar a duplicação de departamentos intanciados
        // Acho que isso deveria ser global
        Map<Integer, Department> map = new HashMap<>();

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName FROM seller INNER JOIN department "
                                        + "ON seller.DepartmentId = department.Id ORDER BY Name");

            rs = st.executeQuery();

            while(rs.next()){
                Department department = map.get(rs.getInt("DepartmentId"));
                if(department == null){
                    department = instanciateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), department);
                }

                Seller seller = instanciateSeller(rs, department);
                listSellers.add(seller);
            }

            if(!listSellers.isEmpty()){
                return listSellers;
            }
            return null;

        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Seller> findByIdDepartment(Integer id){
        List<Seller> listSellers = new ArrayList<>();

        // Este map é para evitar a duplicação de departamentos instanciados
        Map<Integer, Department> map = new HashMap<>();

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department "
                                + "ON seller.DepartmentId = department.Id WHERE DepartmentId = ? ORDER BY Name");
            
            st.setInt(1, id);
            rs = st.executeQuery();

            while(rs.next()){

                // Antes de instanciar um novo departamento, verifica se ele já existe
                Department department = map.get(rs.getInt("DepartmentId"));
                if(department == null){
                    department = instanciateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), department);
                }

                Seller seller = instanciateSeller(rs, department);
                listSellers.add(seller);
            }
            if(!listSellers.isEmpty()){
                return listSellers;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
    
}
