package ua.nure.cpp.sivenko.practice5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentMethod {
    private long paymentMethodId;
    private String paymentMethodName;
}
