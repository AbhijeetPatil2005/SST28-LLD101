package com.example.tickets;

/**
 * Service layer that creates and "updates" tickets.
 *
 * FIXED:
 * - no mutation after creation
 * - all construction goes through the Builder
 * - validation happens inside Builder.build(), not here
 * - escalateToCritical and assign return NEW ticket instances (immutable update
 * pattern)
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        // Build a fully valid ticket in one shot — validation runs inside build()
        return IncidentTicket.builder()
                .id(id)
                .reporterEmail(reporterEmail)
                .title(title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .addTag("NEW")
                .build();
    }

    // Returns a NEW ticket with CRITICAL priority and ESCALATED tag — original is
    // untouched
    public IncidentTicket escalateToCritical(IncidentTicket t) {
        return t.toBuilder()
                .priority("CRITICAL")
                .addTag("ESCALATED")
                .build();
    }

    // Returns a NEW ticket with assignee set — original is untouched
    public IncidentTicket assign(IncidentTicket t, String assigneeEmail) {
        return t.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}