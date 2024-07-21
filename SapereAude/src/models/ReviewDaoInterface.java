package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReviewDaoInterface {

	public void doSave(ReviewBean revie) throws SQLException;
	
	public ReviewBean doRetrieve(String ISBN) throws SQLException;
	
	public ArrayList<ReviewBean> doRetrieveAll(String account, String order) throws SQLException;
}