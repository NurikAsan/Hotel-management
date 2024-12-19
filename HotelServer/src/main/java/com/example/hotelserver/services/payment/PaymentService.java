package com.example.hotelserver.services.payment;

import com.stripe.Stripe;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;


@Service
public class PaymentService {
    @Value("${stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        if (apiKey != null && !apiKey.isEmpty())
            Stripe.apiKey = apiKey;
        else
            throw new IllegalArgumentException("API key for Stripe is not set.");
    }

    public String createCheckoutSession(Long amount, String name) throws Exception {
        ProductCreateParams data = ProductCreateParams.builder()
                .setName(name)
                .setDescription("Description of " + name)
                .build();

        var product = Product.create(data);

        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setUnitAmount(amount * 100)
                .setCurrency("usd")
                .setProduct(product.getId())
                .build();

        var price = Price.create(priceParams);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/api/v1/payment/success")
                .setCancelUrl("http://localhost:8080/api/v1/payment/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
//                                .setPrice("price_1PAlHd2MdXOh3Erwy4E3PL9J")
                                .setPrice(price.getId())
                                .build()
                )
                .build();

        Session session = Session.create(params);

        return session.getUrl();

    }
}
