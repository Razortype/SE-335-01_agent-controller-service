package com.razortype.cyberproject.service.concretes;

import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.ErrorDataResult;
import com.razortype.cyberproject.core.results.SuccessDataResult;
import com.razortype.cyberproject.service.abstracts.EnumService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnumServiceImpl implements EnumService {


    @Override
    public DataResult<List<String>> getEnumValues(String enumName) {
        List<String> enumValues = new ArrayList<>();
        try {
            String className = convertToClassName(enumName);
            Class<?> enumClass = Class.forName("com.razortype.cyberproject.core.enums." + className);

            if (enumClass.isEnum()) {
                Object[] constants = enumClass.getEnumConstants();
                for (Object constant : constants) {
                    enumValues.add(constant.toString());
                }
            }
        } catch (ClassNotFoundException e) {
            return new ErrorDataResult<>("Enum not found: " + enumName);
        } catch (Exception e) {
            return new ErrorDataResult<>("Unexpected Error Occurred: " + e.getMessage());
        }

        return new SuccessDataResult<>(enumValues, "Enum found");
    }

    private String convertToClassName(String enumName) {
        StringBuilder classNameBuilder = new StringBuilder();
        String[] parts = enumName.split("-");
        for (String part : parts) {
            classNameBuilder.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return classNameBuilder.toString();
    }
}
