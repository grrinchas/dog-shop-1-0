import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Stateless
@Path("/")
public class ProductService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Event<Order> event;

    @GET
    @Path("/products/{id}")
    @Produces("application/json")
    public Product findById(@PathParam("id") int id) {
        return this.em.find(Product.class, id);
    }

    @GET
    @Path("/products")
    @Produces("application/json")
    public List<Product> findAll() {
        return this.em.createQuery("SELECT e FROM products e", Product.class).getResultList();
    }

    @PUT
    @Path("/orders")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response removeFromStock(Map<Integer, Integer> orders) throws Exception {

        try {
            Order order = new Order();
            order.setStatus(this.em.find(OrderStatus.class, 1));
            order.setDateTime(new Date());
            this.em.persist(order);

            for (Integer id : orders.keySet()) {
                Product product = this.findById(id);
                int qty = orders.get(id);
                int inStock = product.getStock();
                if (qty <= inStock) {
                    product.setStock(product.getStock() - orders.get(id));
                } else {
                    throw new Exception("Stock is too low");
                }
                ProductOrder productOrder = new ProductOrder();
                productOrder.setProduct(product);
                productOrder.setOrder(order);
                productOrder.setQuantity(qty);
                this.em.persist(productOrder);
            }
            this.event.fire(order);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    public void updateOrderStatus(int id, int orderStatus) {
        Order order = this.em.find(Order.class, id);
        order.setStatus(this.em.find(OrderStatus.class, orderStatus));
        this.em.merge(order);
        this.event.fire(order);
    }
}
