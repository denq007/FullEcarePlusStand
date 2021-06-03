/*
import org.hibernate.SessionFactory;
import com.t_systems.ecare.eCare.entity.Contract;
import com.t_systems.ecare.eCare.entity.Customer;
import com.t_systems.ecare.eCare.entity.Option;
import com.t_systems.ecare.eCare.entity.Tariff;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TestDao {
    @Autowired
    static SessionFactory sessionFactory;

    Session session=sessionFactory.getCurrentSession();

    @Test
    public void addNewOption()
    {
    Option option1=new Option("UnlimInternet",100.00,0.00,1);
        Tariff firstTariff=new Tariff("unlimited",123.99,true);
        firstTariff.addOptionToTariff(option1);
    session.beginTransaction();
    session.persist(option1);
    session.getTransaction().commit();
    session.close();
    }
    @Test
    public void addNewCustomer()
    {

    }
}
*/
