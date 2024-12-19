package com.example.hotelserver.controllers.payment;
import com.example.hotelserver.services.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/checkout")
    public @ResponseBody Map<String, String> redirectToCheckout(@RequestParam Long amount,
                                                                @RequestParam String name,
                                                                RedirectAttributes redirectAttributes) throws Exception {
        String checkoutSessionUrl = paymentService.createCheckoutSession(amount, name);
        Map<String, String> map = new HashMap<>();
        map.put("data", checkoutSessionUrl);
        return map;
    }

    @GetMapping("/success")
    public String successPaymentPage(){
        return "success";
    }

    @GetMapping("/cancel")
    public String cancelPaymentPage(){
        return "cancel";
    }
}
