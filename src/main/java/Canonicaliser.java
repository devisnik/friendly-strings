import java.util.HashMap;

public class Canonicaliser {

    private final HashMap<Character, Character> mapping;
    private char[] mapped;

    Canonicaliser(int size) {
        mapping = new HashMap<>(size);
        mapped = new char[size];
    }

    public String convert(String text) {
        mapping.clear();
        char[] chars = text.toCharArray();
        char current = 'a';

        for (int index = 0; index < chars.length; index++) {
            Character toReplace = chars[index];
            Character value = mapping.get(toReplace);
            if (value != null) {
                mapped[index] = value;
            } else {
                mapped[index] = current;
                mapping.put(toReplace, current);
                current++;
            }
        }

        return new String(mapped);
    }
}
