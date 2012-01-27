package tk.solaapps.ohtune.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.solaapps.ohtune.model.Job;
import tk.solaapps.ohtune.model.JobType;
import tk.solaapps.ohtune.model.Order;
import tk.solaapps.ohtune.model.UserAC;
import tk.solaapps.ohtune.pattern.JsonDataWrapper;
import tk.solaapps.ohtune.pattern.JsonResponse;
import tk.solaapps.ohtune.pattern.OhtuneLogger;
import tk.solaapps.ohtune.pattern.OhtuneServiceHolder;
import tk.solaapps.ohtune.service.IOhtuneService;

import com.google.gson.Gson;

/**
 * Servlet implementation class JobController
 */
@WebServlet("/JobController")
public class JobController extends HttpServlet implements IOhtuneController{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionName = request.getParameter("action");
		process(actionName, request, response);
	}
	
	@Override
	public void process(String actionName, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(actionName == null || actionName.trim().equals(""))
		{
			OhtuneLogger.error("Unknow action name in JobController");
		}
		else if(actionName.equals("getMyJobList"))
		{
			getMyJobList(request, response);
		}
		else if(actionName.equals("getJobByOrder"))
		{
			getJobByOrder(request, response);
		}
		else if(actionName.equals("completeJob"))
		{
			completeJob(request,response);
		}
		else
		{
			OhtuneLogger.error("Unknow action name in JobController, action name = " + actionName);
		}
	}
	
	private void getMyJobList(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		UserAC sessionUser = new UserAC();
		if(request.getSession().getAttribute("user") != null)
			sessionUser = (UserAC)request.getSession().getAttribute("user");
		
		
		IOhtuneService service = (IOhtuneService)OhtuneServiceHolder.getInstence().getBeanFactory().getBean("uhtuneService");
		List<Job> jobs = service.getMyJobList(sessionUser);
		Gson gson = service.getGson();
		JsonDataWrapper dw = new JsonDataWrapper(jobs, JsonDataWrapper.TYPE_JOB);
		response.getOutputStream().write(gson.toJson(dw).getBytes("utf-8"));
	}
	
	private void getJobByOrder(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		UserAC sessionUser = new UserAC();
		if(request.getSession().getAttribute("user") != null)
			sessionUser = (UserAC)request.getSession().getAttribute("user");
		
		String sOrderId = request.getParameter("orderid");
		
		IOhtuneService service = (IOhtuneService)OhtuneServiceHolder.getInstence().getBeanFactory().getBean("uhtuneService");
		Order order = service.getOrderById(Long.parseLong(sOrderId));
		List<Job> jobs = service.getJobByOrder(order);
		Gson gson = service.getGson();
		JsonDataWrapper dw = new JsonDataWrapper(jobs, JsonDataWrapper.TYPE_JOB);
		response.getOutputStream().write(gson.toJson(dw).getBytes("utf-8"));
	}
	
	private void completeJob(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		UserAC sessionUser = new UserAC();
		if(request.getSession().getAttribute("user") != null)
			sessionUser = (UserAC)request.getSession().getAttribute("user");
		
		String sComplete_count = "0" + request.getParameter("complete_count");
		String sDisuse_count = "0" + request.getParameter("disuse_count");
		String job_type = request.getParameter("job_type");
		
		String sIsCompleted = request.getParameter("is_completed");
		String finish_remark = request.getParameter("finish_remark");
		String id = request.getParameter("id");
		String sAssigned_to = request.getParameter("assigned_to");
		String sIsRejected = request.getParameter("is_rejected");
		
		IOhtuneService service = (IOhtuneService)OhtuneServiceHolder.getInstence().getBeanFactory().getBean("uhtuneService");
		
		JsonResponse jr = null;
		Gson gson = service.getGson();
		
		int iComplete_count = 0;
		int iDisuse_count = 0;
		try
		{
			iComplete_count = Integer.parseInt(sComplete_count);
			iDisuse_count = Integer.parseInt(sDisuse_count);
		}catch(Exception e)
		{
			jr = service.genJsonResponse(false, "完成数或废品数错误", null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
			return;
		}
		
		JobType jobType = service.getJobTypeByName(job_type);
		if(jobType == null)
		{
			jr = service.genJsonResponse(false, "分配部门错误", null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
			return;
		}
		
		Job job = service.getJobById(Long.parseLong(id));
		
		if(job == null)
		{
			jr = service.genJsonResponse(false, "工作不存在", null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
			return;
		}
		
		if(job.getStatus() == Job.STATUS_DONE)
		{
			jr = service.genJsonResponse(false, "工作已完成", null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
			return;
		}
		
		if(!job.getOrders().getStatus().equals(Order.STATUS_PROCESSING))
		{
			jr = service.genJsonResponse(false, "无法完成，订单尚未开始或已经完成", null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
			return;
		}
		
		if(job.getJob_type().getName().equals(job_type))
		{
			jr = service.genJsonResponse(false, "不能分配至相同部门", null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
			return;
		}
		
		if(job.getRemaining() < (iComplete_count + iDisuse_count))
		{
			jr = service.genJsonResponse(false, "完成数加废品数不能大于总数", null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
			return;
		}
		
		UserAC assignedTo = null;
		try
		{
			assignedTo = service.getUserACById(Long.parseLong(sAssigned_to));
		}catch(Exception e)
		{
			assignedTo = null;
		}
		
		boolean isCompleted = (sIsCompleted + "").equals("on");
		if(job.getTotal() == (iComplete_count + iDisuse_count))
			isCompleted = true;
		boolean isRejected = (sIsRejected + "").equals("on");
		
		if(isRejected && (job_type.equals(JobType.FINISH_DEPOT) || job_type.equals(JobType.FINISH_SEMI_FINISH)))
		{
			jr = service.genJsonResponse(false, "无法返工至成品仓或半成品仓", null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
			return;
		}
		
		try
		{
			boolean success = service.completeJob(job, assignedTo, job_type, iComplete_count, iDisuse_count, isCompleted, isRejected, finish_remark, sessionUser);
			
			boolean alldone = true;
			List<Job> jobByOrder = service.getJobByOrder(job.getOrders());
			for(int i = 0 ; i < jobByOrder.size(); i++)
			{
				if(!jobByOrder.get(i).getStatus().equals(Job.STATUS_DONE))
				{
					alldone = false;
				}
			}
			if(alldone)
			{
				success = success & service.completeOrder(job.getOrders(), sessionUser);
			}
			
			jr = service.genJsonResponse(success, "工作成功完成", null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
		}
		catch(RuntimeException e)
		{
			jr = service.genJsonResponse(false, e.getMessage(), null);
			response.getOutputStream().write(gson.toJson(jr).getBytes("utf-8"));
		}
	}

}
