import java.nio.charset.StandardCharsets;

/*
 * ─── LSP FIX: JsonExporter ───
 *
 * ORIGINAL PROBLEM:
 *   The old JsonExporter handled null inputs differently than other exporters
 *   (returned empty instead of error). This was INCONSISTENT with the base
 *   contract, breaking substitutability.
 *
 * HOW WE FIXED IT:
 *   - Null checks are now handled by the BASE CLASS (Exporter.export() is final).
 *     JsonExporter's doExport() is only called with non-null values.
 *   - Proper JSON escaping handles special characters (\, ", \n, \r, \t)
 *     so no data is silently lost — honouring the postcondition.
 *
 * KEY LSP POINT:
 *   All exporters now handle nulls the same way (base class rejects them).
 *   JsonExporter focuses purely on JSON encoding, trusting the base to
 *   enforce preconditions.
 */
public class JsonExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        String json = "{\"title\":\"" + escapeJson(req.title)
                    + "\",\"body\":\"" + escapeJson(req.body) + "\"}";
        return new ExportResult("application/json",
                json.getBytes(StandardCharsets.UTF_8));
    }

    /** Escapes characters that are special inside a JSON string value. */
    private String escapeJson(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}