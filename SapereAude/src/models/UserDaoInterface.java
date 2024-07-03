package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDaoInterface {

	public void doSave(UserBean user) throws SQLException;
	
	public UserBean doRetrieve(String username) throws SQLException;
	
	public UserBean doRetrieveLog(String username, String password) throws SQLException;
	
	public ArrayList<UserBean> doRetrieveAll(String order) throws SQLException;
}