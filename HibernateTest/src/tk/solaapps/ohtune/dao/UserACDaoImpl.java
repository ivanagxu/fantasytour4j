package tk.solaapps.ohtune.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import tk.solaapps.ohtune.model.UserAC;

@Transactional
public class UserACDaoImpl implements IUserACDao{
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
	public List<UserAC> loadAllUserAC() {
        List<UserAC> users = this.sessionFactory.getCurrentSession().createCriteria(UserAC.class)                              
        		.list();
        return users;
    }

	@Override
	public boolean addUserAC(UserAC userac) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(userac);
		return true;
	}
    
    
}
