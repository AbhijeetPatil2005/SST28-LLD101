
/*
 * ─── Demo06 — LSP Demonstration ───
 *
 * HOW LSP IS VISIBLE HERE:
 *   - All three senders (email, sms, wa) are typed as NotificationSender
 *     (the BASE type), not their concrete types.
 *   - send() is called on each with the SAME Notification — no format-specific
 *     handling, no instanceof, no try-catch.
 *   - "No try-catch needed: base contract guarantees no exception." —
 *     this comment proves LSP is working.
 *   - WhatsApp fails gracefully (phone doesn't start with "+") but the
 *     caller doesn't need to handle it specially — it's just another send().
 *   - audit.size() == 3 confirms all senders logged exactly one entry each.
 *
 * KEY INSIGHT:
 *   If we added a 4th sender (e.g., PushNotificationSender), we'd just
 *   add it here and call send(). The existing code doesn't change.
 *   TRUE substitutability: any NotificationSender works the same way.
 */
public class Demo06 {
    public static void main(String[] args) {
        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

        // ─── All senders typed as the BASE type NotificationSender ───
        // No subtype-specific handling required — this IS LSP.
        NotificationSender email = new EmailSender(audit);
        NotificationSender sms = new SmsSender(audit);
        NotificationSender wa = new WhatsAppSender(audit);

        // ─── Uniform calls — no try-catch, no instanceof ───
        email.send(n);  // Delivers full body to email
        sms.send(n);    // Delivers body via SMS (subject skipped — channel limitation)
        wa.send(n);     // Gracefully fails (phone missing "+") — no exception thrown

        // All 3 senders logged exactly one audit entry each
        System.out.println("AUDIT entries=" + audit.size());
    }
}
