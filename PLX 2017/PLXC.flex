// author: Alex J. Porras
// University of Malaga, Spain
// Language Processors

import java_cup.runtime.Symbol;

%%

%cup

%%

// Reserved words
"if"                    { return new Symbol(sym.IF); }
"else"                  { return new Symbol(sym.ELSE); }
"while"                 { return new Symbol(sym.WHILE, Gen.genTag()); }
"do"                    { return new Symbol(sym.DO, Gen.genTag()); }
"for"                   { return new Symbol(sym.FOR, new DoubleTag()); }
"print"                 { return new Symbol(sym.PRINT); }

// 2014
"int"                   { return new Symbol(sym.INT); }
"switch"								{ return new Symbol(sym.SWITCH); }
"case"									{ return new Symbol(sym.CASE); }
"break"									{ return new Symbol(sym.BREAK); }
"default"								{ return new Symbol(sym.DEFAULT); }


// Splitters

"("                     { return new Symbol(sym.LPAREN); }
")"                     { return new Symbol(sym.RPAREN); }
";"                     { return new Symbol(sym.SEMI); }
"{"                     { return new Symbol(sym.LCURLY); }
"}"                     { return new Symbol(sym.RCURLY); }

// 2017
","                     { return new Symbol(sym.COMMA); }
":"											{ return new Symbol(sym.COLON); }
"&"											{ return new Symbol(sym.REF); }

// Operators

"+"                     { return new Symbol(sym.PLUS); }
"-"                     { return new Symbol(sym.MINUS); }
"*"                     { return new Symbol(sym.TIMES); }
"/"                     { return new Symbol(sym.DIVIDE); }
"="                     { return new Symbol(sym.EQUAL); }
"=="                    { return new Symbol(sym.BEQUAL);}
"!"                     { return new Symbol(sym.NOT); }
">"                     { return new Symbol(sym.MORE); }
"<"                     { return new Symbol(sym.LESS); }
"<="										{ return new Symbol(sym.LEQ); }
">="										{ return new Symbol(sym.MOEQ); }
"!="										{ return new Symbol(sym.NEQ); }
"&&"                    { return new Symbol(sym.AND); }
"||"                    { return new Symbol(sym.OR); }

// 2017
"?"											{ return new Symbol(sym.QUESTION, Gen.genTag());}
"?:"										{ return new Symbol(sym.ELVIS); }

// Numbers
0|([1-9][0-9]*)         {return new Symbol(sym.INTEGER, yytext()); }

// Identifiers
[_$a-zA-Z][_a-z$A-Z0-9]*  {return new Symbol(sym.IDENT, yytext());}

// Error
[^]                     { }
