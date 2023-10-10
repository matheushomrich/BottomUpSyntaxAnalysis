//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "minijava.y"
  import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IDENTIFIER=257;
public final static short INT=258;
public final static short EOF=259;
public final static short CLASS=260;
public final static short EXTENDS=261;
public final static short PUBLIC=262;
public final static short STATIC=263;
public final static short VOID=264;
public final static short MAIN=265;
public final static short STRING=266;
public final static short BOOLEAN=267;
public final static short RETURN=268;
public final static short IF=269;
public final static short ELSE=270;
public final static short WHILE=271;
public final static short SYSTEM_OUT_PRINTLN=272;
public final static short THIS=273;
public final static short NEW=274;
public final static short TRUE=275;
public final static short FALSE=276;
public final static short length=278;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    2,    1,    3,    5,    5,    6,    6,    7,
    7,    8,    8,    9,   11,   11,   13,   13,   10,   10,
   10,   14,   14,    4,    4,    4,    4,    4,    4,    4,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   15,   15,
   16,   16,
};
final static short yylen[] = {                            2,
    2,    2,    0,   17,    7,    2,    0,    2,    0,    2,
    0,    3,    0,   13,    3,    0,    4,    0,    2,    2,
    2,    2,    0,    3,    7,    5,    5,    4,    7,    0,
    3,    3,    3,    3,    3,    3,    1,    1,    1,    1,
    1,    5,    4,    2,    3,    4,    3,    6,    2,    0,
    3,    0,
};
final static short yydefred[] = {                         0,
    0,    0,    3,    0,    0,    0,    0,    2,    0,    0,
    0,    0,    0,    0,    6,    9,    0,    0,    0,    0,
    0,    0,    0,    8,    0,    0,    0,   21,   19,   20,
    0,    5,   10,    0,    0,   22,    0,   12,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   15,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   40,   37,   41,    0,
   38,   39,    0,    0,    0,    0,    0,    0,    0,   24,
    4,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   28,    0,    0,    0,
   17,    0,    0,    0,   45,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   47,    0,   26,   27,    0,   43,
    0,   46,    0,    0,    0,    0,   42,   29,    0,    0,
   25,   14,    0,   49,   48,    0,   51,
};
final static short yydgoto[] = {                          2,
    3,    5,    8,   54,   13,   18,   23,   24,   33,   25,
   45,   75,   56,   28,  130,  134,
};
final static short yysindex[] = {                      -244,
 -231,    0,    0,  -67, -200, -184, -195,    0, -182, -176,
 -170, -154,  -19, -156,    0,    0,   76, -124, -148,   31,
   31,   31, -108,    0, -115,   57,   56,    0,    0,    0,
 -124,    0,    0,   91,   58,    0, -102,    0, -100,  126,
  129, -124,   48,  -85,  133,  -84,  134,   60,  -38,  139,
  141,  146,  -84,   66, -124,    0, -124,  -33,  -33,  -33,
  -33,  -33,   69,   71,  -59,  -84,    0,    0,    0, -233,
    0,    0,  -33,  -33,   30,   46,  -12,   -5,    4,    0,
    0,  134,  -69,  160,  114,   24,  122,  -33,  -33,  -33,
  -33,  -33,  -33,  -33,  145, -256,    0,  -84,  -84,  158,
    0,  -33,  177,  -33,    0,  -28,  -28,   22,   22,   11,
   11,   54,  -33,  180,    0,  -43,    0,    0,   65,    0,
   84,    0,   93,  -33,  -84,  103,    0,    0,  116,  188,
    0,    0,  -33,    0,    0,  116,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  239,    0,    0,    0,    0,  124,
    0,    0,    0,    0,    0,    0,    0,  -82,    0,   -9,
   -9,   -9,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  216,    0,    0,    0,  144,  217,    0,    0,    0,
    0,    0,  144,    0,    0,    0,  -79,    0,    0,    0,
    0,    0,    0,    0,    0,   -4,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  217,    0,    0,    0,    0,   39,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -3,  -73,    0,
    0,    0,    0,    0,    0,  178,  218,  167,  209,  -39,
  -32,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  229,  -73,    0,    0,    0,  232,    0,
    0,    0,    0,    0,    0,  232,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   21,    0,    0,    0,  219,    0,   86,
    0,  142,  192,   37,    0,  143,
};
final static int YYTABLESIZE=399;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         74,
  114,   31,   31,   31,   31,   31,   73,   31,   32,   32,
   32,   32,   32,   90,   32,    1,   32,   96,   91,   31,
   31,  115,   59,   84,   85,    4,   32,   32,   98,   90,
   88,   93,   89,   96,   91,   99,   90,   88,   53,   89,
   96,   91,   11,   13,  100,   90,   88,   93,   89,   96,
   91,   30,   58,   31,   93,    6,   96,   29,   30,    7,
   32,   10,   94,   93,  105,   90,   88,   96,   89,   96,
   91,   90,   88,   63,   89,   96,   91,    9,   94,   44,
   11,   93,   44,   93,   12,   94,   83,   90,   88,   93,
   89,   96,   91,   14,   94,   90,   88,   44,   89,   96,
   91,   94,   15,   16,   97,   93,   90,   88,   17,   89,
   96,   91,   94,   93,   94,   19,   37,   26,  116,  117,
   94,   27,   95,  126,   93,   90,   88,   44,   89,   96,
   91,   44,   20,   21,   90,   88,   94,   89,   96,   91,
   65,   34,   22,   93,   94,  131,  122,   35,   36,   38,
   39,  128,   93,   31,   40,   94,   41,   90,   88,  133,
   89,   96,   91,   90,   88,   42,   89,   96,   91,   43,
   46,   47,   49,   48,   94,   93,  127,   55,   60,   11,
   61,   93,   57,   94,   50,   62,   51,   52,   13,   13,
   64,   13,   13,   80,   30,   81,   30,   82,  102,  103,
   76,   77,   78,   79,  104,  113,   94,   35,   35,   35,
   35,   35,   94,   35,   86,   87,  118,  120,   33,  124,
   33,   33,   33,   67,   68,   35,  125,  132,  135,  106,
  107,  108,  109,  110,  111,  112,   33,   31,    1,   69,
   70,   71,   72,  119,   32,  121,    7,   23,   92,   36,
   36,   36,   36,   36,  123,   36,   16,   18,   34,   35,
   34,   34,   34,   30,   92,  129,   30,   36,   30,   50,
   33,   92,   52,  101,  136,   66,   34,    0,  137,    0,
   92,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   92,    0,
   92,   36,    0,    0,    0,    0,   92,    0,    0,    0,
   34,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   92,    0,    0,    0,    0,    0,    0,    0,
   92,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   92,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   92,    0,    0,    0,    0,    0,    0,    0,    0,   92,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   92,    0,    0,    0,    0,    0,   92,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
  257,   41,   42,   43,   44,   45,   40,   47,   41,   42,
   43,   44,   45,   42,   47,  260,  125,   46,   47,   59,
   60,  278,   61,  257,  258,  257,   59,   60,   41,   42,
   43,   60,   45,   46,   47,   41,   42,   43,  123,   45,
   46,   47,  125,  123,   41,   42,   43,   60,   45,   46,
   47,  125,   91,   93,   60,  123,   46,   21,   22,  260,
   93,  257,   91,   60,   41,   42,   43,   46,   45,   46,
   47,   42,   43,   53,   45,   46,   47,  262,   91,   41,
  263,   60,   44,   60,  261,   91,   66,   42,   43,   60,
   45,   46,   47,  264,   91,   42,   43,   59,   45,   46,
   47,   91,  257,  123,   59,   60,   42,   43,  265,   45,
   46,   47,   91,   60,   91,   40,   31,  266,   98,   99,
   91,   91,   93,   59,   60,   42,   43,   42,   45,   46,
   47,   93,  257,  258,   42,   43,   91,   45,   46,   47,
   55,  257,  267,   60,   91,  125,   93,   91,   93,   59,
   93,   59,   60,  262,  257,   91,  257,   42,   43,   44,
   45,   46,   47,   42,   43,   40,   45,   46,   47,   41,
  123,  257,  257,   41,   91,   60,   93,   44,   40,  262,
   40,   60,  123,   91,  269,   40,  271,  272,  268,  269,
  125,  271,  272,  125,  268,  125,  270,  257,  268,   40,
   59,   60,   61,   62,   91,   61,   91,   41,   42,   43,
   44,   45,   91,   47,   73,   74,   59,   41,   41,   40,
   43,   44,   45,  257,  258,   59,  270,  125,   41,   88,
   89,   90,   91,   92,   93,   94,   59,  277,    0,  273,
  274,  275,  276,  102,  277,  104,  123,  257,  277,   41,
   42,   43,   44,   45,  113,   47,   41,   41,   41,   93,
   43,   44,   45,  268,  277,  124,  270,   59,  125,   41,
   93,  277,   41,   82,  133,   57,   59,   -1,  136,   -1,
  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,   -1,
  277,   93,   -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,
   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,   -1,   -1,   -1,   -1,   -1,  277,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=278;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"IDENTIFIER","INT","EOF","CLASS",
"EXTENDS","PUBLIC","STATIC","VOID","MAIN","STRING","BOOLEAN","RETURN","IF",
"ELSE","WHILE","SYSTEM_OUT_PRINTLN","THIS","NEW","TRUE","FALSE","\"&&\"",
"\"length\"",
};
final static String yyrule[] = {
"$accept : Goal",
"Goal : MainClass ListClassDeclaration",
"ListClassDeclaration : ListClassDeclaration ClassDeclaration",
"ListClassDeclaration :",
"MainClass : CLASS IDENTIFIER '{' PUBLIC STATIC VOID MAIN '(' STRING '[' ']' IDENTIFIER ')' '{' Statement '}' '}'",
"ClassDeclaration : CLASS IDENTIFIER ExtendsOpt '{' ListVarDeclaration ListMethodDeclaration '}'",
"ExtendsOpt : EXTENDS IDENTIFIER",
"ExtendsOpt :",
"ListVarDeclaration : ListVarDeclaration VarDeclaration",
"ListVarDeclaration :",
"ListMethodDeclaration : ListMethodDeclaration MethodDeclaration",
"ListMethodDeclaration :",
"VarDeclaration : Type IDENTIFIER ';'",
"VarDeclaration :",
"MethodDeclaration : PUBLIC Type IDENTIFIER '(' ParamList ')' '{' VarDeclaration Statement RETURN Expression ';' '}'",
"ParamList : Type IDENTIFIER ParamListRecurr",
"ParamList :",
"ParamListRecurr : ',' Type IDENTIFIER ParamListRecurr",
"ParamListRecurr :",
"Type : INT TypeArr",
"Type : BOOLEAN TypeArr",
"Type : IDENTIFIER TypeArr",
"TypeArr : '[' ']'",
"TypeArr :",
"Statement : '{' Statement '}'",
"Statement : IF '(' Expression ')' Statement ELSE Statement",
"Statement : WHILE '(' Expression ')' Statement",
"Statement : SYSTEM_OUT_PRINTLN '(' Expression ')' ';'",
"Statement : IDENTIFIER '=' Expression ';'",
"Statement : IDENTIFIER '[' Expression ']' '=' Expression ';'",
"Statement :",
"Expression : Expression \"&&\" Expression",
"Expression : Expression '<' Expression",
"Expression : Expression '+' Expression",
"Expression : Expression '-' Expression",
"Expression : Expression '*' Expression",
"Expression : Expression '/' Expression",
"Expression : INT",
"Expression : TRUE",
"Expression : FALSE",
"Expression : IDENTIFIER",
"Expression : THIS",
"Expression : NEW INT '[' Expression ']'",
"Expression : NEW IDENTIFIER '(' ')'",
"Expression : '!' Expression",
"Expression : '(' Expression ')'",
"Expression : Expression '[' Expression ']'",
"Expression : Expression '.' \"length\"",
"Expression : Expression '.' IDENTIFIER '(' ArgList ')'",
"ArgList : Expression ArgListRecurr",
"ArgList :",
"ArgListRecurr : ',' Expression ArgListRecurr",
"ArgListRecurr :",
};

//#line 106 "minijava.y"


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
//#line 409 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
