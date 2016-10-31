import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/orders")
@ApplicationScoped
public class OrderEndPoint {
    private final static Set<Session> clients = new HashSet<>();

    @Inject
    private ProductService productService;

    @OnOpen
    public void open(Session session) {
        this.clients.add(session);
    }

    @OnClose
    public void close(Session session) {
        this.clients.remove(session);
    }

    @OnError
    public void onError(Throwable error) {

    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        if (message.contains("Collected")) {
            this.productService.updateOrderStatus(Integer.parseInt(message.replace("Collected", "")), 2);
        } else if (message.contains("Completed")) {
            this.productService.updateOrderStatus(Integer.parseInt(message.replace("Completed", "")), 3);
        }
    }

    public void notifyClients(@Observes Order order) {
        String json = "{\"id\": " + order.getId() + ", \"status\": \"" + order.getStatus().getName() + "\"}";
        this.clients.forEach(s -> s.getAsyncRemote().sendText(json));
    }
}