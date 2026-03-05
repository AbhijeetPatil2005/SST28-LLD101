/*
 * ─── Demo05 — LSP Demonstration ───
 *
 * HOW LSP IS VISIBLE HERE:
 *   - All three exporters (PDF, CSV, JSON) are used through the SAME
 *     base type: Exporter.
 *   - The format() helper treats ALL results uniformly — no instanceof,
 *     no try-catch, no format-specific handling.
 *   - PDF returns an error result (large content), but it's handled the
 *     same way as a success. The caller doesn't need to know it's PDF.
 *
 * KEY INSIGHT:
 *   "No try-catch needed — the contract guarantees no exception."
 *   This ONE comment proves LSP is working: the base contract is reliable.
 */
public class Demo05 {
    public static void main(String[] args) {
        System.out.println("=== Export Demo ===");

        ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());
        Exporter pdf = new PdfExporter();
        Exporter csv = new CsvExporter();
        Exporter json = new JsonExporter();

        System.out.println("PDF: " + format(pdf.export(req)));
        System.out.println("CSV: " + format(csv.export(req)));
        System.out.println("JSON: " + format(json.export(req)));
    }

    /** No try-catch needed — the contract guarantees no exception. */
    private static String format(ExportResult result) {
        if (!result.success) {
            return "ERROR: " + result.errorMessage;
        }
        return "OK bytes=" + result.bytes.length;
    }
}