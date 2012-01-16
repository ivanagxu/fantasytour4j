package tk.solaapps.ohtune.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import tk.solaapps.ohtune.model.Dummy;
import tk.solaapps.ohtune.model.Job;
import tk.solaapps.ohtune.model.JobType;
import tk.solaapps.ohtune.model.Order;
import tk.solaapps.ohtune.model.Post;
import tk.solaapps.ohtune.model.Product;
import tk.solaapps.ohtune.model.ProductRate;
import tk.solaapps.ohtune.model.Role;
import tk.solaapps.ohtune.model.UserAC;
import tk.solaapps.ohtune.pattern.JsonResponse;
import tk.solaapps.ohtune.pattern.OhtuneLogger;
import tk.solaapps.ohtune.pattern.SystemConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OhtuneService extends OhtuneDA implements IOhtuneService {

	private Gson gson = null;
	private SystemConfig config;

	@Override
	public boolean test(UserAC userac) throws Exception {
		List<Dummy> dummyList = dummyDao.getAllDummy();
		return dummyList == null;
	}

	@Override
	public SystemConfig getSystemConfig() {
		return config;
	}

	@Override
	public void setSystemConfig(SystemConfig config) {
		this.config = config;
	}

	@Override
	public synchronized Gson getGson() {
		if (gson == null) {
			gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		}
		return gson;
	}

	@Override
	public JsonResponse genJsonResponse(boolean success, String msg, Object data) {
		return new JsonResponse(success, msg, data);
	}

	@Override
	public UserAC login(String login_id, String password) {
		OhtuneLogger.info("User login, userid=" + login_id);
		
		UserAC user = this.getUserACByLoginId(login_id);
		if (user == null)
			return null;

		if (user.getPassword().equals(password)) {
			return user;
		} else {
			return null;
		}
	}
	
	@Override
	public boolean createUser(UserAC user, UserAC operator) {
		Post post = user.getPost();
		this.addPost(post);
		post = this.getPostByName(post.getName());
		user.setPost(post);
		
		OhtuneLogger.info("Create new user, userid=" + user.getLogin_id() + " by " + operator.getLogin_id());
		
		return this.addUserAC(user);
	}

	@Override
	public boolean deleteUser(UserAC user, UserAC operator) {
		Post post = user.getPost();
		
		OhtuneLogger.info("Delete user, userid=" + user.getLogin_id() + " by " + operator.getLogin_id());
		
		return this.deleteUserAC(user) & this.deletePost(post);
	}

	@Override
	public boolean createOrder(Order order, List<Job> jobs, UserAC operator) {
		SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String number = sm.format(date) + "-";
		
		String[] columns = new String[] { Order.COLUMN_NUMBER };
		String[] values = new String[] { number + "001" };
		
		int counter = 1;
		while(this.searchOrder(columns, values, null,null, 0, 1, "id", true).size() > 0)
		{
			counter++;
			values = new String[] { number + ("" + (1000 + counter)).substring(1) };
		}
		
		number = values[0];
		order.setNumber(number);
		OhtuneLogger.info("Create order, number=" + order.getNumber() + " by " + operator.getLogin_id());
		if(this.addOrder(order))
		{
			//Product product = this.getProductByName(order.getProduct_name());
			//product.setFinished(product.getFinished() - order.getUse_finished());
			//product.setSemi_finished(product.getSemi_finished() - order.getUse_semi_finished());
			boolean success = true; //this.updateProduct(product);
			
			order = this.searchOrder(columns, values,null,null, 0, 1, "id", true).get(0);
			for(int i = 0; i < jobs.size(); i++)
			{
				jobs.get(i).setComplete_date(null);
				jobs.get(i).setDeadline(order.getDeadline());
				jobs.get(i).setFinished(0);
				jobs.get(i).setTotal_rejected(0);
				jobs.get(i).setOrders(order);
				jobs.get(i).setStart_date(order.getCreate_date());
				jobs.get(i).setStatus(Job.STATUS_APPROVING);
				jobs.get(i).setUserac(operator);
				success = success & this.addJob(jobs.get(i));
			}
			return success;
		}
		else
		{
			return false;
		}
	}
	
	public boolean updateOrder(Order order, Product product, List<Job> newJobs, List<Job> deleteJobs, UserAC operator)
	{
		OhtuneLogger.info("Update ordrer, by " + operator.getLogin_id());
		boolean success = true;
		success = success & updateOrder(order);
		success = success & updateProduct(product);
		return success;
	}
	
	public boolean deleteJobByOrder(Order order, UserAC operator)
	{
		OhtuneLogger.info("Delete job by order, userid=" + " by " + operator.getLogin_id());
		List<Job> jobs = this.getJobByOrder(order);
		
		boolean success = true;
		for(int i = 0 ; i < jobs.size(); i++)
		{
			success = success & this.deleteJob(jobs.get(i));
		}
		
		return success;
		
	}

	@Override
	public List<Job> getMyJobList(UserAC user) {
		
		List<JobType> jobTypes = this.getAllJobType(false);
		List<Role> userRoles = user.getPost().getRole();
		List<JobType> includeJobTypes = new ArrayList<JobType>();
		for(int i = 0; i < userRoles.size(); i++)
		{
			for(int j = 0 ; j < jobTypes.size(); j++)
			{
				if(userRoles.get(i).getName().equals(Role.SUPERUSER_ADMIN) || userRoles.get(i).getName().equals(Role.SUPERUSER_MANAGER)
						|| userRoles.get(i).getName().equals(Role.SUPERUSER_MANAGER2) || userRoles.get(i).getName().equals(Role.SUPERUSER_MANAGER3))
				{
					includeJobTypes = jobTypes;
					break;
				}
				else if(userRoles.get(i).getId().equals(jobTypes.get(j).getRole().getId()))
				{
					includeJobTypes.add(jobTypes.get(j));
				}
			}
		}
		List<String> status = new ArrayList<String>();
		
		status.add(Job.STATUS_PAUSED);
		status.add(Job.STATUS_PROCESSING);
		
		String[] inClause = new String[] {Job.COLUMN_JOBTYPE, Job.COLUMN_STATUS };
		Collection[] in = new Collection[]{includeJobTypes, status};
		List<Job> jobs = this.searchJob(null , null, inClause, in ,  0, 10000, "id", true);
		
		return jobs;
	}

	@Override
	public boolean completeJob(Job job, UserAC assignedTo, String jobType, int complete_count,
			boolean isCompleted, boolean isRejected, String remark, UserAC operator) {
		
		boolean success = true;
		
		job.setFinish_remark(remark);
		job.setRemaining(job.getRemaining() - complete_count);
		if(isRejected)
		{
			job.setTotal(job.getTotal() - complete_count);
			if(job.getTotal() < 0)
			{
				throw new RuntimeException("返工数不能大于生产总数");
			}
		}
		else
			job.setFinished(job.getFinished() + complete_count);
		
		if(isCompleted || job.getRemaining() == 0)
		{
			job.setComplete_date(new Date());
			job.setRemaining(0);
			job.setStatus(Job.STATUS_DONE);
		}
		success = success && this.updateJob(job);
		
		JobType jt = this.getJobTypeByName(jobType);
		
		if(jt == null)
		{
			OhtuneLogger.info("Unknow job type selected to complete job, job type=" + jobType + " by " + operator.getLogin_id());
			return false;
		}
		
		List<Job> jobByOrder = this.getJobByOrder(job.getOrders());
		Job existingJob = null;
		for(int i = 0 ; i < jobByOrder.size(); i++)
		{
			if(jt.getName().equals(jobByOrder.get(i).getJob_type().getName()))
			{
				if(assignedTo == null && jobByOrder.get(i).getAssigned_to() == null)
				{
					existingJob = jobByOrder.get(i);
					break;
				}
				else if(assignedTo != null && jobByOrder.get(i).getAssigned_to() != null)
				{
					if(assignedTo.getId().longValue() == jobByOrder.get(i).getAssigned_to().getId().longValue())
					{
						existingJob = jobByOrder.get(i);
						break;
					}
				}
			}
		}
		
		if(existingJob == null)
		{
			if(isRejected)
				throw new RuntimeException("返工失败,找不到需要返工的工作和员工");
			
			Job newJob = new Job();
			newJob.setComplete_date(null);
			newJob.setDeadline(job.getOrders().getDeadline());
			newJob.setFinish_remark("");
			newJob.setJob_type(jt);
			newJob.setOrders(job.getOrders());
			newJob.setRemaining(complete_count);
			newJob.setStart_date(new Date());
			newJob.setStatus(Job.STATUS_PROCESSING);
			newJob.setTotal(complete_count);
			newJob.setUserac(operator);
			newJob.setAssigned_to(assignedTo);
			newJob.setPrevious_jobid(job.getId());
			newJob.setTotal_rejected(0);
			newJob.setFinished(0);
			
			if(newJob.getJob_type().getName().equals(JobType.FINISH_DEPOT) || 
					newJob.getJob_type().getName().equals(JobType.FINISH_SEMI_FINISH))
			{
				newJob.setComplete_date(new Date());
				newJob.setRemaining(0);
				newJob.setFinished(complete_count);
				newJob.setStatus(Job.STATUS_DONE);
				
				Product product = this.getProductByName(newJob.getOrders().getProduct_name());
				if(product != null)
				{
					if(newJob.getJob_type().getName().equals(JobType.FINISH_DEPOT))
					{
						product.setFinished(product.getFinished() + complete_count);
					}
					else
					{
						product.setSemi_finished(product.getSemi_finished() + complete_count);
					}
					this.updateProduct(product);
				}
			}
			success = this.addJob(newJob);
		}
		else
		{
			if(existingJob.getJob_type().getName().equals(JobType.FINISH_DEPOT) || 
					existingJob.getJob_type().getName().equals(JobType.FINISH_SEMI_FINISH))
			{
				existingJob.setStart_date(new Date());
				existingJob.setRemaining(0);
				existingJob.setTotal(existingJob.getTotal() + complete_count);
			}
			else
			{
				existingJob.setStart_date(new Date());
				existingJob.setRemaining(existingJob.getRemaining() + complete_count);
				existingJob.setComplete_date(null);
				existingJob.setStatus(Job.STATUS_PROCESSING);
				if(isRejected)
				{
					existingJob.setTotal_rejected(existingJob.getTotal_rejected() + complete_count);
					existingJob.setFinished(existingJob.getFinished() - complete_count);
					
					if(existingJob.getFinished() < 0)
						throw new RuntimeException("返工数不能大于员工的生产总数");
				}
				else
				{
					existingJob.setTotal(existingJob.getTotal() + complete_count);
				}
			}
			success = this.updateJob(existingJob);
		}
		
		return success;
	}

	@Override
	public boolean completeOrder(Order order, UserAC operator) {
		order.setStatus(Order.STATUS_FINISHED);
		return this.updateOrder(order);
	}

	@Override
	public List<ProductRate> generateProductRateByProduct(Product product,
			UserAC operator) {
		String[] columns = new String[] { Order.COLUMN_PRODUCT_NAME, Order.COLUMN_STATUS };
		String[] values = new String[] { product.getName(), Order.STATUS_FINISHED };
		
		List<Order> orders = searchOrder(columns, values, null,null,0, 100, "id", false);
		List<ProductRate> rates = new ArrayList<ProductRate>();
		
		ProductRate rate;
		List<Job> jobs;
		int total;
		int finished;
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		for(int i = 0 ; i < orders.size(); i++)
		{
			total = orders.get(i).getQuantity();
			finished = 0;
			jobs = this.getJobByOrder(orders.get(i));
			for(int j = 0; j < jobs.size(); j++)
			{
				if(jobs.get(j).getJob_type().getName().equals(JobType.FINISH_DEPOT) ||
						jobs.get(j).getJob_type().getName().equals(JobType.FINISH_SEMI_FINISH))
				{
					finished += jobs.get(j).getTotal();
				}
			}
			double dRate = (double)(((double)finished) / ((double)total));
			rate = new ProductRate();
			rate.setOrder(orders.get(i));
			rate.setProduct(this.getProductByName(orders.get(i).getProduct_name()));
			rate.setRate("" + (Double.valueOf(twoDForm.format(dRate * 100))) + "%");
			rates.add(rate);
		}
		return rates;
	}

	@Override
	public boolean approveOrder(Order order, List<Job> jobs, UserAC operator) {
		
		OhtuneLogger.info("Save order, number=" + order.getNumber() + " by " + operator.getLogin_id());
		order.setStatus(Order.STATUS_PROCESSING);
		if(this.updateOrder(order))
		{
			Product product = this.getProductByName(order.getProduct_name());
			product.setFinished(product.getFinished() - order.getUse_finished());
			product.setSemi_finished(product.getSemi_finished() - order.getUse_semi_finished());
			boolean success = this.updateProduct(product);
			
			for(int i = 0; i < jobs.size(); i++)
			{
				jobs.get(i).setComplete_date(null);
				jobs.get(i).setDeadline(order.getDeadline());
				jobs.get(i).setFinished(0);
				jobs.get(i).setTotal_rejected(0);
				jobs.get(i).setOrders(order);
				jobs.get(i).setStart_date(new Date());
				jobs.get(i).setStatus(Job.STATUS_PROCESSING);
				jobs.get(i).setUserac(operator);
				success = success & this.addJob(jobs.get(i));
			}
			return success;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean pauseOrder(Order order, UserAC operator) {
		OhtuneLogger.info("Pause order, number=" + order.getNumber() + " by " + operator.getLogin_id());
		order.setStatus(Order.STATUS_PAUSED);
		boolean success = this.updateOrder(order);
		List<Job> jobByOrder = this.getJobByOrder(order);
		for(int i = 0; i < jobByOrder.size(); i++)
		{
			if(jobByOrder.get(i).getStatus().equals(Job.STATUS_PROCESSING))
			{
				jobByOrder.get(i).setStatus(Job.STATUS_PAUSED);
				success = success & this.updateJob(jobByOrder.get(i));
			}
		}
		return success;
	}

	@Override
	public boolean cancelOrder(Order order, UserAC operator) {
		OhtuneLogger.info("Cancel order, number=" + order.getNumber() + " by " + operator.getLogin_id());
		
		Product product = this.getProductByName(order.getProduct_name());
		if(product != null && !order.getStatus().equals(Order.STATUS_APPROVING))
		{
			product.setFinished(product.getFinished() + order.getUse_finished());
			product.setSemi_finished(product.getSemi_finished() + order.getUse_semi_finished());
			this.updateProduct(product);
		}
		
		order.setStatus(Order.STATUS_CANCELED);
		
		boolean success = this.updateOrder(order);
		List<Job> jobByOrder = this.getJobByOrder(order);
		for(int i = 0; i < jobByOrder.size(); i++)
		{
			jobByOrder.get(i).setStatus(Job.STATUS_CANCELED);
			success = success & this.updateJob(jobByOrder.get(i));
		}
		return success;
	}

	@Override
	public boolean resumeOrder(Order order, UserAC operator) {
		OhtuneLogger.info("Resume order, number=" + order.getNumber() + " by " + operator.getLogin_id());
		order.setStatus(Order.STATUS_PROCESSING);
		boolean success = this.updateOrder(order);
		List<Job> jobByOrder = this.getJobByOrder(order);
		for(int i = 0; i < jobByOrder.size(); i++)
		{
			if(jobByOrder.get(i).getStatus().equals(Job.STATUS_PAUSED))
			{
				jobByOrder.get(i).setStatus(Job.STATUS_PROCESSING);
				success = success & this.updateJob(jobByOrder.get(i));
			}
		}
		return success;
	}

	@Override
	public boolean addJobToOrder(Order order, JobType jobType, int iQuantity, UserAC assignedTo,
			UserAC operator) {
		
		OhtuneLogger.info("Add job to order, number=" + order.getNumber() + " by " + operator.getLogin_id());
		
		List<Job> jobByOrder = this.getJobByOrder(order);
		Job existingJob = null;
		boolean success = true;
		
		for(int i = 0 ; i < jobByOrder.size(); i++)
		{
			if(jobType.getName().equals(jobByOrder.get(i).getJob_type().getName()))
			{
				if(assignedTo == null && jobByOrder.get(i).getAssigned_to() == null)
				{
					existingJob = jobByOrder.get(i);
					break;
				}
				else if(assignedTo != null && jobByOrder.get(i).getAssigned_to() != null)
				{
					if(assignedTo.getId().longValue() == jobByOrder.get(i).getAssigned_to().getId().longValue())
					{
						existingJob = jobByOrder.get(i);
						break;
					}
				}
			}
		}
		
		if(existingJob == null)
		{	
			Job newJob = new Job();
			newJob.setComplete_date(null);
			newJob.setDeadline(order.getDeadline());
			newJob.setFinish_remark("");
			newJob.setJob_type(jobType);
			newJob.setOrders(order);
			newJob.setRemaining(iQuantity);
			newJob.setStart_date(new Date());
			newJob.setStatus(Job.STATUS_PROCESSING);
			newJob.setTotal(iQuantity);
			newJob.setUserac(operator);
			newJob.setAssigned_to(assignedTo);
			newJob.setPrevious_jobid(null);
			newJob.setTotal_rejected(0);
			newJob.setFinished(0);
			
			if(newJob.getJob_type().getName().equals(JobType.FINISH_DEPOT) || 
					newJob.getJob_type().getName().equals(JobType.FINISH_SEMI_FINISH))
			{
				newJob.setComplete_date(new Date());
				newJob.setRemaining(0);
				newJob.setFinished(iQuantity);
				newJob.setStatus(Job.STATUS_DONE);
				
				Product product = this.getProductByName(newJob.getOrders().getProduct_name());
				if(product != null)
				{
					if(newJob.getJob_type().getName().equals(JobType.FINISH_DEPOT))
					{
						product.setFinished(product.getFinished() + iQuantity);
					}
					else
					{
						product.setSemi_finished(product.getSemi_finished() + iQuantity);
					}
					this.updateProduct(product);
				}
			}
			success = this.addJob(newJob);
		}
		else
		{
			if(existingJob.getJob_type().getName().equals(JobType.FINISH_DEPOT) || 
					existingJob.getJob_type().getName().equals(JobType.FINISH_SEMI_FINISH))
			{
				existingJob.setStart_date(new Date());
				existingJob.setRemaining(0);
				existingJob.setTotal(existingJob.getTotal() + iQuantity);
			}
			else
			{
				existingJob.setStart_date(new Date());
				existingJob.setRemaining(existingJob.getRemaining() + iQuantity);
				existingJob.setComplete_date(null);
				existingJob.setStatus(Job.STATUS_PROCESSING);
				existingJob.setTotal(existingJob.getTotal() + iQuantity);
			}
			success = this.updateJob(existingJob);
		}
		
		return success;
	}
	
	
}
