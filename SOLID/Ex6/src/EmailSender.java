public class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public boolean canSend(Notification n) {
        return n != null && n.email != null && !n.email.isEmpty();
    }

    @Override
    public void send(Notification n) {
        if (!canSend(n)) {
            throw new IllegalArgumentException("Email address is required");
        }
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
        audit.add("email sent");
    }
}
