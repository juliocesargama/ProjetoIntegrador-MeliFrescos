package br.com.meli.PIFrescos.controller.dtos;

import br.com.meli.PIFrescos.models.Batch;
import br.com.meli.PIFrescos.models.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marcelo Leite
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO implements WarehouseQuantityDTO{
  private Integer warehouseCode;
  private Integer totalQuantity;

  public WarehouseDTO(Warehouse warehouse, List<Batch> batches) {
    this.warehouseCode = warehouse.getWarehouseCode();
    this.totalQuantity = sumQuantity(batches);
  }

  public static Integer sumQuantity(List<Batch> batches) {
    return batches.stream().mapToInt(Batch::getCurrentQuantity).sum();
  }

  public static List<WarehouseDTO> warehouseDTOList(List<Warehouse> warehouses, List<Batch> batches) {
    return warehouses.stream().map(warehouse -> new WarehouseDTO(warehouse, batches)).collect(Collectors.toList());
  }

  @Override
  public Integer getWarehousecode() {
    return warehouseCode;
  }

  @Override
  public BigInteger getTotalquantity() {
    return BigInteger.valueOf(totalQuantity);
  }
}
