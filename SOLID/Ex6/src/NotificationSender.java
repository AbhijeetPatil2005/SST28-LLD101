/*
 * ─── LSP REFACTOR: NotificationSender (Abstract Base Class) ───
 *
 * PROBLEM SOLVED:
 *   Before the refactor, subtypes violated LSP in multiple ways:
 *   - EmailSender SILENTLY TRUNCATED messages (weakened postcondition —
 *     the body was supposed to be delivered as-is, but email cut it short).
 *   - WhatsAppSender REJECTED non-E.164 phone numbers by throwing an
 *     exception (tightened precondition — the base didn't require "+" prefix).
 *   - SmsSender IGNORED the subject field (inconsistent behavior).
 *   Result: callers couldn't safely substitute one sender for another.
 *   They needed subtype-specific try-catch blocks and format checks.
 *
 * WHAT IS LSP (Liskov Substitution Principle)?
 *   If S is a subtype of T, then objects of type S should be usable wherever
 *   T is expected WITHOUT breaking the program. Subtypes must:
 *   - NOT tighten preconditions (don't reject inputs the base accepts).
 *   - NOT weaken postconditions (don't produce worse output than base promises).
 *   - NOT throw unexpected exceptions.
 *
 * HOW WE FIXED IT:
 *   1. Defined a CLEAR CONTRACT in this base class:
 *      Pre:  n is non-null with a non-null body.
 *      Post: body is delivered as-is (no silent truncation or alteration).
 *            If delivery fails (e.g., invalid address), print a diagnostic
 *            and log to audit — NEVER throw an exception.
 *            Exactly one audit entry per call.
 *   2. All subtypes now HONOUR this contract:
 *      - EmailSender delivers body without truncation.
 *      - WhatsAppSender handles invalid phones gracefully (prints error,
 *        logs to audit) instead of throwing.
 *      - SmsSender skips subject (channel limitation) but delivers body as-is.
 *
 * WHY ABSTRACT CLASS (not interface):
 *   The `audit` field is common to ALL senders — using an abstract class
 *   lets us share this state and constructor logic. Each subtype inherits
 *   the audit reference and adds exactly one entry per send() call.
 *
 * KEY LSP BENEFIT:
 *   The caller (Demo06) uses NotificationSender references for ALL channels.
 *   "No try-catch needed: base contract guarantees no exception." — this
 *   is the proof that substitutability works.
 */
public abstract class NotificationSender {
    protected final AuditLog audit;
    protected NotificationSender(AuditLog audit) { this.audit = audit; }
    public abstract void send(Notification n);
}