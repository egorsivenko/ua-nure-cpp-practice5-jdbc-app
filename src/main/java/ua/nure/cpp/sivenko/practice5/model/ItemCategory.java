package ua.nure.cpp.sivenko.practice5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemCategory {
    private long itemCategoryId;
    private String itemCategoryName;
    private List<Pawnbroker> activePawnbrokers;
}
