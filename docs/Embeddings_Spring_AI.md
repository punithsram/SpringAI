# Why Do We Use Embeddings in Spring AI?

Embeddings are one of the core concepts in AI applications such as
semantic search and Retrieval-Augmented Generation (RAG).

## What is an Embedding?

An embedding converts text into a numerical vector (an array of
floating-point numbers).

Example:

**Input**

``` text
Spring AI is awesome
```

**Output**

``` text
[0.12, -0.45, 0.78, 0.34, ...]
```

These numbers represent the **meaning** of the text rather than the
actual words.

------------------------------------------------------------------------

## Why Do We Need Embeddings?

Embeddings help computers understand the **semantic meaning** of text
instead of relying on exact keyword matching.

### Without Embeddings (Keyword Search)

Suppose your documents are:

``` text
Document 1:
Spring AI helps build AI applications.

Document 2:
Java Streams process collections.

Document 3:
Spring Boot simplifies REST APIs.
```

User asks:

``` text
How do I integrate artificial intelligence into Spring?
```

A keyword search looks for words like:

- integrate
- artificial
- intelligence
- Spring

It may fail to find **Document 1** because it contains **AI** instead of
**artificial intelligence**.

------------------------------------------------------------------------

### With Embeddings (Semantic Search)

The embedding model converts both the question and documents into
vectors.

``` text
Question
↓
Vector
↓
[0.21, -0.34, ...]
```

``` text
Document 1
↓
Vector
↓
[0.20, -0.35, ...]
```

Since the vectors are very close, the system understands that both have
similar meaning.

------------------------------------------------------------------------

## Real-World Example

Imagine an online shopping application.

Products:

``` text
Gaming Laptop
MacBook Pro
iPhone
```

User searches:

``` text
Laptop for programming
```

A keyword search may miss relevant products.

An embedding search understands that both **Gaming Laptop** and
**MacBook Pro** are suitable recommendations because it compares meaning
instead of exact words.

------------------------------------------------------------------------

## Why Not Send All Documents to the LLM?

Imagine you have **100,000 company documents**.

Sending every document to the LLM would be:

- Slow
- Expensive
- Limited by the model's context window

Instead:

``` text
User Question
      │
      ▼
Embedding Model
      │
      ▼
Vector Search
      │
Retrieve Top 5 Documents
      │
      ▼
LLM
      │
      ▼
Final Answer
```

Only the most relevant documents are sent to the LLM.

------------------------------------------------------------------------

## Library Analogy

Without embeddings:

You ask:

> I need a book about fixing cars.

A keyword search looks for:

- fixing
- cars

It may not find:

``` text
Automobile Repair Guide
```

With embeddings:

The system understands:

- cars ≈ automobiles
- fixing ≈ repair

and returns the correct book.

------------------------------------------------------------------------

## Common Use Cases

Embeddings are widely used in:

- Retrieval-Augmented Generation (RAG)
- Semantic Search
- Recommendation Systems
- AI Chatbots
- Duplicate Detection
- Document Similarity
- Product Search

------------------------------------------------------------------------

## Spring AI Embedding Flow

### Indexing Documents

``` text
Document
    │
    ▼
Embedding Model
    │
    ▼
Vector
    │
    ▼
Vector Database
```

### Answering a User Question

``` text
User Question
      │
      ▼
Embedding Model
      │
      ▼
Question Vector
      │
      ▼
Similarity Search
      │
      ▼
Relevant Documents
      │
      ▼
Chat Model
      │
      ▼
Final Answer
```

------------------------------------------------------------------------

## Interview Answer

> Embeddings convert text into numerical vectors that capture semantic
> meaning. They enable semantic search and similarity search, allowing
> AI systems to retrieve the most relevant information instead of
> relying on exact keyword matching.

------------------------------------------------------------------------

## Summary

- Embeddings convert text into vectors.
- Vectors represent the meaning of text.
- Similar texts produce similar vectors.
- Embeddings power semantic search and RAG.
- Spring AI uses embedding models to create vectors and vector
  databases to perform similarity searches before sending relevant
  context to a chat model.
