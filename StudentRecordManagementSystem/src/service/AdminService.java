package service;

import entities.Admin;
import exceptions.DatabaseException;

public interface AdminService {

	public boolean login(Admin admin);
	public Admin register(Admin admin) throws DatabaseException;
}
