/* parser.y */

%token <IDENTIFIER> IDENTIFIER
%token <INTEGER_LITERAL> INTEGER_LITERAL
%token <EOF> EOF
%token CLASS EXTENDS PUBLIC STATIC VOID MAIN STRING BOOLEAN RETURN
%token IF ELSE WHILE SYSTEM_OUT_PRINTLN
%token THIS NEW

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

ExtendsOpt : EXTENDS Identifier
    |
    ;

ListVarDeclaration: ListVarDeclaration VarDeclaration
    |
    ;

ListMethodDeclaration: ListMethodDeclaration MethodDeclaration
    |
    ;

VarDeclaration: Type IDENTIFIER ';'
    ;

MethodDeclaration: PUBLIC Type IDENTIFIER '(' (ParamList)? ')' '{' (VarDeclaration)* (Statement)* RETURN Expression ';' '}'
    ;

ParamList: Type IDENTIFIER (',' Type IDENTIFIER)*
    ;

Type: 'int' '[' ']' | 'boolean' | 'int' | IDENTIFIER
    ;

Statement: '{' (Statement)* '}'
    | 'if' '(' Expression ')' Statement 'else' Statement
    | 'while' '(' Expression ')' Statement
    | 'System.out.println' '(' Expression ')' ';'
    | IDENTIFIER '=' Expression ';'
    | IDENTIFIER '[' Expression ']' '=' Expression ';'
    ;

Expression: Expression '&&' Expression
    | Expression '<' Expression
    | Expression '+' Expression
    | Expression '-' Expression
    | Expression '*' Expression
    | Expression '/' Expression
    | INTEGER_LITERAL
    | 'true'
    | 'false'
    | IDENTIFIER
    | 'this'
    | 'new' 'int' '[' Expression ']'
    | 'new' IDENTIFIER '(' ')'
    | '!' Expression
    | '(' Expression ')'
    | Expression '[' Expression ']'
    | Expression '.' 'length'
    | Expression '.' IDENTIFIER '(' (ArgList)? ')'
    ;

ArgList: Expression (',' Expression)*
    ;

%%

/* You can include your Java code here to handle the parsed grammar. */
