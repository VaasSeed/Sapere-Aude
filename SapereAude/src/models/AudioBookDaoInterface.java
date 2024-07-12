package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AudioBookDaoInterface {

	public void doSave(AudioBookBean audioBook) throws SQLException;
	
	public AudioBookBean doRetrieve(String ISBNOpera) throws SQLException;
	
	public ArrayList<AudioBookBean> doRetrieveAll(String order) throws SQLException;
}