package tk.solaapps.ohtune.service;

import java.util.List;

import tk.solaapps.ohtune.model.Post;
import tk.solaapps.ohtune.model.UserAC;

public interface IOhtuneService {
	List<Post> getAllPost() throws Exception;
	List<UserAC> getAllUser() throws Exception;
}
