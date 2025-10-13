package br.com.postech.soat.payment.application.gateway;

public class GatewayOperationResult {
    private final boolean success;
    private final String paymentUrl;

    private GatewayOperationResult(boolean success, String paymentUrl) {
        this.success = success;
        this.paymentUrl = paymentUrl;
    }

    public static GatewayOperationResult success(String paymentUrl) {
        return new GatewayOperationResult(true, paymentUrl);
    }

    public static GatewayOperationResult failure() {
        return new GatewayOperationResult(false, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GatewayOperationResult that = (GatewayOperationResult) obj;
        return success == that.success;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(success);
    }
}
