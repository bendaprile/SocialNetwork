import java.util.ArrayList;
import java.util.Stack;

/**
 * Class for Account actions such as publishing posts, viewing timelines, and following accounts.
 *
 * @author Ben D'Aprile
 */
public class Account {

    private String username;
    private String firstName;
    private String lastName;
    private Stack<Post> posts;
    private ArrayList<Account> followers;
    private ArrayList<Account> following;

    public Account(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new Stack<Post>();
        this.following = new ArrayList<Account>();
        this.followers = new ArrayList<Account>();
    }

    /**
     * Adds the post to the Stack of posts for this account.
     *
     * @param post to be published.
     */
    void publishPost(Post post) {
        posts.push(post);
    }

    /**
     * Adds an account to this account's following list and to the other accounts followers list.
     *
     * @param account to be follow.
     */
    void followAccount(Account account) {
        if (account.equals(this)) {
            System.out.println("You cannot follow yourself.");
            return;
        }

        following.add(account);
        account.addFollower(this);
    }

    /**
     * Adds a follower to this account's followers list.
     *
     * @param account to be added to followers list.
     */
    void addFollower(Account account) {
        followers.add(account);
    }

    /**
     * Outputs the Stack of posts in a more readable format.
     *
     * @param account - the account is being viewed.
     * @param printToConsole - whether or not to print the output to the console
     *
     * @return ArrayList<String> of all the posts that the account has posted
     */
    ArrayList<String> viewPersonalTimeline(Account account, boolean printToConsole) {
        Stack<Post> posts = account.getAllPosts();
        ArrayList<String> timeline = new ArrayList<String>();
        boolean viewingOwnTimeline = false;

        if (account == this) {
            viewingOwnTimeline = true;
        }

        for (int i=0; i <= posts.size(); i++) {
            Post post = posts.pop();

            // If this person is viewing their own timeline then don't show timestamps
            timeline.add( viewingOwnTimeline ? post.getContent() : post.getContent() + " " + post.calculateTimeSincePost());

            if (printToConsole) {
                System.out.println(timeline.get(i));
            }
        }

        return timeline;
    }

    /**
     * Collects all following timelines, orders them, and returns them in a more readable form.
     *
     * @param printToConsole - whether or not to print the output to the console
     *
     * @return ArrayList<String> of all the posts that the account follows
     */
    ArrayList<String> viewMainTimeline(boolean printToConsole) {

        // Add the current account's posts to the timeline first
        ArrayList<Post> timelinePosts = new ArrayList<>(this.getAllPosts());

        ArrayList<String> timelineOutput = new ArrayList<String>();
        for (Account account : following) {
            timelinePosts.addAll(account.getAllPosts());
        }

        timelinePosts.sort(new DateComparator());

        for (Post post : timelinePosts) {

            if (post.getPostingAccount() != null) {
                timelineOutput.add(post.getPostingAccount().getFirstName()  + " - " + post.getContent() + " " + post.calculateTimeSincePost());
                continue;
            }
            timelineOutput.add(post.getContent() + " " + post.calculateTimeSincePost());
        }

        if (printToConsole) {
            for (String post : timelineOutput) {
                System.out.println(post);
            }
        }

        return timelineOutput;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Stack<Post> getAllPosts() {
        return (Stack<Post>)posts.clone();
    }

    public ArrayList<Account> getFollowers() {
        return followers;
    }

    public ArrayList<Account> getFollowing() {
        return following;
    }
}
