package ru.gds.spring.mongo.dto;

import lombok.Data;
import java.util.List;

@Data
public class ParamsDto {

    private List<String> orders;

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
