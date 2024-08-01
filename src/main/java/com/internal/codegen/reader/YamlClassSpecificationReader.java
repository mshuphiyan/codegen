package com.internal.codegen.reader;

import com.internal.codegen.model.ClassSpecification;
import com.internal.codegen.model.FieldSpecification;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class YamlClassSpecificationReader {
    public List<ClassSpecification> read(File yamlFile) throws FileNotFoundException {
        return read(new FileReader(yamlFile));
    }

    public List<ClassSpecification> read(Reader reader) {
        Map<String, Map<String, String>> yamlClassSpecifications = readYamlClassSpecifications(reader);
        return createClassSpecificationsFrom(yamlClassSpecifications);
    }

    private Map<String, Map<String, String>> readYamlClassSpecifications(Reader reader) {
        Yaml yaml = new Yaml();

        // Read in the complete YAML file to a map of strings to a map of strings to strings

        return yaml.load(reader);
    }

    private List<ClassSpecification> createClassSpecificationsFrom(Map<String, Map<String, String>> yamlClassSpecifications) {
        final Map<String, List<FieldSpecification>> classNameToFieldSpecificationsMap
                = createClassNameToFieldSpecificationsMap(yamlClassSpecifications);

        List<ClassSpecification> classSpecifications =
                classNameToFieldSpecificationsMap.entrySet().stream()
                        .map(e -> new ClassSpecification(e.getKey(), e.getValue()))
                        .collect(toList());

        return classSpecifications;
    }

    private Map<String, List<FieldSpecification>> createClassNameToFieldSpecificationsMap(
            Map<String, Map<String, String>> yamlClassSpecifications) {

        if (yamlClassSpecifications == null) return new HashMap<>();

        return yamlClassSpecifications.entrySet().stream()
                .collect(toMap(this::className, this::fieldSpecifications));
    }

    private String className(Map.Entry<String, Map<String, String>> yamlOuterMapEntry) {
        return yamlOuterMapEntry.getKey();
    }

    private List<FieldSpecification> fieldSpecifications(Map.Entry<String, Map<String, String>> yamlOuterMapEntry) {
        Map<String, String> yamlFieldSpecifications = yamlOuterMapEntry.getValue();

        if (yamlFieldSpecifications == null) return new ArrayList<>();

        return yamlFieldSpecifications.entrySet().stream()
                .map(e -> new FieldSpecification(e.getKey(), e.getValue()))
                .collect(toList());
    }
}
