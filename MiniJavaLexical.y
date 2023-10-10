%{
  import java.io.*;
%}


%byaccj

%token <IDENTIFIER> IDENTIFIER
%token <INTEGER_LITERAL> INT
%token <EOF> EOF
%token CLASS EXTENDS PUBLIC STATIC VOID MAIN STRING BOOLEAN RETURN
%token IF ELSE WHILE SYSTEM_OUT_PRINTLN
%token THIS NEW
%token TRUE FALSE 

%left '+' '-'
%left '*' '/'
%left '&&' '<'

%%

Goal: MainClass ListClassDeclaration
    ;

ListClassDeclaration: ListClassDeclaration ClassDeclaration
    |
    ;

MainClass: CLASS IDENTIFIER '{' PUBLIC STATIC VOID MAIN '(' STRING '[' ']' IDENTIFIER ')' '{' Statement '}' '}'
    ;

ClassDeclaration: CLASS IDENTIFIER ExtendsOpt '{' ListVarDeclaration ListMethodDeclaration '}'
    ;

ExtendsOpt : EXTENDS IDENTIFIER
    |
    ;

ListVarDeclaration: ListVarDeclaration VarDeclaration
    |
    ;

ListMethodDeclaration: ListMethodDeclaration MethodDeclaration
    |
    ;

VarDeclaration: Type IDENTIFIER ';'
    |
    ;

MethodDeclaration: PUBLIC Type IDENTIFIER '(' ParamList ')' '{' VarDeclaration Statement RETURN Expression ';' '}'
    ;

ParamList: Type IDENTIFIER ParamListRecurr
    |
    ;

ParamListRecurr: ',' Type IDENTIFIER ParamListRecurr 
    |
    ;

Type: INT TypeArr
    | BOOLEAN TypeArr
    | IDENTIFIER TypeArr
    ;

TypeArr: '[' ']'
    |
    ;

Statement: '{' Statement '}'
    | IF '(' Expression ')' Statement ELSE Statement
    | WHILE '(' Expression ')' Statement
    | SYSTEM_OUT_PRINTLN '(' Expression ')' ';'
    | IDENTIFIER '=' Expression ';'
    | IDENTIFIER '[' Expression ']' '=' Expression ';'
    |
    ;

Expression: Expression '&&' Expression
    | Expression '<' Expression
    | Expression '+' Expression
    | Expression '-' Expression
    | Expression '*' Expression
    | Expression '/' Expression
    | INT
    | TRUE
    | FALSE
    | IDENTIFIER
    | THIS
    | NEW INT '[' Expression ']'
    | NEW IDENTIFIER '(' ')'
    | '!' Expression
    | '(' Expression ')'
    | Expression '[' Expression ']'
    | Expression '.' 'length'
    | Expression '.' IDENTIFIER '(' ArgList ')'
    ;

ArgList: Expression ArgListRecurr
    |
    ;

ArgListRecurr: ',' Expression ArgListRecurr
    |
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
