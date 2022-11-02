package exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;


class ValidationTest {

    @Test
    void testValidate() {
        Address address1 = new Address("Russia", "Ufa", "Lenina", "54", null);
        List<String> result1 = Validator.validate(address1);
        List<String> expected1 = List.of();
        assertThat(result1).isEqualTo(expected1);

        Address address2 = new Address(null, "London", "1-st street", "5", "1");
        List<String> result2 = Validator.validate(address2);
        List<String> expected2 = List.of("country");
        assertThat(result2).isEqualTo(expected2);

        Address address3 = new Address("USA", null, null, null, "1");
        List<String> result3 = Validator.validate(address3);
        List<String> expected3 = List.of("city", "street", "houseNumber");
        assertThat(result3).isEqualTo(expected3);
    }

    @Test
    void testAdvancedValidate() {
        Address addressWithAllFieldsIsValid = new Address("Russia", "Ufa", "Lenina", "54", null);
        Map<String, List<String>> resultWhereAllFieldsIsValid = Validator.advancedValidate(addressWithAllFieldsIsValid);
        assertThat(resultWhereAllFieldsIsValid).isEmpty();

        Address addressWithFieldsIsNull = new Address("Russia", null, null, "54", null);
        Map<String, List<String>> exceptedWhereFieldsIsNull = Map.of("city", List.of("can not be null"), "street", List.of("can not be null"));
        Map<String, List<String>> resultWhereFieldsIsNull = Validator.advancedValidate(addressWithFieldsIsNull);
        assertThat(resultWhereFieldsIsNull).isEqualTo(exceptedWhereFieldsIsNull);

        Address addressWithMoreMinLength = new Address("Russi", "Ufa", "Lenina", "54", null);
        Map<String, List<String>> resultWhereMoreMinLength = Validator.advancedValidate(addressWithMoreMinLength);
        assertThat(resultWhereMoreMinLength).isEmpty();

        Address addressWithEqualsMinLength = new Address("Russ", "Ufa", "Lenina", "54", null);
        Map<String, List<String>> resultWhereEqualsMinLength = Validator.advancedValidate(addressWithEqualsMinLength);
        assertThat(resultWhereEqualsMinLength).isEmpty();

        Address addressWithLessMinLength = new Address("Ru", "Ufa", "Lenina", "54", null);
        Map<String, List<String>> resultWhereLessMinLength = Validator.advancedValidate(addressWithLessMinLength);
        Map<String, List<String>> exceptedWhereLessMinLength = Map.of("country", List.of("length less than 4"));
        assertThat(resultWhereLessMinLength).isEqualTo(exceptedWhereLessMinLength);

        Address addressWithIsNotValidFields = new Address(null, "Ufa", null, "54", null);
        Map<String, List<String>> resultWhereIsNotValidFields = Validator.advancedValidate(addressWithIsNotValidFields);
        Map<String, List<String>> exceptedWhereIsNotValidFields = Map.of("country", List.of("can not be null", "length less than 4"), "street", List.of("can not be null"));
        assertThat(resultWhereIsNotValidFields).isEqualTo(exceptedWhereIsNotValidFields);
    }
}
