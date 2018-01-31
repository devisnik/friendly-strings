import org.junit.Assert;
import org.junit.Test;

public class FriendsMapTest {

    String[] sampleSet = {"LALALA", "XOXOXO", "GCGCGC", "HHHCCC", "BBBMMM", "EGONUH", "HHRGOE"};

    @Test
    public void shouldFindFriendsInSample() {
        FriendsMap friendsMap = new FriendsMap();
        for (String text : sampleSet) {
            friendsMap.add(text);
        }

        Assert.assertEquals(5, friendsMap.count());
    }
}
