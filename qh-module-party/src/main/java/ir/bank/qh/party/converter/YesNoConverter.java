package ir.bank.qh.party.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts a Java {@link Boolean} to/from the single-character {@code 'Y'/'N'}
 * column representation used throughout the Party model (e.g. IS_PRIMARY,
 * IS_ACTIVE, LISTED_COMPANY_FLAG). Referenced via {@code @Convert(converter =
 * YesNoConverter.class)} on the relevant entity fields.
 */
@Converter
public class YesNoConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return "N";
        }
        return attribute ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return Boolean.FALSE;
        }
        return "Y".equalsIgnoreCase(dbData.trim());
    }
}
