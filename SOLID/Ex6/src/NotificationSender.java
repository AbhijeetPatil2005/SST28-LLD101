/**
 * Base contract for notification senders.
 * 
 * Preconditions:
 * - Notification must not be null.
 * - Required fields depend on channel (see canSend).
 * 
 * Postconditions:
 * - Message is sent without modification to original meaning.
 * - Audit log is updated.
 */
public abstract class NotificationSender {
    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) {
        this.audit = audit;
    }

    /**
     * Returns true if this sender can handle the given notification.
     */
    public abstract boolean canSend(Notification n);

    /**
     * Sends the notification. Must honor contract: no truncation, no field omission.
     * Throws RuntimeException only if preconditions are violated.
     */
    public abstract void send(Notification n);
}
