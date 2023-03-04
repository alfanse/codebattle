import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://codebattle.hexlet.io/games/124253
 */
public class InvertCaseTest {

    /**
     * Implement a function that changes the case of each letter in the string to the opposite.
     * Examples:
     * "hELLO, wORLD."  == solution("Hello, World.")
     * "i lOVE clojure!"  == solution("I Love CLOJURE!")
     * "mIxEd CaSe"  == solution("MiXeD cAsE")
     * "FRONTEND? what IS it?"  == solution("frontend? WHAT is IT?")
     */
    private static Stream<Arguments> acceptanceTestValues() {
        return Stream.of(
                Arguments.of("hELLO, wORLD.", "Hello, World."),
                Arguments.of("i lOVE clojure!", "I Love CLOJURE!"),
                Arguments.of("mIxEd CaSe", "MiXeD cAsE"),
                Arguments.of("FRONTEND? what IS it?", "frontend? WHAT is IT?")
        );
    }

    /**
     * first working solution
     */
    private String invertCaseImperative(String str) {
        StringBuilder sb = new StringBuilder();
        char[] thing = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            sb.append(inverseChar(thing[i]));
        }
        return sb.toString();
    }

    /** functional working solution */
    private String invertCaseFunctional(String str) {
        return str.chars()
                .map(this::inverseCharFromInt)
                .mapToObj(Character::toString)
                .collect(Collectors.joining());

//                .collect(StringBuilder::new,
//                        StringBuilder::append,
//                        (sbl, sbr) -> sbl.append(sbr.toString())
//                )

//                .collect(
//                        StringWriter::new,
//                        StringWriter::write,
//                        (swl, swr) -> swl.write(swr.toString()))
//                .toString();

//        StringBuilder sb = new StringBuilder();
//        str.chars().forEachOrdered(value -> sb.append(Character.toString(inverseCharFromInt(value))));
//        return sb.toString();
    }

    private int inverseCharFromInt(int currentChar) {
        if (Character.isLowerCase(currentChar)) {
            return Character.toUpperCase(currentChar);
        } else if (Character.isUpperCase(currentChar)) {
            return Character.toLowerCase(currentChar);
        } else {
            return currentChar;
        }
    }

    private char inverseChar(char currentChar) {
        if (Character.isLowerCase(currentChar)) {
            return Character.toUpperCase(currentChar);
        } else if (Character.isUpperCase(currentChar)) {
            return Character.toLowerCase(currentChar);
        } else {
            return currentChar;
        }
    }

    @RepeatedTest(1000)
    void performanceTestImperativeSoln() {
        invertCaseImperative("input Some DATA");
    }

    @RepeatedTest(1000)
    void performanceTestFunctionalSoln() {
        invertCaseFunctional("input Some DATA");
    }

    @ParameterizedTest
    @MethodSource("acceptanceTestValues")
    void acceptanceTestImperativeSolution(String input, String expected) {
        assertThat(invertCaseImperative(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("acceptanceTestValues")
    void acceptanceTestFunctionalSolution(String input, String expected) {
        assertThat(invertCaseFunctional(input)).isEqualTo(expected);
    }
}
