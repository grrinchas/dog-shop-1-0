import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "orderStatus")
public class OrderStatus implements Serializable {
    @Id
    @Column(name = "STATUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "status")
    private List<Order> orders;

    public OrderStatus() {
    }

    public OrderStatus(int id) {
        this.setId(id);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
