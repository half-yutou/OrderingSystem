package System.domain;

public class DinningTable {
    private Integer Id;
    private String state;
    private String orderName;
    private String orderTel;

    public DinningTable() {
    }

    public DinningTable(Integer id, String state, String orderName, String orderTel) {
        Id = id;
        this.state = state;
        this.orderName = orderName;
        this.orderTel = orderTel;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderTel() {
        return orderTel;
    }

    public void setOrderTel(String orderTel) {
        this.orderTel = orderTel;
    }

    @Override
    public String toString() {
        return Id + "\t\t" + state;
    }
}
