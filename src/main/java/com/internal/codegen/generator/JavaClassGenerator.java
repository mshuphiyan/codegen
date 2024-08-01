package com.internal.codegen.generator;

import com.internal.codegen.model.ClassSpecification;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JavaClassGenerator {
    private final Configuration configuration;

    public JavaClassGenerator() {
        configuration = new Configuration(Configuration.VERSION_2_3_28);

        // Set the root of the class path ("") as the location to find templates
        configuration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "");

        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
    }

    public void generateJavaSourceFiles(Collection<ClassSpecification> classSpecifications, File yamlFileDirectory) throws Exception {
        Map<String, Object> freemarkerDataModel = new HashMap<>();

        // Get the template to generate Java source files
        Template template = configuration.getTemplate("javadataclass.ftl");


        for (ClassSpecification classSpecification : classSpecifications) {
            freemarkerDataModel.put("classSpecification", classSpecification);
            log.info("main location " + yamlFileDirectory);
            File dir = null;

            if (classSpecification.getName().endsWith("Service")) {
                dir = new File(yamlFileDirectory + File.separator + "service");
                dir.mkdir();
                log.info("new location " + dir);
            } else if (classSpecification.getName().endsWith("Repository")) {
                dir = new File(yamlFileDirectory + File.separator + "repository");
                dir.mkdir();
                log.info("new location " + dir);
            } else if (classSpecification.getName().endsWith("Controller")) {
                dir = new File(yamlFileDirectory + File.separator + "controller");
                dir.mkdir();
                log.info("new location " + dir);
            } else if (classSpecification.getName().endsWith("Application")) {
                dir = yamlFileDirectory;
                log.info("new location " + dir);
            } else {
                dir = new File(yamlFileDirectory + File.separator + "model");
                dir.mkdir();
                log.info("new location " + dir);
            }


            File javaSourceFile = new File(dir + File.separator, classSpecification.getName() + ".java");
            Writer javaSourceFileWriter = new FileWriter(javaSourceFile);

            template.process(freemarkerDataModel, javaSourceFileWriter);
        }
    }
}
