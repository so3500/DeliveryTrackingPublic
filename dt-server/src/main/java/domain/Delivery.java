package domain;

public class Delivery {

    private String invoiceNumber;  // 송장 번호
    private String deliveryCompany; // 택배 회사
    private Integer result;         // 배송 결과: 0 ro 1
    private String date;            // 날짜
    private String time;            // 시간
    private String currentState;    // 처리 현황
    private String currentPosition; // 현재 위치, 담당 점소
    private String sender;          // 보낸 사람
    private String receiver;        // 받는 사람

    public Delivery(String invoiceNumber, String deliveryCompany, Integer result, String date, String time,
                    String currentState, String currentPosition, String sender, String receiver) {
        this.invoiceNumber = invoiceNumber;
        this.deliveryCompany = deliveryCompany;
        this.result = result;
        this.date = date;
        this.time = time;
        this.currentState = currentState;
        this.currentPosition = currentPosition;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", deliveryCompany='" + deliveryCompany + '\'' +
                ", result=" + result +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", currentState='" + currentState + '\'' +
                ", currentPosition='" + currentPosition + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Delivery(){
    }
}
