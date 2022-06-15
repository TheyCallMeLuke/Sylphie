package com.example.sylphie.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class VendingItem {

    private final Item item;
    private final long price;
    private final int amount;

    public long getId() {
        return item.getId();
    }
}
