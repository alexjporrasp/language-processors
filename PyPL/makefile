default:
	java -jar ../java-cup/java-cup-11b.jar PYPLC.cup
	java -jar ../jflex/jflex-1.6.1.jar PYPLC.flex
	javac -classpath ../java-cup/java-cup-11b-runtime.jar *.java
	java -classpath .:../java-cup/java-cup-11b-runtime.jar PYPLC prog.pl prog.ctd
	rm *.class sym.java parser.java Yylex.java
