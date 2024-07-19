package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BuyDigitalBookDaoInterface {

	public void doSave(BuyDigitalBookBean buyDigitalBook) throws SQLException;
	
	public BuyDigitalBookBean doRetrieve(String ISBN, int order) throws SQLException;
	
	public ArrayList<BuyDigitalBookBean> doRetrieveAll(int order, String sort) throws SQLException;
	
	public void doDelete(int id) throws SQLException;
	
	public Boolean alreadySaved(String ISBN, int order, String type) throws SQLException;
	
	public void updateDate(int id) throws SQLException;
}