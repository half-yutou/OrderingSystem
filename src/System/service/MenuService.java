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
}
