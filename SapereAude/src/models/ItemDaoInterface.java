package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDaoInterface {

	public void doSave(ItemBean item) throws SQLException;
	
	public ItemBean doRetrieve(String ISBN) throws SQLException;
	
	public ArrayList<ItemBean> doRetrieveAll(String order) throws SQLException;
	
	public void doUpdate(String isbn, ItemBean updated) throws SQLException;
	
	public void doDelete(String isbn) throws SQLException;
}