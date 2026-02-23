public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public boolean canSend(Notification n) {
        return n != null && n.phone != null && !n.phone.isEmpty();
    }

    @Override
    public void send(Notification n) {
        if (!canSend(n)) {
            throw new IllegalArgumentException("Phone number is required");
        }
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
    }
}
