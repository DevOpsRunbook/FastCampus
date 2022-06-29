package co.fastcampus.api.composite.product;

import java.util.List;

public class ProductAggregate {
    private final int productId;
    private final String name;
    private final int weight;
    private final ServiceAddresses serviceAddresses;

    public ProductAggregate() {
        productId = 0;
        name = null;
        weight = 0;
        serviceAddresses = null;
    }

    public ProductAggregate(
        int productId,
        String name,
        int weight,
        ServiceAddresses serviceAddresses) {

        this.productId = productId;
        this.name = name;
        this.weight = weight;
        this.serviceAddresses = serviceAddresses;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public ServiceAddresses getServiceAddresses() {
        return serviceAddresses;
    }
}
