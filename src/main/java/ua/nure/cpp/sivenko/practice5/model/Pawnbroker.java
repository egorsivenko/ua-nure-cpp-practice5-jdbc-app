package ua.nure.cpp.sivenko.practice5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pawnbroker {
    private long pawnbrokerId;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String contactNumber;
    private String email;
    private String address;
    private List<ItemCategory> specializations;
}
