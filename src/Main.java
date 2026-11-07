import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        playlist.addSong(new Song("First song"));
        playlist.addSong(new Song("Second song"));
        playlist.addSong(new Song("Third song"));

        SongCollection collection = playlist;

        System.out.println("=== Full traversal ===");
        printSongs(collection.createIterator());

        System.out.println("\n=== Independent iterators ===");
        SongIterator first = collection.createIterator();
        SongIterator second = collection.createIterator();
        first.next();
        first.next();
        System.out.println("Second iterator returns: " + second.next().getTitle());
        System.out.println("First iterator returns: " + first.next().getTitle());

        System.out.println("\n=== Empty playlist ===");
        SongIterator empty = new Playlist().createIterator();
        System.out.println("hasNext on empty playlist: " + empty.hasNext());

        System.out.println("\n=== Calling next() after the end ===");
        try {
            first.next();
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Reverse traversal ===");
        printSongs(playlist.createReverseIterator());

        System.out.println("\n=== New normal traversal keeps the original order ===");
        printSongs(collection.createIterator());

        System.out.println("\n=== Reverse traversal on an empty playlist ===");
        SongIterator emptyReverse = new Playlist().createReverseIterator();
        System.out.println("hasNext on empty playlist: " + emptyReverse.hasNext());
    }

    private static void printSongs(SongIterator iterator) {
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getTitle());
        }
    }
}
