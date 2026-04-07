package com.trading.g1m5.dto;

import com.trading.g1m5.enums.OrderSide;
import com.trading.g1m5.enums.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OrderSnapshot {

    @NotBlank
    private String orderId;

    @NotBlank
    private String clientId;

    @NotBlank
    private String symbol;

    @NotNull
    private OrderSide side;

    @Min(1)
    private long quantity;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull
    private OrderStatus status;

    public OrderSnapshot() {
    }

    public OrderSnapshot(String orderId, String clientId, String symbol, OrderSide side, long quantity, BigDecimal price, OrderStatus status) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.symbol = symbol;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
