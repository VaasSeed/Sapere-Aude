package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DigitalBookDaoInterface {

	public void doSave(DigitalBookBean digitalBook) throws SQLException;
	
	public DigitalBookBean doRetrieve(String ISBNOpera) throws SQLException;
	
	public ArrayList<DigitalBookBean> doRetrieveAll(String order) throws SQLException;
	
	public void doUpdate(String isbn, DigitalBookBean updated) throws SQLException;

	public void doDelete(String isbn) throws SQLException;
}