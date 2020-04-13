# Java doubly-linked hash map
A basic implementation of a doubly-linked hash map. 
* Similar to LinkedHashMap, but allows for efficient iteration in both forward and reverse order (at the same time!).
* Only iteration over values is currently supported. This doesn't implement any standard Java collections interfaces.
* Only basic operations, such as get, putBack and putFront are supported. 
* This implementation is NOT thread-safe.
* Modifying this map while iterating over it will yield undefined behaviour.

This is by all means not a complete implementation, 
but it provides all the functionality I needed at the time of implementing this.
