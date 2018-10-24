package app.services;

import app.dto.ProductDTO;
import app.webServices.WebService;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@Singleton
@ApplicationScoped
public class ProductServiceImpl implements ProductService{

    @Inject
    WebService webService;

    @Override
    public List<ProductDTO> getProducts() {
        return webService.getProducts();
    }
}
