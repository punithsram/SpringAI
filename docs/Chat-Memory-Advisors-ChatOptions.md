# Chat Memory, Advisors and Chat Options in Spring AI

# I will make changes on this

## Chat Memory

Chat Memory allows an AI application to remember previous conversations.
Instead of treating every request independently, Spring AI automatically
includes previous messages in the prompt so the model can respond with
context.

### Example

**Conversation**

``` text
User: My name is John.
Assistant: Nice to meet you!

User: What's my name?
```

Without chat memory, the model only receives:

``` text
User: What's my name?
```

With chat memory enabled, Spring AI automatically sends:

``` text
User: My name is John.
Assistant: Nice to meet you!
User: What's my name?
```
