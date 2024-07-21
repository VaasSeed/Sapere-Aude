package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AudioBookDaoInterface {

	public void doSave(AudioBookBean audioBook) throws SQLException;
	
	public AudioBookBean doRetrieve(String ISBNOpera) throws SQLException;
	
	public ArrayList<AudioBookBean> doRetrieveAll(String order) throws SQLException;
	
	public void doUpdate(String isbn, AudioBookBean updated) throws SQLException;
	
	public void doDelete(String isbn) throws SQLException;
}