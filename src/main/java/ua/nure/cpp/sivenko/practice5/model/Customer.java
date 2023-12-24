package ua.nure.cpp.sivenko.practice5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private long customerId;
    private String firstname;
    private String lastname;
    private String contactNumber;
    private String email;
}
