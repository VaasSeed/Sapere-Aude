package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RentDigitalBookDaoInterface {

	public void doSave(RentDigitalBookBean rentDigitalBook) throws SQLException;
	
	public RentDigitalBookBean doRetrieve(String ISBN, int order) throws SQLException;
	
	public ArrayList<RentDigitalBookBean> doRetrieveAll(int order, String sort) throws SQLException;
	
	public void doDelete(int id) throws SQLException;
	
	public Boolean alreadySaved(String ISBN, int order, String type) throws SQLException;
}