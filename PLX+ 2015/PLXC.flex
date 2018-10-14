import java_cup.runtime.*;

%%
%unicode
%cup
%line
%column

%{
	private Symbol symbol(int type) {
		return new Symbol(type, yyline, yycolumn);
	}

	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline, yycolumn, value);
	}

%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace		 = {LineTerminator} | [ \t\f]

// Comments
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment   = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"

// Real Numbers
DoubleLiteral =	{float} {Exponent}?

float    = ([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)
Exponent = [eE] [+-]? [0-9]+

// Integers
Natural  = 0|([1-9][0-9]*)

// Identifier
Identifier = [_a-z$A-Z][_a-z$A-Z0-9]*

%%

// Expressions and rules

// Reserved words

"print"	{ return symbol(sym.PRINT); }
"if"		{ return symbol(sym.IF); }
"else"	{ return symbol(sym.ELSE); }
"do"		{ return symbol(sym.DO, Gen.genTag()); }
"while" { return symbol(sym.WHILE, Gen.genTag()); }
"for"		{ return symbol(sym.FOR, new DoubleTag()); }
"in"		{ return symbol(sym.IN); }

"true"	{ return symbol(sym.TRUE); }
"false"	{ return symbol(sym.FALSE); }

"int"		{ return symbol(sym.INT); }
"float"	{ return symbol(sym.FLOAT); }
"(int)"	{ return symbol(sym.CASTINT); }
"(float)"	{ return symbol(sym.CASTFLOAT); }

// Separators

"("	{ return symbol(sym.LPAREN); }
")"	{ return symbol(sym.RPAREN); }
"{"	{ return symbol(sym.LCURLY); }
"}"	{ return symbol(sym.RCURLY); }
"["	{ return symbol(sym.LSQU); }
"]"	{ return symbol(sym.RSQU); }
";"	{ return symbol(sym.SEMI); }
","	{ return symbol(sym.COMMA); }

// Arithmetic operators

"="	{ return symbol(sym.EQUAL); }
"+"	{ return symbol(sym.PLUS); }
"-"	{ return symbol(sym.MINUS); }
"*"	{ return symbol(sym.TIMES); }
"/"	{ return symbol(sym.DIVIDE); }
"%"	{ return symbol(sym.MOD); }

// Boolean operators

"!"	{ return symbol(sym.NOT); }
">"	{ return symbol(sym.MORE); }
"<"	{ return symbol(sym.LESS); }
">="	{ return symbol(sym.MOEQ); }
"<="	{ return symbol(sym.LEQ); }
"=="	{ return symbol(sym.BEQUAL); }
"!="	{ return symbol(sym.NEQ); }
"&&"	{ return symbol(sym.AND); }
"||"	{ return symbol(sym.OR); }

// Numbers
{Natural}	{ return symbol(sym.INTEGER, yytext()); }

{DoubleLiteral}	{ return symbol(sym.REAL, yytext()); }

// Comments
{Comment}	{ /* Ignore comments*/ }

{WhiteSpace} {/* Ignore whitespaces */}

{Identifier} { return symbol(sym.IDENT, yytext()); }

// Error
[^]	{ throw new Error("Illegal character <" + yytext() + ">"); }
