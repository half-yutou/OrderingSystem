package System.service;

import System.dao.BillDao;
import System.domain.Bill;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDao billDao = new BillDao();
    private MenuService menuService = new MenuService();
    private DinningTableService dinningTableService = new DinningTableService();

    //点餐方法
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

    //返回所有账单
    public List<Bill> list() {
        String sql = "select * from bill";
        return billDao.queryMul(sql, Bill.class);
    }

    //查看某餐桌是否有未结账的账单
    public boolean hasNotPayByTableId(int TableId) {
        String sql = "select * from bill where diningTableId = ? and state = '未结账' limit 0,1";
        Bill bill =  billDao.querySingle(sql, Bill.class, TableId);
        return bill != null;
    }

    //完成结账(如果餐桌存在且有未结账的账单)
    public boolean payBill(int TableId, String payMode) {
        //1.修改bill表
        String sql = "update bill set state = ? where diningTableId = ? and state = '未结账'";
        int update = billDao.update(sql, payMode, TableId);
        if (update <= 0) {
            System.out.println("========支付失败========");
            return false;
        }

        //2.修改dinningTable表(调用dinningTableService)
        if (!dinningTableService.updateTableStateNull(TableId, "空")) {
            return false;
        }

        return true;
    }



}
