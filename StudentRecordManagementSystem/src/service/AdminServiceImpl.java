package service;

import dao.AdminDAO;
import dao.AdminDAOImpl;
import entities.Admin;
import exceptions.DatabaseException;
public class AdminServiceImpl implements AdminService{
	
	AdminDAO adminDAO = new AdminDAOImpl();

	@Override
	public boolean login(Admin admin) {
		return adminDAO.login(admin);	
	}

	@Override
	public Admin register(Admin admin) throws DatabaseException {
		return adminDAO.register(admin);
	}

}
