# Dependency security review (2026-10-11)

## Guava

Guava was used only for array iterators and quiet stream closing. It has
been removed, not added to a scanner ignore list. Standard Java iterators
retain the unmodifiable card-holder behavior; try-with-resources closes the
hand-ranking stream. Loading now reads the entire expected table and fails
on a missing or truncated resource rather than using incomplete data.

## Spring CVE-2026-47890 / GHSA-j9f9-w8pj-32f8

Vendor advisory: https://spring.io/security/cve-2026-47890/
Compatibility: https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions

Socket/GitHub marks this critical; the vendor rates it low. This discrepancy
does not make the dependency patched. Spring 6.2.11 remains in the affected
version range.

The vendor requires SSE streaming of view fragments and attacker-controlled
data sent to other users. Inspection of the controllers and MVC XML found
ordinary JSON responses and no SSE endpoints, SseEmitter, ResponseBodyEmitter,
FragmentsRendering, WebFlux or text/event-stream declarations. Consequently,
this specific exploit path does not appear reachable in the current code.
This is a source-level applicability assessment, not a comprehensive audit
or a claim that Spring 6.2.11 is free of other vulnerabilities.

The vendor lists Spring 6.2.20 as enterprise-only and 7.0.9 as the open-source
fix. Spring 7 requires Servlet 6.1 and JPA 3.2; Tomcat 10.1 provides Servlet
6.0. Do not simply bump Spring 7 into this WAR or package a newer Servlet API
inside Tomcat 10 to disguise the runtime mismatch.

Decision required: authorize a Tomcat 11 / Spring 7 migration, supply an
appropriately licensed Spring 6 patched dependency, or explicitly accept a
documented non-applicability decision for this particular alert while keeping
the server on a trusted LAN. No alert has been suppressed, and the PR stays
draft pending this decision. Reassess before adding any SSE/view-fragment
features. The API's missing authentication is a separate outstanding risk.
