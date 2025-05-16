package com.aluracursos.conversor.modelos;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapper extends ObjectMapper {
	public CustomObjectMapper() {
		super();
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.enable(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION);
	}
}
