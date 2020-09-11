import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Class for Post actions such as holding post content/date or calculating time since post.
 *
 * @author Ben D'Aprile
 */
public class Post {

    private final Account postingAccount;
    private final String content;
    private final LocalDateTime timePosted;

    public Post(Account postingAccount, String content) {
        this.postingAccount = postingAccount;
        this.content = content;
        this.timePosted = LocalDateTime.now();
    }

    public Post(Account postingAccount, String content, LocalDateTime timePosted) {
        this.postingAccount = postingAccount;
        this.content = content;
        this.timePosted = timePosted;
    }

    /**
     * Calculates the difference between the current time and the time this post was posted.
     *
     * @return String with the time between the post and now
     */
    public String calculateTimeSincePost() {
        long secondsBetween = Duration.between(timePosted, LocalDateTime.now()).getSeconds();

        if (secondsBetween < TimeUnit.MINUTES.toSeconds(1)) {
            return "(" + Math.round(secondsBetween) + " seconds ago)";
        }
        else if (secondsBetween < TimeUnit.HOURS.toSeconds(1)) {
            return "(" + Math.round(secondsBetween/(float)TimeUnit.MINUTES.toSeconds(1)) + " minutes ago)";
        }
        else if (secondsBetween < TimeUnit.DAYS.toSeconds(1)) {
            return "(" + Math.round(secondsBetween/(float)TimeUnit.HOURS.toSeconds(1)) + " hours ago)";
        }

        return "(" + Math.round(secondsBetween/(float)TimeUnit.DAYS.toSeconds((1))) + " days ago)";
    }

    public String getContent() {
        return content;
    }
    public LocalDateTime getTimePosted() {
        return timePosted;
    }
    public Account getPostingAccount() {
        return postingAccount;
    }
}
