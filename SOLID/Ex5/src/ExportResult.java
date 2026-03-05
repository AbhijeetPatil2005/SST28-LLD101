/*
 * ─── ExportResult (Result Object Pattern) ───
 *
 * WHY THIS CLASS EXISTS:
 *   Instead of using exceptions for failure (which breaks LSP when subtypes
 *   throw different exceptions), we use a RESULT OBJECT that can represent
 *   both success and failure.
 *
 * HOW IT WORKS:
 *   - Success: contentType + bytes + success=true.
 *   - Failure: errorMessage + success=false + empty bytes.
 *   - ExportResult.error("...") factory method for clean failure creation.
 *
 * KEY LSP BENEFIT:
 *   ALL exporters return ExportResult — callers check result.success uniformly.
 *   No try-catch, no instanceof, no format-specific error handling.
 *   This enables TRUE substitutability: swap any exporter and the caller
 *   code works identically.
 */
public class ExportResult {
    public final String contentType;
    public final byte[] bytes;
    public final boolean success;
    public final String errorMessage;

    public ExportResult(String contentType, byte[] bytes) {
        this.contentType = contentType;
        this.bytes = bytes;
        this.success = true;
        this.errorMessage = null;
    }

    private ExportResult(String errorMessage) {
        this.contentType = null;
        this.bytes = new byte[0];
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public static ExportResult error(String message) {
        return new ExportResult(message);
    }
}