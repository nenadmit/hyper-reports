package report.engine;

public class Report {

    private String storeName;
    private double total;
    private int receiptCounter;
    private int invoiceCounter;
    private int paymentByCardCounter;
    private int paymentByCashCounter;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getReceiptCounter() {
        return receiptCounter;
    }

    public void setReceiptCounter(int receiptCounter) {
        this.receiptCounter = receiptCounter;
    }

    public int getInvoiceCounter() {
        return invoiceCounter;
    }

    public void setInvoiceCounter(int invoiceCounter) {
        this.invoiceCounter = invoiceCounter;
    }

    public int getPaymentByCardCounter() {
        return paymentByCardCounter;
    }

    public void setPaymentByCardCounter(int paymentByCardCounter) {
        this.paymentByCardCounter = paymentByCardCounter;
    }

    public int getPaymentByCashCounter() {
        return paymentByCashCounter;
    }

    public void setPaymentByCashCounter(int paymentByCashCounter) {
        this.paymentByCashCounter = paymentByCashCounter;
    }
}
