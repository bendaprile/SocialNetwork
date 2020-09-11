import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Test class for Account. Tests that accounts can be created and timelines can be read.
 *
 * @author Ben D'Aprile
 */
public class AccountTest {

    String username = "bendaprile";
    String firstName = "Ben";
    String lastName = "D'Aprile";

    /**
     * Tests that an Account is created and returns the correct Account information.
     */
    @Test
    public void testCreateAccount() {
        Account newAccount = new Account(username, firstName, lastName);

        assert newAccount.getUsername().equals(username);
        assert newAccount.getFirstName().equals(firstName);
        assert newAccount.getLastName().equals(lastName);
    }

    /**
     * Tests that the length of the account's posts before a post is 0 and 1 after a post is published.
     */
    @Test
    public void testPublish() {
        Account newAccount = new Account(username, firstName, lastName);
        assert newAccount.getAllPosts().size() == 0;

        Post newPost = new Post(null, "This is my first post.");
        newAccount.publishPost(newPost);
        assert newAccount.getAllPosts().size() == 1;
    }

    /**
     * Tests that two posts can be created and are returned in the correct reverse order.
     */
    @Test
    public void testGetAllPostsCorrectOrder() {
        Account newAccount = new Account(username, firstName, lastName);

        Post post1 = new Post(newAccount, "First Post");
        newAccount.publishPost(post1);
        Post post2 = new Post(newAccount, "Second Post");
        newAccount.publishPost(post2);

        Stack<Post> posts = newAccount.getAllPosts();

        assert posts.pop().equals(post2);
        assert posts.pop().equals(post1);
    }

    /**
     * Tests the timeline is printed without timestamps when viewing your own timeline.
     */
    @Test
    public void testViewOwnPersonalTimeline() {
        Account benAccount = new Account(username, firstName, lastName);
        Post post1 = new Post(benAccount, "First Post");
        benAccount.publishPost(post1);
        Post post2 = new Post(benAccount, "Second Post");
        benAccount.publishPost(post2);

        ArrayList<String> timeline = benAccount.viewPersonalTimeline(benAccount, true);

        assert timeline.get(0).equals("Second Post");
        assert timeline.get(1).equals("First Post");
    }

    /**
     * Tests the timeline is printed with timestamps when viewing another account's own timeline.
     */
    @Test
    public void testViewOtherPersonalTimeline() {
        Account benAccount = new Account(username, firstName, lastName);
        Account bobAccount = new Account("bobdaprile", "Bob", "D'Aprile");

        Post post1 = new Post(benAccount, "First Post");
        benAccount.publishPost(post1);
        Post post2 = new Post(benAccount, "Second Post");
        benAccount.publishPost(post2);

        ArrayList<String> timeline = bobAccount.viewPersonalTimeline(benAccount, true);
        assert timeline.get(0).equals("Second Post (0 seconds ago)");
        assert timeline.get(1).equals("First Post (0 seconds ago)");
    }

    /**
     * Tests that the following and followers lists get updated when following an account.
     */
    @Test
    public void testFollowAccount() {
        Account benAccount = new Account(username, firstName, lastName);
        Account bobAccount = new Account("bobdaprile", "Bob", "D'Aprile");

        benAccount.followAccount(bobAccount);

        assert benAccount.getFollowing().contains(bobAccount);
        assert benAccount.getFollowers().isEmpty();
        assert bobAccount.getFollowers().contains(benAccount);
        assert bobAccount.getFollowing().isEmpty();
    }

    /**
     * Tests that attempting to follow yourself does nothing.
     */
    @Test
    public void testFollowOwnAccount() {
        Account benAccount = new Account(username, firstName, lastName);

        benAccount.followAccount(benAccount);

        assert benAccount.getFollowing().isEmpty();
        assert benAccount.getFollowers().isEmpty();

    }

    /**
     * Tests that viewing the main timeline outputs with names and in the correct order.
     */
    @Test
    public void testViewMainTimeline() {
        Account benAccount = new Account(username, firstName, lastName);
        Account bobAccount = new Account("bobdaprile", "Bob", "D'Aprile");
        Account charlieAccount = new Account("charliebrown", "Charlie", "Brown");

        benAccount.publishPost(new Post(benAccount, "I love the weather today.", LocalDateTime.now().minus(4, ChronoUnit.HOURS)));
        bobAccount.publishPost(new Post(bobAccount, "Darn! We lost!", LocalDateTime.now().minus(3, ChronoUnit.HOURS)));
        bobAccount.publishPost(new Post(bobAccount, "Good game though.", LocalDateTime.now().minus(2, ChronoUnit.HOURS)));
        charlieAccount.publishPost(new Post(charlieAccount, "I'm in New York today! Anyone wants to have a coffee?", LocalDateTime.now().minus(1, ChronoUnit.HOURS)));

        charlieAccount.followAccount(benAccount);
        charlieAccount.followAccount(bobAccount);

        ArrayList<String> timeline = charlieAccount.viewMainTimeline(true);

        assert timeline.get(3).equals("Ben - I love the weather today. (4 hours ago)");
        assert timeline.get(2).equals("Bob - Darn! We lost! (3 hours ago)");
        assert timeline.get(1).equals("Bob - Good game though. (2 hours ago)");
        assert timeline.get(0).equals("Charlie - I'm in New York today! Anyone wants to have a coffee? (1 hours ago)");
    }
}