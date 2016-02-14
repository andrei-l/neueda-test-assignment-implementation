# Test assignment implementation for Neueda
My implementation of a test assignment for [Neueda](https://github.com/neueda/homework/tree/master/mindmap), Latvia @ 25.01.2015

### Implementation details
In order to launch app please use next command:
* ````java -jar web-service-testing-jar-with-dependencies.jar <path to valid .mm file> <URL to valid website>````

e.g.
 * ````java -jar web-service-testing-jar-with-dependencies.jar C:\Projects\neueda\web-service-testing\src\test\resources\calc_tests.mm http://calculator.neueda.lv/````

## Neueda Homework - Mind Maps

You have to write a test automation solution that uses mind maps
to generate and execute tests against a remote REST service.

REST service endpoint location is:
http://calculator.neueda.lv/api/

The service represents a simple calculator that performs 4 basic arithmetic
operations:
* addition,
* subtraction,
* division,
* multiplication.

This service uses JSON as the data exchange format.

A web UI for this REST service is found at:
http://calculator.neueda.lv/

You can use the UI and browser development tools to find out what is the
exact query format.

[Provided mind map](https://github.com/neueda/homework/blob/master/mindmap/calc_tests.mm) describes test cases for the REST service.

![Mind Map](https://github.com/neueda/homework/blob/master/mindmap/mindmap.png)

Node levels have the following meaning:

1. Test suite name
2. Test case name or 'Request' keyword
3. Test arguments and expected result or service call method and path

The test suite for each operation includes the path to appropriate operation,
and input/output parameters (names and values).
So, basically, by parsing the mind map you will get 9 test cases with test
parameters and results.
The task is to execute the service call and assert that the result is as
expected.

You can open \*.mm files with [FreeMind](http://freemind.sourceforge.net/).

## Requirements

1. Your code should compile and run on the Java VM. Any language or approach can be used.
2. It should be possible to generate tests from mindmap file and execute them using command line.
3. User should be able to see test results, somehow (console is ok).
4. You can use any libraries and/or frameworks you need.
5. The provided mind map is just an example. You have to support similar tests for the same calculator system.

The main value for us is the quality of your code and the elegance of your
solution. Try not to reinvent stuff, if it's already open source!
