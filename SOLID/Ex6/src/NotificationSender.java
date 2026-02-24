
public abstract class NotificationSender {
    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) {
        this.audit = audit;
    }

    public abstract boolean canSend(Notification n);
    public abstract void send(Notification n);
}
