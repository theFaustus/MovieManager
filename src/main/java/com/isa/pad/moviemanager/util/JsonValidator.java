package com.isa.pad.moviemanager.util;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Faust on 11/13/2017.
 */
public class JsonValidator {
    private static Logger logger = Logger.getLogger(XmlValidator.class.getName());

    private String schemaFilePath;

    public JsonValidator(String schemaFilePath) {
        this.schemaFilePath = schemaFilePath;
    }

    public boolean validate(String jsonData) {
        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(getClass().getClassLoader().getResourceAsStream(schemaFilePath)));
        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(new JSONObject(jsonData));
            return true;
        } catch (ValidationException e) {
            return false;
        }

    }
}
