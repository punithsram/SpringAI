# Spring AI Demo

A Spring Boot application demonstrating Spring AI with Ollama.

## Features

- Chat with Ollama
- Chat Memory
- Chat Options
- Prompt Templates
- Structured Output
- Prompt Engineering

## Technologies

- Java 17 or above
- Spring Boot 3.x
- Spring AI
- Ollama
- Maven

## Prerequisites

- Java 17
- Maven
- Ollama installed in locally

## Install Ollama

```bash
ollama pull mistral
```

or

```bash
ollama pull mistral
```

## Configuration

```properties
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=mistral
```

## API

### 1. Chat  AskMeAnything

```
POST /ask
```

### 2. Cuisine Helper

```
POST /cuisineHelper
```

Example:

```
POST /cuisineHelper?country=India&numCuisines=3&language=English&conversationID=123
```
### 3.Travel guide Helper

```
POST /travelGuide
```

Example:

```
POST /travelGuide?city=India&month=June&language=English&budget=Luxury&conversationID=12
```


## Author

Punith S Ram Gowda 