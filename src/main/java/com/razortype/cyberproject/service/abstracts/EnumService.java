package com.razortype.cyberproject.service.abstracts;

import com.razortype.cyberproject.core.results.DataResult;

import java.util.List;

public interface EnumService {

    DataResult<List<String>> getEnumValues(String enumName);


}
