package com.example.sylphie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Item {
    private final long id;
    private final String name;
}
