package tk.solaapps.ohtune.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import tk.solaapps.ohtune.model.Job;
import tk.solaapps.ohtune.model.JobType;
import tk.solaapps.ohtune.model.Order;

public class JobDaoImpl extends BaseDao implements IJobDao{

	@Override
	public boolean addJob(Job job) {
		getSession().save(job);
		return true;
	}

	@Override
	public boolean updateJob(Job job) {
		getSession().saveOrUpdate(job);
		return true;
	}
	
	@Override
	public boolean deleteJob(Job job)
	{
		getSession().delete(job);
		return true;
	}

	@Override
	public List<Job> getJobByOrder(Order order) {
		if(order == null)
			return new ArrayList<Job>();
		
		List<Job> jobs = getSession().createCriteria(Job.class).add(Restrictions.eq("orders", order)).list();
		
		return jobs;
	}

	@Override
	public List<Job> searchJob(String[] columns, Object[] values, String[] inClause, Collection[] in, int start,
			int limit, String orderby, boolean sortAsc) {
		List<Job> jobs = this.search(columns, values, inClause, in, start, limit, orderby, sortAsc);
		/*
		Criteria c = getSession().createCriteria(Job.class);
		
		if(columns != null && values != null)
		{
			for(int i = 0; i < columns.length; i++)
			{
				c.add(Restrictions.eq(columns[i], values[i]));
			}
		}
		
		if(in != null)
		{
			c.add(Restrictions.in(inClause, in));
		}
		
		c.setMaxResults(limit);
		if(orderby != null && !orderby.equals(""))
		{
			c.setFirstResult(start);
			
			if(sortAsc)
				c.addOrder(Property.forName(orderby).asc());
			else
				c.addOrder(Property.forName(orderby).desc());
		}
		
		jobs = c.list();
		*/
		return jobs;
	}
	
	public Job getJobById(Long id)
	{
		List<Job> jobs = getSession().createCriteria(Job.class).add(Restrictions.eq("id", id)).list();
		if(jobs.size() > 0)
			return jobs.get(0);
		else
			return null;
	}
	
	@Override
	protected Class getModelClass() {
		return Job.class;
	}

	@Override
	public List<Job> getJobByCompDateAndSection(Date compDate, JobType jobType) {
		Date from = new Date();
		from.setYear(compDate.getYear());
		from.setMonth(compDate.getMonth());
		from.setDate(compDate.getDate());
		from.setHours(0);
		from.setMinutes(0);
		from.setSeconds(0);
		
		Date to = new Date();
		to.setYear(compDate.getYear());
		to.setMonth(compDate.getMonth());
		to.setDate(compDate.getDate());
		to.setHours(23);
		to.setMinutes(59);
		to.setSeconds(59);
		
		Criteria criteria = getSession().createCriteria(Job.class);
		criteria.add(Restrictions.eq("status", Job.STATUS_DONE)).add(Restrictions.eq("job_type", jobType)).add(Restrictions.between("complete_date", from, to));
		criteria.createAlias("orders", "o");
		criteria.addOrder(org.hibernate.criterion.Order.asc("o.product_name"));
		
		
		List<Job> jobs = criteria.list();
		
		return jobs;
	}
}
