import java.nio.charset.StandardCharsets;

/*
 * ─── LSP FIX: PdfExporter ───
 *
 * ORIGINAL PROBLEM:
 *   The old PdfExporter THREW an exception when content was too long.
 *   This TIGHTENED the precondition compared to the base Exporter, which
 *   accepted any non-null request. Callers had to wrap PDF calls in
 *   try-catch — breaking substitutability (LSP violation).
 *
 * HOW WE FIXED IT:
 *   Instead of throwing, PdfExporter now returns ExportResult.error(...)
 *   with a descriptive message. This HONOURS the base-class postcondition:
 *   "Never throw; return an error result on failure."
 *   The caller handles success/error UNIFORMLY for all exporters.
 *
 * KEY LSP POINT:
 *   PdfExporter still can't handle large content — that's a real limitation.
 *   But it communicates this GRACEFULLY via the error result, consistent
 *   with the base contract. No surprises for the caller.
 */
public class PdfExporter extends Exporter {
    private static final int MAX_BODY_LENGTH = 20;

    @Override
    protected ExportResult doExport(ExportRequest req) {
        // Documented limitation — returns error result instead of throwing.
        if (req.body.length() > MAX_BODY_LENGTH) {
            return ExportResult.error(
                    "PDF cannot handle content > " + MAX_BODY_LENGTH + " chars");
        }
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf",
                fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}