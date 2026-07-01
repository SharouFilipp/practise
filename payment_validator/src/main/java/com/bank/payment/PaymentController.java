package com.bank.payment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/payment")
public class PaymentController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processPayment(PaymentRequest req) {
        if (isEmpty(req.getRequest_id())) return error("fields request_id is empty");
        if (isEmpty(req.getLoan_id())) return error("fields loan_id is empty");
        if (isEmpty(req.getTerminal_id())) return error("fields terminal_id is empty");
        if (isEmpty(req.getMerchant_id())) return error("fields merchant_id is empty");
        if (isEmpty(req.getNspc_trans_id())) return error("fields nspc_trans_id is empty");
        if (isEmpty(req.getAmount())) return error("fields amount is empty");
        if (isEmpty(req.getCommission_amount())) return error("fields commission_amount is empty");
        if (isEmpty(req.getPayment_type())) return error("fields payment_type is empty");
        if (isEmpty(req.getCard_type())) return error("fields card_type is empty");
        if (isEmpty(req.getAuthorization_code())) return error("fields authorization_code is empty");
        if (isEmpty(req.getPayment_date())) return error("fields payment_date is empty");
        if (isEmpty(req.getOperation_number())) return error("fields operation_number is empty");

        if (!req.getAmount().matches("\\d+")) {
            return error("field amount must contain only digits");
        }

        return Response.ok(new Status("0", "OK")).build();
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private Response error(String message) {
        return Response.ok(new Status("1", message)).build();
    }

    public static class Status {
        public String Error;
        public String MSG;

        public Status(String error, String msg) {
            this.Error = error;
            this.MSG = msg;
        }
    }
}