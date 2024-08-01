package com.internal.codegen;

import com.internal.codegen.generator.JavaClassGenerator;
import com.internal.codegen.model.ClassSpecification;
import com.internal.codegen.reader.YamlClassSpecificationReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class CodegenApplication implements CommandLineRunner {

	@Autowired
	private YamlClassSpecificationReader yamlReader;

	public static void main(String[] args) {
		SpringApplication.run(CodegenApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		final File yamlFile = ResourceUtils.getFile("classpath:userClass.yml");
		final File outputDirectory = yamlFile.getParentFile();

		List<ClassSpecification> classSpecifications = yamlReader.read(yamlFile);

		// Generate Java source files from the class specifications
		JavaClassGenerator javaDataClassGenerator = new JavaClassGenerator();
		javaDataClassGenerator.generateJavaSourceFiles(classSpecifications, outputDirectory);

		System.out.println("Successfully generated files to: " + outputDirectory.getAbsolutePath());
	}
}
