package tk.solaapps.ohtune.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.dialect.HSQLDialect;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import tk.solaapps.ohtune.dao.PostDaoImpl;
import tk.solaapps.ohtune.dao.UserACDaoImpl;
import tk.solaapps.ohtune.model.Post;
import tk.solaapps.ohtune.model.UserAC;

public class DAOTest {
	private UserACDaoImpl userdao = new UserACDaoImpl(); 
	private PostDaoImpl postdao = new PostDaoImpl();
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction tx;
	@Before
	public void setUp() throws Exception {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:mem:ProductDAOTest");
		dataSource.setUsername("sa");
		
		AnnotationSessionFactoryBean annotatedSessionFactoryBean = new AnnotationSessionFactoryBean();
		annotatedSessionFactoryBean.setDataSource(dataSource);
		Properties prop = new Properties();
		prop.setProperty("hibernate.dialect", HSQLDialect.class.getName());
		prop.setProperty("hibernate.show_sql", "true");
		prop.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		annotatedSessionFactoryBean.setHibernateProperties(prop);
		annotatedSessionFactoryBean.setAnnotatedClasses(new Class[]{Post.class, UserAC.class});
		
		
		sessionFactory = annotatedSessionFactoryBean.buildSessionFactory();
		
	    userdao.setSessionFactory(sessionFactory);
	    postdao.setSessionFactory(sessionFactory);
	    
	    this.session = SessionFactoryUtils.getSession(sessionFactory, true);
	    TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
	}

	@After
	public void tearDown() throws Exception {
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}

	@Test
	public void testPostUserDAO() {
		tx = session.beginTransaction();
		Post post = new Post();
		post.setId(99l);
		post.setName("testpost");
		post.setRemark("testpost");
		post.setSection(99l);
		boolean success = postdao.addPost(post);
		tx.commit();
		
		List<Post> posts = postdao.loadAllPost();
		assertTrue(success);
		assertTrue(posts.size() == 1);
		assertTrue(posts.get(0).getName().equals(post.getName()));
		
		tx.begin();
		UserAC userac = new UserAC();
		userac.setBirthday(new Date());
		userac.setCreate_date(new Date());
		userac.setEmail("ivanagxu@gmail.com");
		userac.setEmploy_date(new Date());
		userac.setId(99l);
		userac.setLogin_id("testu");
		userac.setName("testu");
		userac.setPassword("testpwd");
		userac.setPost(post);
		userac.setRemark("testu");
		userac.setSalary(0f);
		userac.setSex("Male");
		userac.setStatus("Enable");
		userac.setUpdate_date(new Date());
		boolean result = userdao.addUserAC(userac);
		tx.commit();
		
		assertTrue(result);
		List<UserAC> users = userdao.loadAllUserAC();
		assertTrue(users.size() == 1);
		assertTrue(users.get(0).getName().equals(userac.getName()));
	}

}
