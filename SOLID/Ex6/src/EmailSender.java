/*
 * ─── LSP FIX: EmailSender ───
 *
 * ORIGINAL PROBLEM:
 *   The old EmailSender SILENTLY TRUNCATED long messages. This WEAKENED
 *   the postcondition: the base contract promises "body delivered as-is",
 *   but EmailSender was cutting content, causing silent data loss.
 *
 * HOW WE FIXED IT:
 *   EmailSender now delivers the FULL body without truncation.
 *   "Delivers body as-is — no truncation (honours base-class postcondition)."
 *   If there's an email-specific length limit in production, it should be
 *   handled at a HIGHER level, not silently in the sender.
 *
 * AUDIT:
 *   Adds exactly one entry ("email sent") per call, as the contract requires.
 */
public class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) { super(audit); }

    @Override
    public void send(Notification n) {
        // ─── Delivers body AS-IS — no truncation (honours base-class postcondition) ───
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
        audit.add("email sent");
    }
}