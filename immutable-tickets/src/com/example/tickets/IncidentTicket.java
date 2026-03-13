package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * INTENTION: A ticket should be an immutable record-like object.
 *
 * FIXED:
 * - all fields are private final (no mutation after construction)
 * - no public setters
 * - tags list is wrapped in unmodifiableList (no external leak)
 * - single private constructor, only called from Builder
 * - all validation centralized in Builder.build()
 * - toBuilder() allows creating a modified copy without mutating the original
 */
public final class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;

    private final String description;
    private final String priority; // LOW, MEDIUM, HIGH, CRITICAL
    private final List<String> tags; // unmodifiable — no external leak
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes; // optional
    private final String source; // e.g. "CLI", "WEBHOOK", "EMAIL"

    // Only the Builder can construct an IncidentTicket
    private IncidentTicket(Builder b) {
        this.id = b.id;
        this.reporterEmail = b.reporterEmail;
        this.title = b.title;
        this.description = b.description;
        this.priority = b.priority;
        // defensive copy + unmodifiable so no caller can mutate internal state
        this.tags = Collections.unmodifiableList(new ArrayList<>(b.tags));
        this.assigneeEmail = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes = b.slaMinutes;
        this.source = b.source;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Returns a Builder pre-filled with this ticket's values so a "modified copy"
    // can be built
    public Builder toBuilder() {
        return Builder.from(this);
    }

    // Getters — no setters exist
    public String getId() {
        return id;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public List<String> getTags() {
        return tags;
    } // safe: already unmodifiable

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public boolean isCustomerVisible() {
        return customerVisible;
    }

    public Integer getSlaMinutes() {
        return slaMinutes;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }

    // -------------------------------------------------------------------------
    // Builder
    // -------------------------------------------------------------------------
    public static final class Builder {

        // Required fields
        private String id;
        private String reporterEmail;
        private String title;

        // Optional fields with sensible defaults
        private String description;
        private String priority;
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail;
        private boolean customerVisible;
        private Integer slaMinutes;
        private String source;

        private Builder() {
        }

        // Copy all values from an existing ticket — used by toBuilder()
        public static Builder from(IncidentTicket t) {
            Builder b = new Builder();
            b.id = t.id;
            b.reporterEmail = t.reporterEmail;
            b.title = t.title;
            b.description = t.description;
            b.priority = t.priority;
            b.tags = new ArrayList<>(t.tags); // mutable copy so we can addTag
            b.assigneeEmail = t.assigneeEmail;
            b.customerVisible = t.customerVisible;
            b.slaMinutes = t.slaMinutes;
            b.source = t.source;
            return b;
        }

        // Fluent setters
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder reporterEmail(String email) {
            this.reporterEmail = email;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String desc) {
            this.description = desc;
            return this;
        }

        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = (tags == null) ? new ArrayList<>() : new ArrayList<>(tags);
            return this;
        }

        public Builder addTag(String tag) {
            if (tag != null)
                this.tags.add(tag);
            return this;
        }

        public Builder assigneeEmail(String email) {
            this.assigneeEmail = email;
            return this;
        }

        public Builder customerVisible(boolean visible) {
            this.customerVisible = visible;
            return this;
        }

        public Builder slaMinutes(Integer sla) {
            this.slaMinutes = sla;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        // ALL validation is centralized here — single place to enforce all rules
        public IncidentTicket build() {
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");

            if (assigneeEmail != null) {
                Validation.requireEmail(assigneeEmail, "assigneeEmail");
            }

            Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");
            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");

            return new IncidentTicket(this);
        }
    }
}