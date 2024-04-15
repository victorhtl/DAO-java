package model.entities;

import java.sql.Date;

public class Seller {
    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;

    private Department department;

    public Seller(Integer id, String name, String email, Date date, Double baseSalary, Department department){
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = date;
        this.baseSalary = baseSalary;
        this.department = department;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public Date getBithDate(){
        return birthDate;
    }

    public Double getBaseSalary(){
        return baseSalary;
    } 

    public Department getDepartment(){
        return department;
    }

    @Override
    public String toString(){
        return "id = "+id+", name = "+name+", email = "+email+", birthDate = "+birthDate+", baseSalary = "+baseSalary+", department = "+department;
    }
}
