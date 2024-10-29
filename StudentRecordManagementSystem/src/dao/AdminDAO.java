package dao;

import entities.Admin;
import exceptions.DatabaseException;

public interface AdminDAO {
	public boolean login(Admin admin);	
	public Admin register(Admin admin) throws DatabaseException;
}
