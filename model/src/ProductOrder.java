import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity(name = "productOrder")
public class ProductOrder implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    @EmbeddedId
    private ProductOrderID id = new ProductOrderID();
    @Column(name = "QUANTITY")
    private int quantity;


    public int getQuantity() { return this.quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductOrderID getId() {
        return this.id;
    }

    public void setId(ProductOrderID id) {
        this.id = id;
    }

    @Transient
    public Product getProduct() {
        return this.id.getProduct();
    }

    public void setProduct(Product product) {
        this.id.setProduct(product);
    }

    @Transient
    public Order getOrder() {
        return this.id.getOrder();
    }

    public void setOrder(Order order) {
        this.id.setOrder(order);
    }
}
