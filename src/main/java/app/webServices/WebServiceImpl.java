package app.webServices;


import app.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;


@Singleton
@ApplicationScoped
public class WebServiceImpl implements WebService {
    private final String GET_ALL_URL = "http://localhost:8080/jsDonut/rest/getTop10Products";
    private final int STATUS_OK = 200;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<ProductDTO> getProducts() {
        WebResource webResource = getWebResource(GET_ALL_URL);
        ClientResponse response = queryGet(webResource);
        List<ProductDTO> entityList = Collections.emptyList();
        try {
            if (response.getStatus() == STATUS_OK) {
                entityList = mapper.readValue(response.getEntity(String.class),
                        mapper.getTypeFactory().constructCollectionType(List.class, ProductDTO.class));
            }
        } catch (Exception ex) {

        }
        return entityList;
    }

    /**
     * Construct web resource
     *
     * @return - web resource
     */
    private WebResource getWebResource(String resource) {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);

        return client.resource(resource);

    }

    protected ClientResponse queryGet(WebResource webResource) {
        return webResource.type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
    }
}
