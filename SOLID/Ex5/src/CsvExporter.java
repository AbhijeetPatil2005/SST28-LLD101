import java.nio.charset.StandardCharsets;

/*
 * ─── LSP FIX: CsvExporter ───
 *
 * ORIGINAL PROBLEM:
 *   The old CsvExporter SILENTLY CORRUPTED data by poorly handling commas
 *   and newlines in field values. If a title had a comma, it would break
 *   the CSV structure. This WEAKENED the postcondition — the base contract
 *   promises "data faithfully represented", but CSV was losing/changing data.
 *
 * HOW WE FIXED IT:
 *   Implemented proper RFC 4180 CSV quoting:
 *   - Fields containing commas, newlines, or double-quotes are wrapped in quotes.
 *   - Internal double-quotes are escaped as "".
 *   This ensures NO DATA LOSS — honouring the base-class postcondition.
 *
 * KEY LSP POINT:
 *   The caller passes any string and gets back a valid CSV with no data loss.
 *   CsvExporter is now fully substitutable — it behaves as the base promises.
 */
public class CsvExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        // Proper RFC 4180 quoting — no data loss.
        String csv = "title,body\n" + quoteCsv(req.title) + "," + quoteCsv(req.body) + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }

    /** Quotes a field if it contains comma, newline, or double-quote. */
    private String quoteCsv(String s) {
        if (s.contains(",") || s.contains("\n") || s.contains("\"")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }
}