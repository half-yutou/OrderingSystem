package System.service;

import System.dao.DinningTableDAO;
import System.domain.DinningTable;

import java.util.List;

public class DinningTableService {
    private DinningTableDAO dinningTableDAO = new DinningTableDAO();

    //返回所有餐桌信息
    public List<DinningTable> list() {
        String sql = "select id, state from diningTable";
        return dinningTableDAO.queryMul(sql, DinningTable.class);
    }

    //根据Id查询对应的餐桌，返回空表示对应餐桌不存在
    public DinningTable getDiningTableById(int id) {
        String sql = "select * from diningTable where id = ?";
        return dinningTableDAO.querySingle(sql, DinningTable.class, id);
    }

    //如果餐桌可以预定，调用方法对其状态进行更新
    public boolean orderingTable(int id, String orderName, String orderTel) {
        String sql = "update diningTable set state = '已预定', orderName = ?, orderTel = ? where id = ?";
        int affectedRow =
                dinningTableDAO.update(sql,orderName, orderTel, id);
        return affectedRow > 0;
    }

    //更新餐桌状态的方法
    public boolean updateTableState(int id, String state) {
        String sql = "update diningTable set state = ? where id = ?";
        int update = dinningTableDAO.update(sql, state, id);
        return update > 0;
    }

    //更新餐桌状态为空状态
    public boolean updateTableStateNull(int id, String state) {
        String sql = "update diningTable set state = ?, orderName = '', orderTel = '' where id = ?";
        int update = dinningTableDAO.update(sql, state, id);
        return update > 0;
    }
}
