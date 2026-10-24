# Java LRU Cache Simulator

A Java-based implementation of a Least Recently Used (LRU) cache using a HashMap and a doubly linked list. This project demonstrates efficient cache operations, eviction, and performance measurement.

## Overview

Caching stores recently accessed data for faster retrieval. Because a cache has limited capacity, an eviction policy is required when it becomes full.

This project implements an LRU cache that evicts the least recently used entry when a new entry must be inserted into a full cache.

The project also includes a simulation that generates 10,000 queries and reports cache hits, misses, and their ratios.

## Features

- LRU eviction policy
- Average O(1) `get()` and `put()` operations
- HashMap-based key lookup
- Doubly linked list for tracking usage order
- Cache hit and miss statistics
- Reproducible request simulation
- Automated tests for core operations and edge cases
- Makefile for compilation, execution, testing, and cleanup

## Technologies Used

- Java
- HashMap
- Doubly linked list
- Java Random
- Makefile
- Git and GitHub

## Project Structure

```text
lru-cache-simulator/
├── src/
│   ├── LRUCache.java
│   └── Simulation.java
├── tests/
│   └── LRUCacheTest.java
├── Makefile
├── README.md
└── build/
```

The `build/` directory is generated when the project is compiled.

## Architecture

### LRUCache.java

Implements the cache operations, node management, eviction policy, and hit/miss counters.

### Simulation.java

Generates requests, simulates fetching data on cache misses, and calculates cache performance metrics.

### LRUCacheTest.java

Tests cache insertion, retrieval, eviction, updates, statistics, and edge cases.

### Makefile

Provides commands to compile, run, test, and clean the project.

## How It Works

The cache combines two data structures.

### HashMap

The HashMap maps each key to its corresponding node, allowing average O(1) lookup.

### Doubly Linked List

The list maintains entries in Most Recently Used (MRU) to Least Recently Used (LRU) order.

- Accessing an existing entry moves it to the front.
- Updating an existing entry moves it to the front.
- Inserting into a full cache evicts the least recently used entry.
- Evicted entries are removed from both the linked list and the HashMap.

### Example

Suppose the cache contains:

```text
MRU → [3:30] → [2:20] → [1:10] → LRU
```

After accessing key `1`:

```text
MRU → [1:10] → [3:30] → [2:20] → LRU
```

If a new key is inserted into this full cache, key `2` is evicted.

## Time and Space Complexity

| Operation | Complexity |
|---|---|
| `get(key)` | Average O(1) |
| `put(key, value)` | Average O(1) |
| Remove a known node | O(1) |
| Insert at the front | O(1) |
| Cache storage | O(C) |

Here, `C` represents the cache capacity. HashMap operations have average O(1) complexity under typical conditions.

## Simulation Configuration

| Parameter | Value |
|---|---:|
| Cache capacity | 100 |
| Measured queries | 10,000 |
| General key range | 0–199 |
| Hot-key range | 0–49 |
| Hot-key request probability | 80% |
| Other request probability | 20% |
| Random seed | 42 |

The simulation initially loads keys `0` through `99` into the cache. It then processes 10,000 queries.

The simulated data source returns `key * 10`. It does not connect to a real database.

## Performance Metrics

The simulation reports:

- Total queries
- Cache hits
- Cache misses
- Hit ratio
- Miss ratio

Hit ratio:

```text
(Cache Hits / Total Queries) × 100
```

Miss ratio:

```text
(Cache Misses / Total Queries) × 100
```

The fixed random seed makes the request sequence reproducible.

## Prerequisites

Install a Java Development Kit (JDK) and GNU Make.

Verify the installations:

```bash
java -version
javac -version
make --version
```

## Installation

Clone the repository:

```bash
git clone https://github.com/kuldeepcodex/lru-cache-simulator.git
```

Navigate to the project:

```bash
cd lru-cache-simulator
```

## Usage

Compile the source and test files:

```bash
make all
```

Run the simulation:

```bash
make run
```

Run the tests:

```bash
make test
```

Remove generated build files:

```bash
make clean
```

## Testing

The test suite covers:

- Basic insertion and retrieval
- Missing keys
- LRU eviction
- Updating existing entries
- Recent-access protection
- Capacity-one behavior
- Hit and miss statistics
- Update recency
- Zero-capacity behavior
- List order after a cache miss
- Ensuring `put()` does not affect hit/miss counters

## Design Decisions

**Why use a HashMap?**

It allows the cache to locate a node by key without scanning the linked list.

**Why use a doubly linked list?**

It allows efficient removal of a known node and insertion at the front using `prev` and `next` references.

**Why use dummy head and tail nodes?**

They simplify node insertion and removal by reducing special cases at the boundaries.

**Why use LRU?**

Recently accessed entries may be requested again, so LRU keeps them in the cache while removing less recently used entries when necessary.

## Future Improvements

- Add thread safety for concurrent access.
- Support configurable cache capacities and eviction policies.
- Benchmark LRU against FIFO.
- Add eviction counters and request-latency measurements.
- Replace the `-1` miss indicator with a dedicated result type.
- Add GitHub Actions for automated builds and tests.
- Integrate a real data source.

## Learning Outcomes

This project demonstrates practical understanding of Java, HashMap, doubly linked lists, cache design, algorithm complexity, testing, and build automation.

## Author

**Kuldeep Nagar**

GitHub: [kuldeepcodex](https://github.com/kuldeepcodex)

---

Developed as a learning project to demonstrate cache management and data-structure efficiency in Java.
