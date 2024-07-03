package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDaoInterface {

	public void doSave(ItemBean item) throws SQLException;
	
	public ItemBean doRetrieve(String opera) throws SQLException;
	
	public ArrayList<ItemBean> doRetrieveAll(String order) throws SQLException;
}