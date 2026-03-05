/*
 * ─── LSP REFACTOR: Exporter (Abstract Base Class / Template Method) ───
 *
 * PROBLEM SOLVED:
 *   Before the refactor, subtypes violated the Liskov Substitution Principle (LSP):
 *   - PdfExporter THREW exceptions for large content (tightened preconditions).
 *     The base class didn't say "input must be < 20 chars", but PdfExporter
 *     added that restriction. Callers had to use try-catch ONLY for PDF.
 *   - CsvExporter SILENTLY CORRUPTED data by dropping commas/newlines
 *     (weakened postconditions — data loss).
 *   - JsonExporter handled nulls differently than others (inconsistent contract).
 *   Result: callers could NOT safely substitute one exporter for another.
 *   They needed instanceof checks and format-specific workarounds.
 *
 * WHAT IS LSP?
 *   Liskov Substitution Principle says: if S is a subtype of T, then objects
 *   of type T can be replaced with objects of type S without breaking the
 *   program. Subtypes must:
 *   - NOT tighten preconditions (don't reject inputs the base accepts).
 *   - NOT weaken postconditions (don't produce worse results than base promises).
 *   - NOT throw new exceptions the base doesn't declare.
 *
 * HOW WE FIXED IT (Template Method Pattern):
 *   1. export() is FINAL — subtypes can't override it.
 *   2. export() enforces COMMON precondition checks (req != null, fields != null).
 *   3. Subtypes implement doExport() which is called ONLY after preconditions pass.
 *   4. Contract is explicitly documented:
 *      - Preconditions: req, title, body must be non-null.
 *      - Postconditions: NEVER throw; return ExportResult.error() on failure.
 *        On success, data must be faithfully represented (no silent corruption).
 *
 * WHY TEMPLATE METHOD:
 *   - The FINAL export() method ensures ALL subtypes go through the same
 *     precondition checks. No subtype can skip or tighten them.
 *   - doExport() is the "customizable step" for format-specific logic.
 *   - This guarantees SUBSTITUTABILITY: any Exporter subtype behaves
 *     consistently from the caller's perspective.
 *
 * BENEFIT:
 *   - Caller never needs instanceof or try-catch — all exporters behave the same.
 *   - Adding a new exporter = extend Exporter, implement doExport(), honour contract.
 *   - Contract is documented and enforced, not just hoped for.
 */
public abstract class Exporter {

    // ─── FINAL: subtypes CANNOT override this. Ensures uniform precondition checking. ───
    public final ExportResult export(ExportRequest req) {
        // ── Common precondition checks (subtypes cannot tighten these) ──
        if (req == null) {
            return ExportResult.error("Request must not be null");
        }
        if (req.title == null || req.body == null) {
            return ExportResult.error("Title and body must not be null");
        }
        // Delegate to subtype for format-specific logic
        return doExport(req);
    }

    /*
     * Format-specific logic. Called ONLY after preconditions pass.
     * Subtypes MUST honour the postcondition:
     *   - Never throw; return ExportResult.error() on failure.
     *   - On success, faithfully represent the data (no silent data loss).
     */
    protected abstract ExportResult doExport(ExportRequest req);
}