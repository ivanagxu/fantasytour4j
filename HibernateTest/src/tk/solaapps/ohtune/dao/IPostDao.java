package tk.solaapps.ohtune.dao;

import java.util.List;

import tk.solaapps.ohtune.model.Post;

public interface IPostDao {
	boolean addPost(Post post);
	List<Post> loadAllPost();
}
