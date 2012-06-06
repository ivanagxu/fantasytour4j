package tk.solaapps.ohtune.service;

import java.util.List;

import tk.solaapps.ohtune.dao.IPostDao;
import tk.solaapps.ohtune.dao.IUserACDao;
import tk.solaapps.ohtune.model.Post;
import tk.solaapps.ohtune.model.UserAC;

public class OhtuneService implements IOhtuneService {
	private IUserACDao userACDao = null;
	private IPostDao postDao = null;
	
	public void setUserACDao(IUserACDao userACDao)
	{
		this.userACDao = userACDao;
	}
	public void setPostDao(IPostDao postDao)
	{
		this.postDao = postDao;
	}
	
	@Override
	public List<Post> getAllPost() throws Exception {
		return postDao.loadAllPost();
	}
	
	@Override
	public List<UserAC> getAllUser() throws Exception {
		return userACDao.loadAllUserAC();
	}
}
