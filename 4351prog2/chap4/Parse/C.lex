package Parse;
import ErrorMsg.ErrorMsg;

%% 

%implements Lexer
%function nextToken
%type java_cup.runtime.Symbol
%char

%{
private void newline() {
  errorMsg.newline(yychar);
}

private void err(int pos, String s) {
  errorMsg.error(pos,s);
}

private void err(String s) {
  err(yychar,s);
}

private java_cup.runtime.Symbol tok(int kind) {
    return tok(kind, null);
}

private java_cup.runtime.Symbol tok(int kind, Object value) {
    return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(), value);
}

private ErrorMsg errorMsg;

Yylex(java.io.InputStream s, ErrorMsg e) {
  this(s);
  errorMsg=e;
}
String s;
%}

%eofval{
	{
	 return tok(sym.EOF, null);
        }
%eofval}       

%state STRING
%state COMMENT

%%
<YYINITIAL>" "	{}
<YYINITIAL,COMMENT>\n	{newline();}
<YYINITIAL> switch {return tok(sym.SWITCH);}
<YYINITIAL> int {return tok(sym.INT);}
<YYINITIAL> auto {return tok(sym.AUTO);}
<YYINITIAL> struct {return tok(sym.STRUCT);}
<YYINITIAL> if {return tok(sym.IF);}
<YYINITIAL> while {return tok(sym.WHILE);}
<YYINITIAL> break {return tok(sym.BREAK);}
<YYINITIAL> case      { return tok(sym.CASE); }
<YYINITIAL> var {return tok(sym.VAR);}
<YYINITIAL> enum      { return tok(sym.ENUM); }
<YYINITIAL> register  { return tok(sym.REGISTER); }
<YYINITIAL> typedef   { return tok(sym.TYPEDEF); }
<YYINITIAL> char      { return tok(sym.CHAR); }
<YYINITIAL> extern    { return tok(sym.EXTERN); }
<YYINITIAL> return    { return tok(sym.RETURN); }
<YYINITIAL> union     { return tok(sym.UNION); }
<YYINITIAL> const     { return tok(sym.CONST); }
<YYINITIAL> float     { return tok(sym.FLOAT); }
<YYINITIAL> short     { return tok(sym.SHORT); }
<YYINITIAL> unsigned  { return tok(sym.UNSIGNED); }
<YYINITIAL> continue  { return tok(sym.CONTINUE); }
<YYINITIAL> signed    { return tok(sym.SIGNED); }
<YYINITIAL> void      { return tok(sym.VOID); }
<YYINITIAL> default   { return tok(sym.DEFAULT); }
<YYINITIAL> goto      { return tok(sym.GOTO); }
<YYINITIAL> sizeof    { return tok(sym.SIZEOF); }
<YYINITIAL> volatile  { return tok(sym.VOLATILE); }
<YYINITIAL> static    { return tok(sym.STATIC); }
<YYINITIAL> \\b {return tok(sym.BACKSPACE);}
<YYINITIAL> \\n {return tok(sym.BACKSPACE);}
<YYINITIAL> else {return tok(sym.ELSE);}
<YYINITIAL> do {return tok(sym.DO);}
<YYINITIAL> for {return tok(sym.FOR);}
<YYINITIAL>[A-Za-z_][A-Za-z0-9_]*	{return tok(sym.ID,yytext());}
<YYINITIAL>[0-9]*	{return tok(sym.INT_LITERAL,yytext());}


<YYINITIAL> \" {yybegin(STRING); s = ""; }
<YYINITIAL> \' {yybegin(STRING); s = ""; }
<STRING> \" {yybegin(YYINITIAL); return tok(sym.STRING,s); }
<STRING> \' {yybegin(YYINITIAL); return tok(sym.STRING,s); }
<STRING> \\n { s += '\n'; newline(); }
<STRING> \u0007 { s += '\u0007'; }
<STRING> \\b { s += '\b'; }
<STRING> \\f { s += '\f'; }
<STRING> \\r { s += '\r'; }
<STRING> \\t { s += '\t'; }
<STRING> \u000B { s += '\u000B'; }
<STRING> \\\" { s += '\"'; }
<STRING> \\' { s += '\''; }
<STRING> \\? { s += '?'; }
<STRING> \\\\ { s += '\\'; }
<STRING> \. { err("Illegal character: " + yytext()); }
<STRING> \\\n {newline(); err("Illegal character: " + yytext()); }
<STRING> . { s += yytext(); }


<YYINITIAL> "//".*\n {return tok(sym.COMMENT);}
<YYINITIAL> "/*" {yybegin(COMMENT); }
<COMMENT> "*/" {yybegin(YYINITIAL); return tok(sym.COMMENT); }
<COMMENT> . {}

<YYINITIAL>"}"	{return tok(sym.RBRACE);}
<YYINITIAL>"{"	{return tok(sym.LBRACE);}
<YYINITIAL>"]"	{return tok(sym.RBRACKET);}
<YYINITIAL>"["	{return tok(sym.LBRACKET);}
<YYINITIAL>")"	{return tok(sym.RPAREN);}
<YYINITIAL>"("	{return tok(sym.LPAREN);}
<YYINITIAL>"\\0"	{return tok(sym.EOF);}
<YYINITIAL>">>="	{return tok(sym.DOUBLE_RIGHT_ARROW_EQUAL);}
<YYINITIAL>"<<="	{return tok(sym.DOUBLE_LEFT_ARROW_EQUAL);}
<YYINITIAL>"|="	{return tok(sym.OR_EQUAL);}
<YYINITIAL>"^="	{return tok(sym.CARROT_EQUAL);}
<YYINITIAL>"&="	{return tok(sym.AND_EQUAL);}
<YYINITIAL>"-="	{return tok(sym.MINUS_EQUAL);}
<YYINITIAL>"+="	{return tok(sym.PLUS_EQUAL);}
<YYINITIAL>"%="	{return tok(sym.PERCENT_EQUAL);}
<YYINITIAL>"/="	{return tok(sym.SLASH_EQUAL);}
<YYINITIAL>"*="	{return tok(sym.STAR_EQUAL);}
<YYINITIAL>"||"	{return tok(sym.OR);}
<YYINITIAL>"&&"	{return tok(sym.AND_AND);}
<YYINITIAL>"!="	{return tok(sym.NOT_EQUAL);}
<YYINITIAL>">>"	{return tok(sym.DOUBLE_RIGHT_ARROW);}
<YYINITIAL>"<<"	{return tok(sym.DOUBLE_LEFT_ARROW);}
<YYINITIAL>"++"	{return tok(sym.PLUS_PLUS);}
<YYINITIAL>"--"	{return tok(sym.MINUS_MINUS);}
<YYINITIAL>"##"	{return tok(sym.POUND_POUND);}
<YYINITIAL>"==" {return tok(sym.EQUAL_EQUAL);}
<YYINITIAL>"+"	{return tok(sym.PLUS);}
<YYINITIAL>"-"	{return tok(sym.MINUS);}
<YYINITIAL>"/"	{return tok(sym.DIVIDE);}
<YYINITIAL>"|"	{return tok(sym.B_OR);}
<YYINITIAL>">="	{return tok(sym.GE);}
<YYINITIAL>"<="	{return tok(sym.LE);}
<YYINITIAL>">"	{return tok(sym.GT);}
<YYINITIAL>"<"	{return tok(sym.LT);}
<YYINITIAL>","	{return tok(sym.COMMA);}
<YYINITIAL>"."	{return tok(sym.DOT);}
<YYINITIAL>"*"	{return tok(sym.STAR);}
<YYINITIAL>"="	{return tok(sym.ASSIGN);}
<YYINITIAL>"#"	{return tok(sym.POUND);}
<YYINITIAL>"~"	{return tok(sym.TILDA);}
<YYINITIAL>"&"  {return tok(sym.AND);}
<YYINITIAL>"%"	{return tok(sym.PERCENT);}
<YYINITIAL>":"	{return tok(sym.COLON);}
<YYINITIAL>";"	{return tok(sym.SEMICOLON);}
<YYINITIAL>"^"	{return tok(sym.CARROT);}
<YYINITIAL>"?"	{return tok(sym.QUESTION_MARK);}
<YYINITIAL>. { err("Illegal character: " + yytext()); }


