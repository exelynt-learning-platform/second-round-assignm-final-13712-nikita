package com.ecommerce.service;

import java.util.ArrayList;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
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
	@Value("${paypal.success.url}")
	private String successUrl;

	@Value("${paypal.cancel.url}")
	private String cancelUrl;
	
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
		payer.setPaymentMethod("paypal");
		
		Payment payment= new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		
		RedirectUrls redirecturls=new RedirectUrls();
		redirecturls.setCancelUrl(cancelUrl);
		redirecturls.setReturnUrl(successUrl);
		
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

    public String markPaymentSuccess(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentstatus("PAID");

        orderRepo.save(order);

        return "Payment successful and order updated!";
    }

}
