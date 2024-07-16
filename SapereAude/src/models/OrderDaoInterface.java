package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDaoInterface {

	public void doSave(OrderBean order) throws SQLException;
	
	public OrderBean doRetrieve(String user, int status) throws SQLException;
	
	public OrderBean doRetrieveByKey(int id) throws SQLException;
	
	public ArrayList<OrderBean> doRetrieveAll(String organize) throws SQLException;
	
	public void doUpdateCost(int id, double cost) throws SQLException;
}