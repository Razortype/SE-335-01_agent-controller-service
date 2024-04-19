package com.razortype.cyberproject.api.controller;

import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.service.abstracts.EnumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/enum")
@RequiredArgsConstructor
public class EnumController {

    private final EnumService enumService;

    @Operation(summary = "Get Enum Values", description = "Get enum class with kebab-case. Parameter converted to CamelCase and search for exist class")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{enum-name}")
    public ResponseEntity<DataResult<List<String>>> getEnumValues(@PathVariable(name = "enum-name") String enumName) {

        DataResult<List<String>> result = enumService.getEnumValues(enumName);

        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);
    }

}
