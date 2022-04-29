package br.com.meli.PIFrescos.controller.dtos;

import br.com.meli.PIFrescos.models.Batch;
import br.com.meli.PIFrescos.models.Product;
import br.com.meli.PIFrescos.models.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWarehousesDTO {
  private Integer productId;
  private List<WarehouseDTO> warehouses;

  public ProductWarehousesDTO(Product product) {
    this.productId = product.getProductId();
  }
}