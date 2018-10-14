import java_cup.runtime.*;
import java.util.*;

%%

%unicode
%cup
%line
%column

%{
	private int current_indent;
	Stack<Integer> stack = new Stack<Integer>();

	private Symbol symbol(int type) {
		return new Symbol(type, yyline, yycolumn);
	}

	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline, yycolumn, value);
	}
%}

%state BLOCK

%init{
	stack.push(0);
	current_indent = 0;
	yybegin(BLOCK);
%init}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = [ \t\f]

// comments
Comment = {ApostComment} | {HashComment}

// Comment can be the last line of the file, without line terminator
ApostComment      = "'''"[^(''')]*"'''"
HashComment       = "#" {InputCharacter}* {LineTerminator}

indentifier =	[:jletter:] [:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*


%%

/* Expressions and rules */
<YYINITIAL> {
	// reserved Words
	"print"							{ return symbol(sym.PRINT); }
	"if"								{ return symbol(sym.IF); }
	"elif"							{ return symbol(sym.ELIF); }
	"else"							{ return symbol(sym.ELSE); }
	"while"							{ return symbol(sym.WHILE); }
	"for"								{ return symbol(sym.FOR, Gen.genTag()); }
	"in"								{ return symbol(sym.IN); }
	"range"							{ return symbol(sym.RANGE); }

	// separators
	"("									{ return symbol(sym.LPAREN); }
	")"									{ return symbol(sym.RPAREN); }
	";"									{ return symbol(sym.SEMI); }
	","									{ return symbol(sym.COMMA); }
	":"									{ return symbol(sym.COLON); }

	// operators
	"+"									{ return symbol(sym.PLUS); }
	"-"									{ return symbol(sym.MINUS); }
	"*"									{ return symbol(sym.TIMES); }
	"/"									{ return symbol(sym.DIVIDE); }
	"**"								{ return symbol(sym.POWER); }
	"//"								{ return symbol(sym.DIVIDE); }
	"%"									{ return symbol(sym.MOD); }
	"="									{ return symbol(sym.EQUAL); }
	">"									{ return symbol(sym.MORE); }
	"<"									{ return symbol(sym.LESS); }
	"=="								{ return symbol(sym.BEQ); }
	"<="								{ return symbol(sym.LEQ); }
	">="								{ return symbol(sym.MOEQ); }
	"!="								{ return symbol(sym.NEQ); }

	// numbers
	{DecIntegerLiteral}	{ return symbol(sym.INTEGER, yytext()); }

	// indentifiers
	{indentifier}				{ return symbol(sym.IDENT, yytext()); }

	"."									{ return symbol(sym.FULLSTOP); }

	{WhiteSpace}				{ /* ignore whitespaces */ }


	{LineTerminator}		{
												PYPLC.out.println("\t# Begin BLOCK_STATE");
												PYPLC.out.println("\t# current_indent: " + current_indent + "; stack.peek() :" + stack.peek());
												current_indent = 0;
												yybegin(BLOCK);
												return symbol(sym.NEWLINE);
											}

}

<BLOCK> {
	"\t"								{ PYPLC.out.println("\t# Increase indent to: " + " " + ++current_indent); }
	"\f"								{ /* ignore whitespace */}
	.										{
												yypushback(1);
												if (current_indent > stack.peek()) {
													stack.push(current_indent);
													yybegin(YYINITIAL);
													PYPLC.out.println("\t# INDENT");
													PYPLC.out.println("\t# Go to YYINITIAL");
													return symbol(sym.INDENT);
												} else if (current_indent == stack.peek()) {
													PYPLC.out.println("\t# Go to YYINITIAL");
													yybegin(YYINITIAL);
												} else {
													int tmp = stack.pop();
													PYPLC.out.println("\t# DEDENT");
													return symbol(sym.DEDENT);
												}
											}

	{LineTerminator}		{ yypushback(1);
												if (current_indent > stack.peek()) {
													stack.push(current_indent);
													PYPLC.out.println("\t# Push indent and go to YYINITIAL");

													yybegin(YYINITIAL);
													PYPLC.out.println("\t# INDENT");
													PYPLC.out.println("\t# Go to YYINITIAL");
													return symbol(sym.INDENT);
												} else if (current_indent == stack.peek()) {

													PYPLC.out.println("\t# Go to YYINITIAL");

													yybegin(YYINITIAL);
												} else {
													int tmp = stack.pop();
													PYPLC.out.println("\t # DEDENT");
													return symbol(sym.DEDENT);
												}
											}
}

{Comment}							{ /* ignore comments */ }
[^]										{ throw new Error("Illegal character <" + yytext() + ">"); }
