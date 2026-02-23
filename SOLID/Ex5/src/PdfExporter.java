import java.nio.charset.StandardCharsets;

public class PdfExporter extends Exporter {
    private static final int MAX_CONTENT_LENGTH = 20;

    @Override
    public boolean canExport(ExportRequest req) {
        return req != null && req.body != null && req.body.length() <= MAX_CONTENT_LENGTH;
    }

    @Override
    public ExportResult export(ExportRequest req) {
        if (!canExport(req)) {
            throw new IllegalArgumentException("PDF cannot handle content > " + MAX_CONTENT_LENGTH + " chars");
        }
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
