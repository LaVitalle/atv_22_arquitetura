import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Playlist implements SongCollection {

    private final List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null");
        }
        songs.add(song);
    }

    @Override
    public SongIterator createIterator() {
        return new PlaylistIterator();
    }

    public SongIterator createReverseIterator() {
        return new ReversePlaylistIterator();
    }

    private class PlaylistIterator implements SongIterator {

        private int position = 0;

        @Override
        public boolean hasNext() {
            return position < songs.size();
        }

        @Override
        public Song next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more songs");
            }
            Song song = songs.get(position);
            position++;
            return song;
        }
    }

    private class ReversePlaylistIterator implements SongIterator {

        private int position = songs.size() - 1;

        @Override
        public boolean hasNext() {
            return position >= 0;
        }

        @Override
        public Song next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more songs");
            }
            Song song = songs.get(position);
            position--;
            return song;
        }
    }
}
