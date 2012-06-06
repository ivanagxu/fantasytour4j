package tk.solaapps.ohtune.main;

import java.util.List;

import tk.solaapps.ohtune.model.Post;
import tk.solaapps.ohtune.model.UserAC;
import tk.solaapps.ohtune.pattern.OhtuneServiceHolder;
import tk.solaapps.ohtune.service.IOhtuneService;

public class OhtuneMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OhtuneMain main = new OhtuneMain();
		main.update();
	}
	
	private void update()
	{
		try{
			IOhtuneService service = (IOhtuneService)OhtuneServiceHolder.getInstence().getBeanFactory().getBean("ohtuneService");
			List<Post> posts = service.getAllPost();
			List<UserAC> users = service.getAllUser();
			System.out.println("Users : ");
			for(int i = 0; i < users.size(); i++)
			{
				System.out.println(users.get(i).getName());
			}
			System.out.println("Posts : ");
			for(int i = 0; i < posts.size(); i++)
			{
				System.out.println(posts.get(i).getName());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
