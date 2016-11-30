# Shop

[![Continuous Integration status](https://secure.travis-ci.org/JulesGosnell/shop.png)](http://travis-ci.org/JulesGosnell/shop)

## Introduction

A short piece of Scala demo code.

Thoughts:

I've used BigDecimal to represent money - this is a habit from working in financial institutions. Float would have sufficed for this example.

I didn't bother carrying around currency (GBP / £) with the amounts represented in the program. Since all amounts were given in GBP it seemed simpler to leave this implicit to be realised e.g. in the presentation of these amounts rather than in their implementation.

I haven't hardwired details like the stock and their prices into the production code, but have put these into the test code. In production, I would expect these details to be loaded from some other system and subject to frequent change.

