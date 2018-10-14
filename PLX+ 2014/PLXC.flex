// Simple Java interpreter (PL Language)
// @author Alex J. Porras
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
"to"                    { return new Symbol(sym.TO); }
"downto"                { return new Symbol(sym.DOWNTO); }
"step"                  { return new Symbol(sym.STEP); }


// Splitters

"("                     { return new Symbol(sym.LPAREN); }
")"                     { return new Symbol(sym.RPAREN); }
";"                     { return new Symbol(sym.SEMI); }
"{"                     { return new Symbol(sym.LCURLY); }
"}"                     { return new Symbol(sym.RCURLY); }

// 2014
","                     { return new Symbol(sym.COMMA); }

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
"&&"                    { return new Symbol(sym.AND); }
"||"                    { return new Symbol(sym.OR); }

// 2014
"++"                    { return new Symbol(sym.INC); }
"--"                    { return new Symbol(sym.DEC); }
"%"                     { return new Symbol(sym.MOD); }

// Numbers
0|([1-9][0-9]*)         {return new Symbol(sym.INTEGER, yytext()); }

// Identifiers
[_$a-zA-Z][_a-z$A-Z0-9]*  {return new Symbol(sym.IDENT, yytext());}

// Error
[^]                     { }
