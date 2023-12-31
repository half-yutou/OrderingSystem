package System.view;

import System.dao.DinningTableDAO;
import System.domain.Bill;
import System.domain.DinningTable;
import System.domain.Employee;
import System.domain.Menu;
import System.service.BillService;
import System.service.DinningTableService;
import System.service.EmployeeService;
import System.service.MenuService;
import System.utils.Utility;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.Test;

import java.util.List;

@SuppressWarnings({"all"})
public class MainView {
    //控制变量
    private boolean loop = true;
    private String key = "";

    //service变量
    private EmployeeService employeeService = new EmployeeService();
    private DinningTableService dinningTableService = new DinningTableService();
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();

    //显示餐桌状态
    public void listTableState() {
        System.out.println("餐桌编号\t餐桌状态");
        List<DinningTable> list = dinningTableService.list();
        for (DinningTable dinningTable : list) {
            System.out.println(dinningTable);
        }
        System.out.println("显示完毕");
    }

    //预定餐桌
    public void orderingTable() {
        System.out.println("========预定餐桌========");
        System.out.println("请选择要预定的餐桌的编号(-1退出)");
        int orderId = Utility.readInt();
        if (orderId == -1) return;

        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            //根据orderId返回对应餐桌，如果返回空说明餐桌不存在
            DinningTable dinningTable = dinningTableService.getDiningTableById(orderId);
            if (dinningTable == null) {
                System.out.println("========餐桌不存在========");
                return;
            }

            if (!("空".equals(dinningTable.getState()))) {
                System.out.println("========该餐桌已被预定========");
                return;
            }

            //经过上述判断，确认了能够预定该餐桌
            //开始收集预定人信息，并更改餐桌状态
            System.out.println("请输入姓名：");
            String orderName = Utility.readString(50);
            System.out.println("请输入电话：");
            String orderTel = Utility.readString(50);
            if (dinningTableService.orderingTable(orderId, orderName,orderTel)) {
                System.out.println("========预定成功========");
            } else {
                System.out.println("=========预定失败========");
            }

        } else {
            System.out.println("========取消预定========");
        }
    }

    //显示菜品
    public void listMenu() {
        System.out.println("\n菜品编号\t菜品名\t类别\t价格");
        List<Menu> list = menuService.list();
        for (Menu menu : list) {
            System.out.println(menu);
        }
        System.out.println("显示完毕");
    }

    //完成点餐
    public void orderServe() {
        System.out.println("========点餐服务========");
        System.out.println("请输入点餐的餐桌号(-1取消):");
        int orderTableId = Utility.readInt();
        if (orderTableId == -1) {
            System.out.println("========您已取消点餐========");
            return;
        }
        System.out.println("请输入点餐的菜品号(-1取消):");
        int orderMenuId = Utility.readInt();
        if (orderMenuId == -1) {
            System.out.println("========您已取消点餐========");
            return;
        }
        System.out.println("请输入点餐的菜品量(-1取消):");
        int orderNums = Utility.readInt();
        if (orderNums == -1) {
            System.out.println("========您已取消点餐========");
            return;
        }

        //开始验证
        DinningTable dinningTable = dinningTableService.getDiningTableById(orderTableId);
        if (dinningTable == null) {
            System.out.println("餐桌不存在！");
            return;
        }

        Menu menu = menuService.getMenuById(orderMenuId);
        if (menu == null) {
            System.out.println("餐品不存在！");
            return;
        }

        //验证成功，正式开始点菜
        if (billService.order(orderMenuId, orderNums, orderTableId)) {
            System.out.println("=========点餐成功========");
        } else {
            System.out.println("========点餐失败========");
        }
    }

    //显示账单信息
    public void listBill() {
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态");
        List<Bill> billList = billService.list();
        for (Bill bill : billList) {
            System.out.println(bill);
        }
        System.out.println("输出完毕");
    }

    //结账
    public void payBill() {
        System.out.println("========结账服务========");
        System.out.println("请选择要结账的餐桌编号(-1取消)");
        int TableId = Utility.readInt();
        if (TableId == -1) {
            System.out.println("========您已取消========");
            return;
        }
        DinningTable table = dinningTableService.getDiningTableById(TableId);
        //1.验证餐桌是否存在
        if (table == null) {
            System.out.println("========餐桌不存在=========");
            return;
        }

        //2.验证餐桌是否有账单需要支付
        if (!billService.hasNotPayByTableId(TableId)) {
            System.out.println("========该餐桌无账单========");
            return;
        }

        System.out.println("结账方式(crash/wechat/AliPay/ApplePay)[回车退出]");
        String payMode = Utility.readString(10, "");
        if (payMode.equals("")) {
            System.out.println("========取消结账========");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            if (billService.payBill(TableId, payMode)) {
                System.out.println("结账成功");
            } else {
                System.out.println("结账失败");
            }
        } else {
            System.out.println("========取消结账========");
        }


    }

    //主菜单
    public void mainMenu() {//一级菜单
        while(loop) {
            System.out.println("========订餐系统========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 2 退出系统");
            System.out.println("请输入你的选择:");
            key = Utility.readString(1);

            switch (key) {
                case "1" :
                    System.out.println("请输入账号");
                    String empId = Utility.readString(50);
                    System.out.println("请输入密码");
                    String pwd = Utility.readString(50);

                    Employee employee = employeeService.getEmployeeByIdAndPWD(empId, pwd);
                    //此处需要去数据库检查确认
                    if (employee != null) {
                        System.out.println(employee.getName() + "登录成功");

                        while (loop) {
                            System.out.println("========二级菜单========");
                            System.out.println("\t\t 1 显示餐桌状态");
                            System.out.println("\t\t 2 预定餐桌");
                            System.out.println("\t\t 3 显示所有菜品");
                            System.out.println("\t\t 4 点餐服务");
                            System.out.println("\t\t 5 查看账单");
                            System.out.println("\t\t 6 结账");
                            System.out.println("\t\t 9 退出");
                            key = Utility.readString(1);

                            switch (key) {
                                case "1" :
                                    listTableState();
                                    break;
                                case "2" :
                                    orderingTable();
                                    break;
                                case "3" :
                                    listMenu();
                                    break;
                                case "4" :
                                    orderServe();
                                    break;
                                case "5" :
                                    listBill();
                                    break;
                                case "6" :
                                    payBill();
                                    break;
                                case "9" :
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入有误，请重新输入");
                                    break;
                            }

                        }

                    } else {
                        System.out.println("登录失败，请检查账号密码");
                    }

                    break;

                case "2" :
                    loop = false;
                    break;

                default:
                    System.out.println("输入有误，请重新输入");
            }
        }
        System.out.println("您已退出系统");
    }


}
