import java.util.HashMap;
import java.util.Map;

public class FriendsMap {

    private final Map<String, Integer> friendsCount = new HashMap<>();
    private final Canonicaliser canonicaliser;
    private final int key;

    FriendsMap(int key) {
        this.key = key;
        canonicaliser = new Canonicaliser(key);
    }

    public void add(String text) {
        String canonical = canonicaliser.convert(text);
        if (friendsCount.containsKey(canonical))
            friendsCount.put(canonical, friendsCount.get(canonical) + 1);
        else
            friendsCount.put(canonical, 1);
    }

    public int count() {
        return friendsCount.values().stream().filter(i -> i > 1).reduce(0, (a, b) -> a + b);
    }

    public int key() {
        return key;
    }
}
