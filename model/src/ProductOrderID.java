import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ProductOrderID implements Serializable {
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return this.order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        ProductOrderID that = (ProductOrderID) o;

        if (this.product.getId() != that.product.getId())
            return false;
        return this.order.getId() == that.order.getId();

    }

    @Override
    public int hashCode() {
        int result = this.product.getId();
        result = 31 * result + this.order.getId();
        return result;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
