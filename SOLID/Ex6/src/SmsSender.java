/*
 * ─── LSP FIX: SmsSender ───
 *
 * ORIGINAL NOTE:
 *   SMS ignores the subject field — this is a legitimate CHANNEL LIMITATION,
 *   not a contract violation. SMS technology doesn't support subjects.
 *   The key point is: the BODY is delivered as-is (no truncation).
 *
 * HOW IT HONOURS THE CONTRACT:
 *   - Body is delivered without modification (postcondition honoured).
 *   - No exceptions thrown (postcondition honoured).
 *   - One audit entry ("sms sent") per call (contract honoured).
 *   - Subject being skipped is acceptable: the contract says body must be
 *     delivered, not that subject must be used by all channels.
 */
public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) { super(audit); }

    @Override
    public void send(Notification n) {
        // ─── SMS uses phone + body only (subject N/A for this channel — not a contract violation) ───
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
    }
}