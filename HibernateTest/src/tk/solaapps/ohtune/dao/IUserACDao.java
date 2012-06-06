package tk.solaapps.ohtune.dao;

import java.util.List;

import tk.solaapps.ohtune.model.UserAC;

public interface IUserACDao {
	List<UserAC> loadAllUserAC();
	boolean addUserAC(UserAC userac);
}
