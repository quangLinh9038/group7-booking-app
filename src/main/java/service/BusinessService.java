package service;

import model.Admin;
import model.Business;
import model.Employee;
import model.Service;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Quang Linh on 04 Aug 2020
 * RUD functions for Business
 */

@Transactional
@org.springframework.stereotype.Service
public class BusinessService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //save(add) business
    public void saveBusiness (Business business){
        //set admin id into business's table
        if (business.getAdmin()!=null){
            int admin_id = business.getAdmin().getId();
            Query query = sessionFactory.getCurrentSession().createQuery("from Admin where id =:id");
            query.setInteger("id",admin_id);
            Admin admin = (Admin) query.uniqueResult();
            admin.setBusiness(business);
        }
        //set employee id
        if (business.getEmployees()!=null){
            for(Employee employee: business.getEmployees()){
                int employee_id = employee.getId();
                Query query = sessionFactory.getCurrentSession().createQuery("from Employee where id =:id");
                query.setInteger("id", employee_id);
                employee = (Employee) query.uniqueResult();
                business.getEmployees().add(employee);
            }
        }
        //set service id
        if (business.getServices()!=null){
            for(Service service: business.getServices()){
                int service_id = service.getId();
                Query query = sessionFactory.getCurrentSession().createQuery("from Service where id =:id");
                query.setInteger("id", service_id);
                service = (Service) query.uniqueResult();
                business.getServices().add(service);
            }
        }
        sessionFactory.getCurrentSession().saveOrUpdate(business);
    }


    //get business by id
    public Business getBusiness (int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Business where id =:id");
        query.setInteger("id",id);
        return (Business)query.uniqueResult();
    }

    //get list business
    public List<Business> getAllBusiness(){
        Query query = sessionFactory.getCurrentSession().createQuery("from Business");
        return query.list();
    }

    //find business by name
    public Business findBusiness(String name){
        Query query = sessionFactory.getCurrentSession().createQuery("from Business where name like :name");
        query.setString("name", "%"+name+"%");
        Business business = (Business) query.uniqueResult();
        return business;
    }

    //update business
    public void updateBusiness(Business business){
        sessionFactory.getCurrentSession().update(business);
    }

    //delete business
    public void deleteBusiness(int id){
        Query query = sessionFactory.getCurrentSession().createQuery("from Business where id =:id");
        query.setInteger("id", id);
        Business business = (Business) query.uniqueResult();
        sessionFactory.getCurrentSession().delete(business);
    }
}
