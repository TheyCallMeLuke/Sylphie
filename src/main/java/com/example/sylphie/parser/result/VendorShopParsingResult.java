package com.example.sylphie.parser.result;

import com.example.sylphie.component.VendingItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class VendorShopParsingResult {
    private final List<VendingItem> vendingItems;
}
