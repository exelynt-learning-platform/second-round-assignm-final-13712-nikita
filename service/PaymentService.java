package com.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Order;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.OrderRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;


@Service
public class PaymentService {
	@Autowired
	public APIContext apicontext;
	@Autowired
	public OrderRepository orderRepo;
	@Autowired
	public OrderService orderservice;
	
	public String createPayment(Order order) {
		Amount amount=new Amount();
		amount.setCurrency("USD");
		amount.setTotal(String.valueOf(order.getTotalPrice()));
		
		Transaction transaction=new Transaction();
		transaction.setDescription("Order Payment");
		transaction.setAmount(amount);
		
		List<Transaction> transactions=new ArrayList<>();
		transactions.add(transaction);
		
		Payer payer=new Payer();
		payer.setPaymentMethod("Paypal");
		
		Payment payment= new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		
		RedirectUrls redirecturls=new RedirectUrls();
		redirecturls.setCancelUrl("http://localhost:8080/payment/cancel");
		redirecturls.setReturnUrl("http://localhost:8080/payment/success");
		
		payment.setRedirectUrls(redirecturls);
		try {
			Payment created=payment.create(apicontext);
			for(Links link:created.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return link.getHref();
				}
				
			}
			
			
		} catch (Exception e) {
			throw new RuntimeException("Payment creation failed");
		}
		
        return null;
    }

  
    public String executePayment(String paymentId, String payerId,Long orderId) {
    	Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId(payerId);

        try {       
            Payment executed=payment.execute(apicontext, execution);
            if(executed.getState().equals("approved")) {
            	orderservice.updatePaymentStatus(orderId, "SUCCESS");
            	return "Payment success";
            }

        } catch (Exception e) {
            throw new RuntimeException("Payment execution failed");
        }
        orderservice.updatePaymentStatus(orderId, "FAILED");
        return "Payment failed";
        
    }
    public String markPaymentSuccess(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentstatus("PAID");

        orderRepo.save(order);

        return "Payment successful and order updated!";
    }

}
