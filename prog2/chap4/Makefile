JFLAGS=-g

all: Parse/*.class
	javac ${JFLAGS} ErrorMsg/*.java Absyn/*.java Symbol/*.java

Parse/*.class: Parse/*.java Parse/Grm.java Parse/Yylex.java
	javac ${JFLAGS} Parse/*.java

Parse/Grm.java: Parse/Grm.cup
	cd Parse; java java_cup.Main -parser Grm -expect 3 -nonterms -dump_grammar -dump_states <Grm.cup >Grm.out 2>Grm.err

Parse/Yylex.java: Parse/C.lex Parse/sym.java
	cd Parse; java JLex.Main C.lex; mv C.lex.java Yylex.java

clean:
	rm -f */*.class Parse/Grm.java Parse/Grm.err Parse/Grm.out Parse/Yylex.java
