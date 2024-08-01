
<#if classSpecification.name?ends_with("Repository")>
import org.springframework.stereotype.Repository;
<#elseif classSpecification.name?ends_with("Application")>
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<#elseif classSpecification.name?ends_with("Controller")>
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@Slf4j
<#elseif classSpecification.name?ends_with("Service")>
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
@Service
<#else>
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
</#if>
public class ${classSpecification.name}{
<#if classSpecification.name?ends_with("Application")>
	public static void main(String[] args){
		SpringApplication.run(${classSpecification.name}.class, args);
<#else>
	<#list classSpecification.fieldSpecifications as field>
		private ${field.type} ${field.name};
	</#list>
</#if>
}