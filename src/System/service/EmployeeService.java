package System.service;

import System.dao.EmployeeDAO;
import System.domain.Employee;

/*
该类通过调用EmployeeDAO来完成Employee表操作
 */
public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    //根据empId和pwd返回一个Employee对象
    public Employee getEmployeeByIdAndPWD(String empId, String pwd) {
        String sql = "select * from employee where empId = ? and pwd = md5(?)";
        return employeeDAO.querySingle(sql, Employee.class, empId, pwd);
    }
}
