package models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CreditCardDaoInterface {

	public void doSave(CreditCardBean card) throws SQLException;
	
	public CreditCardBean doRetrieve(String cardNumber) throws SQLException;
	
	public void doDelete(String cardNumber) throws SQLException;
	
	public ArrayList<CreditCardBean> doRetrieveAll(String account, String sort) throws SQLException;
}