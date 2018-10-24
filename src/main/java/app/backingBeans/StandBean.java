package app.backingBeans;

import app.dto.ProductDTO;

import java.util.List;

public interface StandBean {
    void updateProductList(List<ProductDTO> productDTO);
}
