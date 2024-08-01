package com.internal.codegen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassSpecification {
    private String name;
    private List<FieldSpecification> fieldSpecifications;

}
