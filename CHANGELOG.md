# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.

## [Kernel Implementation] - 2025-11-20
### Added
- Implemented `MiniStatisticsToolkit1L.java`:
  - Completed full kernel implementation using a dynamically resizing `double[]` representation.
  - Implemented all kernel methods: `addData`, `removeLast`, `length`, and `entries`, following OSU CSE component design rules.
  - Added internal iterator class `ArrayEntries` for read-only traversal using the `Entries` interface.
  - Implemented `Standard` methods (`clear`, `newInstance`, and `transferFrom`) consistent with component discipline.
  - Included detailed representation, convention, and correspondence specifications at the top of the file.

## [Component Abstract Class] - 2025-11-06
### Added
- Implemented `MiniStatisticsToolkitSecondary.java`:
  - Provides secondary (abstract) implementations for all enhanced interface methods (`mean`, `min`, `max`, `stddev`, and `movingAverage`).
  - Includes complete Javadoc design-by-contract specifications with `@requires` and `@ensures` clauses.
  - Implements `toString`, `equals`, and `hashCode` using kernel-level iterators.
- Ensured all methods depend strictly on kernel operations (`entries()`, `length()`), without representation access.

### Notes
- This update finalizes the **secondary (abstract class)** layer for the Mini Statistics Toolkit.
- Next step: implement the concrete representation class (`MiniStatisticsToolkit1L`) for Part 4.
- Verified contracts compile cleanly and all methods follow OSU CSE component design standards.



## [Component Interfaces] - 2025-10-23
### Added
- Completed full kernel and enhanced interfaces for **MiniStatisticsToolkit**:
  - Added `MiniStatisticsToolkitKernel.java` with `Entries` iterator and formal @requires/@ensures annotations.
  - Added `MiniStatisticsToolkit.java` (enhanced interface) defining `mean`, `min`, `max`, `stddev`, and `movingAverage`.
- Updated JavaDoc formatting to design-by-contract style.
- Verified interface structure compiles correctly with no package declaration in default `src` folder.

### Notes
- Implementation class `MiniStatisticsToolkit1L` will be completed in Part 4.
- No behavioral code changes yet; focus of this release is interface design and documentation quality.


## [Proof of Concept] - 2025-10-08
### Added
- Created `MiniStatisticsToolkitKernel.java` with basic operations (`addData`, `removeLast`, `length`).
- Created `MiniStatisticsToolkit.java` with one secondary method `mean()`.
- Created `MiniStatisticsToolkitTest.java` to demonstrate component behavior.
- Linked OSU `components.jar` successfully.



## [Component Brainstorming] - 2025-09-18

### Added
- Designed a Mini Statistics Toolkit component
- Designed a Music Playlist Analyzer component
- Designed a Survey Response Analyzer component
