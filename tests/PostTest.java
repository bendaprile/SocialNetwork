import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Test class for Post. Tests that posts can be created and return the correct information.
 *
 * @author Ben D'Aprile
 */
public class PostTest {

    /**
     * Tests that a post is created and returns the correct post content.
     */
    @Test
    public void testNewPost() {
        Post newPost = new Post(null, "This is my first post");

        assert newPost.getContent().equals("This is my first post");
    }

    /**
     * Tests that a one post is created and then another post is created and the second
     * post was created after the first post.
     */
    @Test
    public void testPostOrder() {
        Post newPost1 = new Post(null, "This is my first post");
        Post newPost2 = new Post(null, "This is my second post");

        assert newPost1.getTimePosted().isBefore(newPost2.getTimePosted());
    }

    /**
     * Tests that the time calculated between two posts is correct for days, hours, minutes, and seconds.
     */
    @Test
    public void testCalculateTimeSincePost() {
        Post newPostDays = new Post(null, "This is my post 3 days ago.", LocalDateTime.now().minus(3, ChronoUnit.DAYS));
        Post newPostHours = new Post(null, "This is my post 3 hours ago.", LocalDateTime.now().minus(3, ChronoUnit.HOURS));
        Post newPostMinutes = new Post(null, "This is my post 3 minutes ago.", LocalDateTime.now().minus(3, ChronoUnit.MINUTES));
        Post newPostSeconds = new Post(null, "This is my post 3 minutes ago.", LocalDateTime.now().minus(3, ChronoUnit.SECONDS));

        assert newPostDays.calculateTimeSincePost().equals("(3 days ago)");
        assert newPostHours.calculateTimeSincePost().equals("(3 hours ago)");
        assert newPostMinutes.calculateTimeSincePost().equals("(3 minutes ago)");
        assert newPostSeconds.calculateTimeSincePost().equals("(3 seconds ago)");
    }
}