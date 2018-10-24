package app.backingBeans;

import app.dto.ProductDTO;
import app.services.ProductService;
import org.apache.log4j.Logger;
import org.richfaces.application.push.MessageException;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@ManagedBean(name = "stand")
@ApplicationScoped
public class StandBeanImpl implements StandBean {

    private static final Logger logger = Logger.getLogger(StandBeanImpl.class);

    private final String CDI_PUSH_TOPIC = "pushCdi";
    private final TopicKey topicKey = new TopicKey(CDI_PUSH_TOPIC);
    private TopicsContext topicsContext;

    @Inject
    private ProductService productService;

    private List<ProductDTO> productDTOS;

    public String getCDI_PUSH_TOPIC() {
        return CDI_PUSH_TOPIC;
    }

    public List<ProductDTO> getProductDTOS() {
        return productDTOS;
    }

    public void setProductDTOS(List<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }

    @PostConstruct
    private void initStorage() {
        productDTOS = new ArrayList<>();
        productDTOS.addAll(productService.getProducts());

        topicsContext = TopicsContext.lookup();
        topicsContext.getOrCreateTopic(topicKey);
        logger.info("Successfully Init Storage, size " + productDTOS.size());
    }

    @Override
    public void updateProductList(List<ProductDTO> productDTO) {
        productDTOS = productDTO;
        logger.info("Storage Successfully Updated!");
        sendUpdate();
    }

    private void sendUpdate() {
        try {
            topicsContext.publish(topicKey, productDTOS);
        } catch (MessageException ex) {
            logger.debug("Cannot send update!");
        }
    }

}
