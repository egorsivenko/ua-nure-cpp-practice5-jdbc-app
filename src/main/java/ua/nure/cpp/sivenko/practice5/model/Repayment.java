package ua.nure.cpp.sivenko.practice5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Repayment {
    private long repaymentId;
    private long transactionId;
    private long paymentMethod;
    private LocalDateTime repaymentDate;
}
