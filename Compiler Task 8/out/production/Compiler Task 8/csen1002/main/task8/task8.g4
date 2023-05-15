grammar task8;

//Parser Rule start to parse the input and check whether it is accepted or rejected
start: (Q2 | Q3 | Q4) + EOF ;


fragment Qs:ONE* | ZERO ZERO ZERO ZERO|  ONE* ZERO ZERO ZERO ZERO ONE*;
fragment Q1:Qs ZERO|ZERO| ZERO ZERO ZERO ZERO ZERO;
//Lexer Rule Q2 which has the Regular Expression of the accepted state Q2
fragment Q2H:Q1((ONE+ZERO)*ZERO|ZERO);
Q2:Q2H | Q2H ZERO ZERO ZERO ZERO| Q2H ONE+ ZERO ZERO ZERO ZERO |  ZERO ZERO ZERO ONE+ ZERO ZERO ONE+ ZERO ZERO ;
//Lexer Rule Q3 which has the Regular Expression of the accepted state Q3
fragment Q3H:Q2 ZERO|Q2 ONE+ (ZERO ONE)*ZERO|Q2 ONE+ (ZERO ONE+)* ZERO |Q2 ZERO (ONE ZERO)*| Q2 ZERO ZERO ZERO ZERO ZERO;

Q3:Q3H|(Q2 ZERO ZERO)+Q3H | (Q2 ONE* (ZERO ONE)* ZERO ZERO)+Q3H| Q3H ONE+ ZERO| ONE+ ZERO ZERO ZERO ONE ZERO  ONE+ ZERO ZERO ONE ZERO ZERO ZERO |Q2 ZERO (ONE ZERO)+ (ONE+ ZERO)*| Q2 ZERO ONE+ ZERO ZERO ZERO ZERO ONE+ ZERO|Q3H (ONE+ ZERO)*ONE+ ZERO;
//Lexer Rule Q4 which has the Regular Expression of the accepted state Q4
fragment Q4H:Q2 ONE+|Q3 ONE+;
Q4:Q4H ONE* |Q4H(ZERO ONE)*|(Q4H ZERO ZERO)+Q4H|Q4H ONE* (ZERO ONE)* | Q4H ZERO ZERO ONE+ ZERO ZERO ONE+;

//Fragments representing the zeros and ones
fragment ZERO: '0' ;
fragment ONE: '1' ;