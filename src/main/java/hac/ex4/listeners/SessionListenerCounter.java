package hac.ex4.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A @WebListener class for session counting.
 */
@WebListener
public class SessionListenerCounter implements HttpSessionListener {
    private final AtomicInteger activeSessions;

    /**
     * The class constructor.
     */
    public SessionListenerCounter() {
        super();
        activeSessions = new AtomicInteger();
    }

    /**
     *
     * @return
     */
    public int getTotalActiveSession() {
        return activeSessions.get();
    }

    /**
     * The function increases the number of sessions by 1 and prints the current number of sessions.
     * It receives a HttpSessionEvent which announces that a new session has been created.
     * @param event The HttpSessionEvent which announces that a new session has been created.
     */
    public void sessionCreated(final HttpSessionEvent event) {
        activeSessions.incrementAndGet();
        System.out.println("SessionListenerCounter +++ Total active session are " + activeSessions.get());
    }

    /**
     * The function decreases the number of sessions by 1 and prints the current number of sessions.
     * It receives a HttpSessionEvent which announces that a new session has been destroyed.
     * @param event The HttpSessionEvent which announces that a new session has been destroyed.
     */
    public void sessionDestroyed(final HttpSessionEvent event) {
        activeSessions.decrementAndGet();
        System.out.println("SessionListenerCounter --- Total active session are " + activeSessions.get());
    }
}
