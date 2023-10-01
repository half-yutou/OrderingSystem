package System.service;

import System.dao.MenuDao;
import System.domain.Menu;

import java.util.List;

public class MenuService {
    private MenuDao menuDao = new MenuDao();

    public List<Menu> list() {
        String sql = "select * from menu";
        return menuDao.queryMul(sql, Menu.class);
    }

    //需要一个方法，根据菜品id返回菜品
    public Menu getMenuById(int id) {
        String sql = "select * from menu where id = ?";
        return menuDao.querySingle(sql, Menu.class, id);
    }
}
