package com.samkruglov.bank.api.graphql.config;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// https://github.com/graphql-java/graphql-java/issues/991#issuecomment-404840156
@Component
public class ZonedDateTimeScalar extends GraphQLScalarType {

    public ZonedDateTimeScalar() {
        super("ZonedDateTime", "java.time.ZonedDateTime scalar",
              new ZonedDateTimeScalarCoercing());
    }


    private static class ZonedDateTimeScalarCoercing implements Coercing<ZonedDateTime, String> {


        public String serialize(Object input) {
            if (input instanceof ZonedDateTime) {
                return ((ZonedDateTime) input).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
            }

            ZonedDateTime result = convertString(input);

            if (result == null) {
                throw new CoercingSerializeException("Invalid value '$input' for ZonedDateTime");
            }

            return result.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        }

        public ZonedDateTime parseValue(Object input) {

            ZonedDateTime time = convertString(input);

            if (time == null) {
                throw new CoercingParseValueException("Invalid value '$input' for ZonedDateTime");
            }

            return time;
        }

        public ZonedDateTime parseLiteral(Object input) {

            if (!(input instanceof StringValue)) {
                return null;
            }

            return convertString(((StringValue) input).getValue());
        }

        private ZonedDateTime convertString(Object input) {

            if (input instanceof String) {

                ZonedDateTime time;

                try {
                    time = ZonedDateTime.parse((String) input);
                } catch (DateTimeParseException e) {
                    time = null;
                }

                return time;
            }

            return null;
        }
    }
}