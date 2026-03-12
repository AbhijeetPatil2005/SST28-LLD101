/*
 * ─── LSP FIX: WhatsAppSender ───
 *
 * ORIGINAL PROBLEM:
 *   The old WhatsAppSender THREW an exception when the phone number didn't
 *   start with "+". This TIGHTENED the precondition: the base class accepted
 *   any non-null notification, but WhatsApp added an extra requirement.
 *   Callers needed try-catch ONLY for WhatsApp — breaking substitutability.
 *
 * HOW WE FIXED IT:
 *   Instead of throwing, WhatsAppSender now:
 *   1. Validates the phone number gracefully.
 *   2. If invalid: prints a diagnostic message and logs "WA failed" to audit.
 *   3. NEVER throws an exception.
 *   This honours the base contract: "If delivery fails, print diagnostic
 *   and log to audit — never throw."
 *
 * KEY LSP POINT:
 *   The caller doesn't need to know it's WhatsApp. It calls send() and
 *   the result is either success or a graceful logged failure. Same
 *   behavior pattern as all other senders.
 */
public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) { super(audit); }

    @Override
    public void send(Notification n) {
        // ─── Validates gracefully — NEVER throws (honours base-class contract) ───
        if (n.phone == null || !n.phone.startsWith("+")) {
            // Graceful failure: print diagnostic + log to audit (instead of throwing)
            System.out.println("WA ERROR: phone must start with + and country code");
            audit.add("WA failed");
            return;
        }
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }
}