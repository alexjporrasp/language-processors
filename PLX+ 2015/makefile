default:
	java -jar ../java-cup/java-cup-11b.jar PLXC.cup
	java -jar ../jflex/jflex-1.6.1.jar PLXC.flex
	javac -classpath ../java-cup/java-cup-11b-runtime.jar *.java
	java -classpath .:../java-cup/java-cup-11b-runtime.jar PLXC prog.plx prog.ctd
	rm *.class sym.java parser.java Yylex.java
