import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Calculator {
    public static void main(String[] args) {
        System.out.println("input:");
        var scanner = new Scanner(System.in);
        var input = scanner.nextLine();
        var patternArabic = Pattern.compile("([0-9]|10)\\s\\+?-?\\*?/?\\s([0-9]|10)");
        var patternRoman = Pattern.compile("(I|II|III|IV|V|VI|VII|VIII|IX|X)\\s\\+?-?\\*?/?\\s(I|II|III|IV|V|VI|VII|VIII|IX|X)");
        if(patternArabic.matcher(input).matches()){
            System.out.println("output:");
            System.out.println(calc(input));
        } else if(patternRoman.matcher(input).matches()){
            System.out.println("output:");
            var arrRoman = input.split(" ");
            arrRoman[0] = String.valueOf(RomanNumeral.romanToArabic(arrRoman[0]));
            arrRoman[2] = String.valueOf(RomanNumeral.romanToArabic(arrRoman[2]));
            var output = arrRoman[0] + " " + arrRoman[1] + " " + arrRoman[2];
            System.out.println(RomanNumeral.arabicToRoman(calc(output)));
        } else {
            throw new IllegalArgumentException();
        }
    }
    public static int calc (String input){
        String[] inputArr = input.split(" ");
        return switch (inputArr[1]) {
            case "+" -> Integer.parseInt(inputArr[0]) + Integer.parseInt(inputArr[2]);
            case "-" -> Integer.parseInt(inputArr[0]) - Integer.parseInt(inputArr[2]);
            case "*" -> Integer.parseInt(inputArr[0]) * Integer.parseInt(inputArr[2]);
            case "/" -> Integer.parseInt(inputArr[0]) / Integer.parseInt(inputArr[2]);
            default -> 0;
        };
    }
    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10);

        private final int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }

        public static int romanToArabic(String input) {
            var romanNumeral = input.toUpperCase();
            var result = 0;
            var romanNumerals = RomanNumeral.getReverseSortedValues();
            var i = 0;

            while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
                RomanNumeral symbol = romanNumerals.get(i);
                if (romanNumeral.startsWith(symbol.name())) {
                    result += symbol.getValue();
                    romanNumeral = romanNumeral.substring(symbol.name().length());
                } else {
                    i++;
                }
            }

            if (romanNumeral.length() > 0) {
                throw new IllegalArgumentException();
            }
            return result;
        }
        public static String arabicToRoman(int number) {
            if (number < 0) {
                throw new IllegalArgumentException();
            }

            if (number == 0) {
                return "0";
            }

            var romanNumerals = RomanNumeral.getReverseSortedValues();
            var i = 0;
            var sb = new StringBuilder();

            while ((number > 0) && (i < romanNumerals.size())) {
                RomanNumeral currentSymbol = romanNumerals.get(i);
                if (currentSymbol.getValue() <= number) {
                    sb.append(currentSymbol.name());
                    number -= currentSymbol.getValue();
                } else {
                    i++;
                }
            }
            return sb.toString();
        }
    }
}
