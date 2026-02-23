public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public boolean canSend(Notification n) {
        return n != null && n.phone != null && n.phone.startsWith("+");
    }

    @Override
    public void send(Notification n) {
        if (!canSend(n)) {
            throw new IllegalArgumentException("phone must start with + and country code");
        }
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }
}
