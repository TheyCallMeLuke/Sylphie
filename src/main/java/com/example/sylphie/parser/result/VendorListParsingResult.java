package com.example.sylphie.parser.result;

import com.example.sylphie.parser.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class VendorListParsingResult {

    private List<String> shopUrls;

    public boolean isLastResult() {
        return shopUrls.size() < Constants.MAX_NUMBER_OF_SHOPS_PER_PAGE;
    }
}
