package tk.solaapps.ohtune.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import tk.solaapps.ohtune.model.Post;

public class PostDaoImpl implements IPostDao{
	private SessionFactory sessionFactory = null;
	
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	@Override
	public boolean addPost(Post post) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(post);
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> loadAllPost() {
		return this.sessionFactory.getCurrentSession().createCriteria(Post.class).list();
	}
}
