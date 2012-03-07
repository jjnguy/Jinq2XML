##Intro

Parsing XML in Java sucks.  Jinq2XML aims to make it better.

The goal of this project is to provide a _small_ library that makes reading XML a snap.  All classes have a minimal number of public methods that have many helpful overloads.

If you like simplicity then this is your new XML parser for Java.  If you are looking for a bunch of silly features that no one ever uses then you should look elsewhere.

The current scope of the library is for reading XML only.  Future iterations of the project may add the ability to edit XML.

In general, the library is loosely based off of .Net's LINQ to XML and JQuery-like method chaining.

##Features

The goal of this library is not to be the most feature-full XML parsing library out there.  That said, here is a short list of some of the features this library provides:

  * Small and simple library
  * Fluent syntax
  * Easy entry points
  * XPath functionality
  * JQuery-like chaining
  * Linq2XML-like syntax
  * Automatically parse xml into Classes (beta)
  * Generate source classes from XML (super beta)

Those are the main features of the library.  Any requests for more functionality should be sent to the owner of the project.

##Getting Started

To begin call `Jocument.load()` and either give it an `InputStream` or a file location, or use `Jocument.parse()` and give it a `String` containing valid XML.  Then start calling methods on the `Jocument`.  Everything should 'just work' without any configuration or setup.

##More Info!

Jinq2XML is essentially a wrapper around Java's built-in DOM XML parser.  All Jinq objects are backed by Java's DOM objects. (i.e Jocument is backed by a Document)

Also, the naming convention of prefixing each type with a 'J' comes from the word 'Java,' not my name.

##Feedback

Please email me at jjnguy13 at gmail with any feedback you may have on this library.