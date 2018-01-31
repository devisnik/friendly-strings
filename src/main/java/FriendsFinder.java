import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static com.google.common.io.Resources.getResource;

public class FriendsFinder {

    private final FileSystem fileSystem = FileSystems.getDefault();

    private Flowable<String> texts() throws IOException {
        Stream<String> linesStream = Files.lines(fileSystem.getPath(getResource("words.txt").getFile()));
        return Flowable
                .fromIterable(linesStream::iterator);
    }

    Maybe<Integer> computeFriends() throws IOException {
        return texts()
                .groupBy(String::length)
                .flatMapSingle(groupedFlowable ->
                        groupedFlowable
                                .reduce(
                                        new FriendsMap(groupedFlowable.getKey()),
                                        (map, text) -> {
                                            map.add(text);
                                            return map;
                                        }
                                )
                )
                .doOnNext(map -> System.out.println(map.key() + ": " + map.count()))
                .map(FriendsMap::count)
                .reduce((a, b) -> a + b);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);

        new FriendsFinder()
                .computeFriends()
                .subscribe(count -> {
                    System.out.println("SUM = " + count);
                    latch.countDown();
                });

        latch.await();
        System.out.println("done: " + (System.currentTimeMillis() - startTime));
    }
}
