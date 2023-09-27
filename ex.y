/* parser.y */

%{
  import java.io.*;
%}
     

%token INT
%token DOUBLE
%token BOOLEAN
%token IDENT
%token NUM
%token IF
%token ELSE
%token WHILE
%token AND
%token '='
%token '+'
%token '*'
%token '/'
%token '>'
%token ';'
%token '{'
%token '}'
%token ','

%%

Prog: Decl ListaFuncoes
    ;

Decl: Tipo LId ';'
    | /* empty */
    ;

Tipo: INT
    | DOUBLE
    | BOOLEAN
    ;

LId: LId ',' IDENT
    | IDENT
    ;

ListaFuncoes: /* TODO: Add production rules for ListaFuncoes here */
    ;

ListaParametros: Tipo IDENT
    | Tipo IDENT ',' ListaParametros
    ;

Bloco: '{' LCmd '}'
    ;

LCmd: Cmd LCmdo
    | /* empty */
    ;

LCmdo: /* TODO: Add production rules for LCmdo here */
    ;

Cmd: Bloco
    | IF '(' E ')' Cmd
    | IF '(' E ')' Cmd ELSE Cmd
    | WHILE '(' E ')' Cmd
    | E ';'
    ;

E: E '=' E
    | E '+' E
    | E '*' E
    | E '/' E
    | E '>' E
    | E AND E
    | NUM
    | IDENT
    | '(' E ')'
    ;

%%


  private Yylex lexer;


  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }


  static boolean interactive;

  public static void main(String args[]) throws IOException {
    System.out.println("BYACC/Java with JFlex Calculator Demo");

    Parser yyparser;
    if ( args.length > 0 ) {
      // parse a file
      yyparser = new Parser(new FileReader(args[0]));
    }
    else {
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("Expression: ");
      interactive = true;
	    yyparser = new Parser(new InputStreamReader(System.in));
    }

    yyparser.yyparse();
    
    if (interactive) {
      System.out.println();
      System.out.println("Have a nice day");
    }
  }
