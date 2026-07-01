package com.bank.payment;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentRequest {
    private String request_id;
    private String loan_id;
    private String terminal_id;
    private String merchant_id;
    private String nspc_trans_id;
    private String amount;
    private String commission_amount;
    private String payment_type;
    private String card_type;
    private String authorization_code;
    private String payment_date;
    private String operation_number;

    public PaymentRequest() {} 

    public String getRequest_id() { return request_id; }
    public void setRequest_id(String request_id) { this.request_id = request_id; }

    public String getLoan_id() { return loan_id; }
    public void setLoan_id(String loan_id) { this.loan_id = loan_id; }

    public String getTerminal_id() { return terminal_id; }
    public void setTerminal_id(String terminal_id) { this.terminal_id = terminal_id; }

    public String getMerchant_id() { return merchant_id; }
    public void setMerchant_id(String merchant_id) { this.merchant_id = merchant_id; }

    public String getNspc_trans_id() { return nspc_trans_id; }
    public void setNspc_trans_id(String nspc_trans_id) { this.nspc_trans_id = nspc_trans_id; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getCommission_amount() { return commission_amount; }
    public void setCommission_amount(String commission_amount) { this.commission_amount = commission_amount; }

    public String getPayment_type() { return payment_type; }
    public void setPayment_type(String payment_type) { this.payment_type = payment_type; }

    public String getCard_type() { return card_type; }
    public void setCard_type(String card_type) { this.card_type = card_type; }

    public String getAuthorization_code() { return authorization_code; }
    public void setAuthorization_code(String authorization_code) { this.authorization_code = authorization_code; }

    public String getPayment_date() { return payment_date; }
    public void setPayment_date(String payment_date) { this.payment_date = payment_date; }

    public String getOperation_number() { return operation_number; }
    public void setOperation_number(String operation_number) { this.operation_number = operation_number; }
}