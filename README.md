# recursive-descent-parser
The task is to write lexical analyzer and parser for "for loop" in C-like languages.

### Grammar
Grammar and FIRST- and FOLLOW-sets are explained [here](Grammar.pdf).

### How to run?
To run parser, which generates output for visualization for [WebGraphViz](http://www.webgraphviz.com) do:
```java
bash run.sh
```
or
```java
chmod +x run.sh
./run
```

To run lexical analyzer to see tokens do:
```java
bash run_la.sh
```
or
```java
chmod +x run_la.sh
./run_la
```

To run tests do:
```java
bash run_tests.sh
```
or
```java
chmod +x run_tests.sh
./run_tests
```
