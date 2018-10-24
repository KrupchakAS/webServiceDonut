package app.messages;


import app.backingBeans.StandBean;
import app.services.ProductService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "Listener", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/DLQ"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class Listener implements MessageListener {
    private static final Logger logger = LogManager.getLogger(Listener.class);

    @Inject
    private StandBean standBean;
    @Inject
    private ProductService productService;

    @Override
    public void onMessage(Message message) {
        try {
            if(message.getBody(String.class).equals("Update")){
            standBean.updateProductList(productService.getProducts());
            }
        } catch (JMSException e) {
            logger.error("Catch Exception");
        }
    }


}
