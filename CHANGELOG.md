# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.

## 2025.09.18

### Added
- Designed a Mini Statistics Toolkit component
- Designed a Music Playlist Analyzer component
- Designed a Survey Response Analyzer component


## [Proof of Concept] - 2025-10-08
### Added
- Created `MiniStatisticsToolkitKernel.java` with basic operations (`addData`, `removeLast`, `length`).
- Created `MiniStatisticsToolkit.java` with one secondary method `mean()`.
- Created `MiniStatisticsToolkitTest.java` to demonstrate component behavior.
- Linked OSU `components.jar` successfully.


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