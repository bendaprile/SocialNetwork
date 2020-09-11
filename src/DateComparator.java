import java.util.Comparator;

public class DateComparator implements Comparator<Post> {

    @Override
    public int compare(Post post1, Post post2) {
        return post2.getTimePosted().compareTo(post1.getTimePosted());
    }
}
