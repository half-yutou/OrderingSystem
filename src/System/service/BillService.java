package System.service;

import System.dao.BillDao;

import javax.swing.*;
import java.util.UUID;

public class BillService {
    private BillDao billDao = new BillDao();
    private MenuService menuService = new MenuService();
    private DinningTableService dinningTableService = new DinningTableService();
    //点餐方法
    //1.生成账单
    public boolean order(int menuId, int nums, int diningTableId) {
        //UUID方法生成一个随机不重复的账单号
        String billId = UUID.randomUUID().toString();
        //将账单生成到bill表
        String sql = "insert into bill values(null, ?, ?, ?, ?, ?, now(), '未结账')";
        int update =
                billDao.update(sql, billId, menuId, nums,
                        menuService.getMenuById(menuId).getPrice() * nums, diningTableId);
        if (update <= 0) {
            return false;
        }

        return dinningTableService.updateTableState(diningTableId, "就餐中");

    }

    //更新餐桌状态




}
