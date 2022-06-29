package co.fastcampus.api.composite.product;

public class ServiceAddresses {
    private final String cmp;
    private final String pro;

    public ServiceAddresses() {
        cmp = null;
        pro = null;
    }

    public ServiceAddresses(String compositeAddress, String productAddress) {
        this.cmp = compositeAddress;
        this.pro = productAddress;
    }

    public String getCmp() {
        return cmp;
    }

    public String getPro() {
        return pro;
    }

}
