package app.webServices;

import app.dto.ProductDTO;

import java.util.List;

public interface WebService {
    List<ProductDTO> getProducts();

}
