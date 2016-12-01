# Shop

[![Continuous Integration status](https://secure.travis-ci.org/JulesGosnell/shop.png)](http://travis-ci.org/JulesGosnell/shop)

## Introduction

A short piece of Scala demo code.

Thoughts:

I've used BigDecimal to represent money - this is a habit from working in financial institutions. Float would have sufficed for this example.

I didn't bother carrying around currency (GBP / £) with the amounts represented in the program. Since all amounts were given in GBP it seemed simpler to leave this implicit to be realised e.g. in the presentation of these amounts rather than in their implementation.

I haven't hardwired details like the stock and their prices into the production code, but have put these into the test code. In production, I would expect these details to be loaded from some other system and subject to frequent change.

Making Shop accept input as a list of strings feels wrong. I think that this would sit better in an e.g. input layer, rather than in Shop.

I've interpreted the word 'system' in 'build a system' as a system of s/w components, not as  a CLI program. Maybe the latter was what was expected, hence the string input ? This would require the simple addition of a 'main()' function in the Shop companion object and the transplanting of the initialising of a shop and the calling of checkOutByName from the test code - only a couple of lines of code.

I'd like to have tested Shop.basketWithoutFreeItems() directly, but I also needed to test that its fn-ality was wired into Shop.checkOut() and checkOutByName(). I decided to allow these tests to subsume the direct one. With a little more time, perhaps I could have decomposed the problem further so that all units and their integration could have been tested without duplication.

