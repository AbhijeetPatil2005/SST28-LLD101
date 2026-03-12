import java.util.*;

/*
 * ─── AuditLog — Shared Audit Trail ───
 *
 * WHY THIS CLASS EXISTS:
 *   All senders share one audit log to track what happened. The base
 *   contract requires "exactly one audit entry per send() call".
 *   This enables verification that all senders behaved correctly —
 *   audit.size() should equal the number of send() calls.
 */
public class AuditLog {
    private final List<String> entries = new ArrayList<>();
    public void add(String e) { entries.add(e); }
    public int size() { return entries.size(); }
}