/* The following code was generated by JFlex 1.3.5 on 6/16/03 12:36 PM */

/* PropertiesLexer.java is a generated file.  You probably want to
 * edit PropertiesLexer.lex to make changes.  Use JFlex to generate it.
 * To generate PropertiesLexer.java
 * Install <a href="http://jflex.de/">JFlex</a> v1.3.2 or later.
 * Once JFlex is in your classpath run<br>
 * <code>java --skel properties.jflex.skel JFlex.Main PropertiesLexer.lex</code><br>
 * You will then have a file called PropertiesLexer.java
 */

/*
 * Goes with UberProperties
 * Copyright (C) 2002 Stephen Ostermiller 
 * http://ostermiller.org/contact.pl?regarding=Java+Utilities
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * See COPYING.TXT for details.
 */

package com.Ostermiller.util;

import java.io.*;

/** 
 * PropertiesLexer is a Java Properties file lexer.  Created with JFlex.  An example of how it is used:
 *  <CODE>
 *  <PRE>
 *  PropertiesLexer shredder = new PropertiesLexer(System.in);
 *  PropertiesToken t;
 *  while ((t = shredder.getNextToken()) != null){
 *      System.out.println(t);
 *  }
 *  </PRE>
 *  </CODE>
 * @see PropertiesToken
 */ 


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.3.5
 * on 6/16/03 12:36 PM from the specification file
 * <tt>file:/home/steveo/personal/projects/java/com/Ostermiller/util/PropertiesLexer.lex</tt>
 */
class PropertiesLexer {

  /** This character denotes the end of file */
  private static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  final private static int YY_BUFFERSIZE = 16384;

  /** lexical states */
  final public static int SEPARATOR = 6;
  final public static int DONE = 8;
  final public static int MID_NAME = 4;
  final public static int MID_NAME_NEW_LINE = 5;
  final public static int VALUE = 7;
  final public static int MID_VALUE = 6;
  final public static int YYINITIAL = 0;
  final public static int WHITE_SPACE = 2;
  final public static int LINE_END = 1;
  final public static int NAME = 3;

  /** 
   * Translates characters to character classes
   */
  final private static char [] yycmap = {
     0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  4,  0,  1,  3,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     1,  6,  0,  6,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  5,  0,  0,  5,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  2,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
  };

  /** 
   * Translates a state to a row index in the transition table
   */
  final private static int yy_rowMap [] = { 
        0,     7,    14,    21,    28,    35,    42,    49,    56,    63, 
       70,    77,    84,    91,    91,    98,    91,   105,   112,   119, 
      126,   133,   140,   147,   154,    77,   105,   112,   161,   168, 
       91,   175,    91
  };

  /** 
   * The packed transition table of the DFA (part 0)
   */
  final private static String yy_packed0 = 
    "\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21"+
    "\1\22\1\21\1\15\1\16\2\21\1\12\1\21\1\14"+
    "\2\21\1\17\1\12\1\23\1\24\1\25\1\15\1\16"+
    "\1\17\1\23\1\12\1\22\1\26\1\15\1\16\1\17"+
    "\2\12\1\27\1\26\1\15\1\16\1\17\1\12\1\23"+
    "\1\24\1\30\1\15\1\16\2\23\1\21\1\24\1\31"+
    "\1\15\1\16\2\21\7\11\1\12\1\0\1\32\3\0"+
    "\1\12\1\0\1\13\1\0\1\15\1\16\2\0\3\12"+
    "\2\0\2\12\4\0\1\16\11\0\3\20\2\0\2\20"+
    "\1\0\1\33\1\0\1\15\1\16\2\0\1\23\1\34"+
    "\1\35\2\0\2\23\1\0\1\24\1\0\1\15\1\16"+
    "\2\0\3\23\1\36\1\37\2\23\3\12\1\36\1\37"+
    "\2\12\1\0\1\27\1\0\1\15\1\16\2\0\3\23"+
    "\1\40\1\41\2\23\3\0\1\40\1\41\2\0\3\23"+
    "\2\0\2\23\4\0\1\37\6\0\1\41\2\0";

  /** 
   * The transition table of the DFA
   */
  final private static int yytrans [] = yy_unpack();

  /** 
   * Per instance reference to the character map.
   * This can be cloned and modified per instance if needed.
   * It is initally set to the static value.
   */
  private char [] yycmap_instance = yycmap;

  /* error codes */
  private static final int YY_UNKNOWN_ERROR = 0;
  private static final int YY_ILLEGAL_STATE = 1;
  private static final int YY_NO_MATCH = 2;
  private static final int YY_PUSHBACK_2BIG = 3;

  /* error messages for the codes above */
  private static final String YY_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Internal error: unknown state",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * YY_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private final static byte YY_ATTRIBUTE[] = {
     1,  0,  1,  1,  1,  1,  1,  0,  1,  1,  1,  1,  1,  9,  9,  1, 
     9,  1,  1,  1,  1,  1,  1,  1,  1,  0,  0,  0,  0,  1,  9,  1,  9
  };

  /** the input device */
  private java.io.Reader yy_reader;

  /** the current state of the DFA */
  private int yy_state;

  /** the current lexical state */
  private int yy_lexical_state = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char yy_buffer[] = new char[YY_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int yy_markedPos;

  /** the textposition at the last state to be included in yytext */
  private int yy_pushbackPos;

  /** the current text position in the buffer */
  private int yy_currentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int yy_startRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int yy_endRead;

  /** yy_atEOF == true <=> the scanner is at the EOF */
  private boolean yy_atEOF;

  /* user code: */
    private int lastToken;
    private int nextState=YYINITIAL;
        
    /**
     * Prints out tokens from a file or System.in.
     * If no arguments are given, System.in will be used for input.
     * If more arguments are given, the first argument will be used as
     * the name of the file to use as input
     *
     * @param args program arguments, of which the first is a filename
     */
    public static void main(String[] args) {
        InputStream in;
        try {
            if (args.length > 0){
                File f = new File(args[0]);
                if (f.exists()){
                    if (f.canRead()){
                        in = new FileInputStream(f);
                    } else {
                        throw new IOException("Could not open " + args[0]);
                    }
                } else {
                    throw new IOException("Could not find " + args[0]);
                }
            } else {
                in = System.in;
            }
            PropertiesLexer shredder = new PropertiesLexer(in);
            PropertiesToken t;
            while ((t = shredder.getNextToken()) != null) {
                if (t.getID() != PropertiesToken.WHITE_SPACE){
                    System.out.println(t);
                }
            }
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }  


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  PropertiesLexer(java.io.Reader in) {
    this.yy_reader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  PropertiesLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the split, compressed DFA transition table.
   *
   * @return the unpacked transition table
   */
  private static int [] yy_unpack() {
    int [] trans = new int[182];
    int offset = 0;
    offset = yy_unpack(yy_packed0, offset, trans);
    return trans;
  }

  /** 
   * Unpacks the compressed DFA transition table.
   *
   * @param packed   the packed transition table
   * @return         the index of the last entry
   */
  private static int yy_unpack(String packed, int offset, int [] trans) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do trans[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   IOException  if any I/O-Error occurs
   */
  private boolean yy_refill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (yy_startRead > 0) {
      System.arraycopy(yy_buffer, yy_startRead, 
                       yy_buffer, 0, 
                       yy_endRead-yy_startRead);

      /* translate stored positions */
      yy_endRead-= yy_startRead;
      yy_currentPos-= yy_startRead;
      yy_markedPos-= yy_startRead;
      yy_pushbackPos-= yy_startRead;
      yy_startRead = 0;
    }

    /* is the buffer big enough? */
    if (yy_currentPos >= yy_buffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[yy_currentPos*2];
      System.arraycopy(yy_buffer, 0, newBuffer, 0, yy_buffer.length);
      yy_buffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = yy_reader.read(yy_buffer, yy_endRead, 
                                            yy_buffer.length-yy_endRead);

    if (numRead < 0) {
      return true;
    }
    else {
      yy_endRead+= numRead;  
      return false;
    }
  }
  
  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  private final void yybegin(int newState) {
    yy_lexical_state = newState;
  }

  /**
   * Returns the text matched by the current regular expression.
   */
  private final String yytext() {
    return new String( yy_buffer, yy_startRead, yy_markedPos-yy_startRead );
  }

  /**
   * Returns the length of the matched text region.
   */
  private final int yylength() {
    return yy_markedPos-yy_startRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void yy_ScanError(int errorCode) {
    String message;
    try {
      message = YY_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = YY_ERROR_MSG[YY_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  private void yypushback(int number)  {
    if ( number > yylength() )
      yy_ScanError(YY_PUSHBACK_2BIG);

    yy_markedPos -= number;
  }

  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   IOException  if any I/O-Error occurs
   */
  public PropertiesToken getNextToken() throws java.io.IOException {
    int yy_input;
    int yy_action;

    // cached fields:
    int yy_currentPos_l;
    int yy_startRead_l;
    int yy_markedPos_l;
    int yy_endRead_l = yy_endRead;
    char [] yy_buffer_l = yy_buffer;
    char [] yycmap_l = yycmap_instance;

    int [] yytrans_l = yytrans;
    int [] yy_rowMap_l = yy_rowMap;
    byte [] yy_attr_l = YY_ATTRIBUTE;

    while (true) {
      yy_markedPos_l = yy_markedPos;

      yy_action = -1;

      yy_startRead_l = yy_currentPos_l = yy_currentPos = 
                       yy_startRead = yy_markedPos_l;
  
      yy_state = yy_lexical_state;


      yy_forAction: {
        while (true) {
    
          if (yy_currentPos_l < yy_endRead_l)
            yy_input = yy_buffer_l[yy_currentPos_l++];
          else if (yy_atEOF) {
            yy_input = YYEOF;
            break yy_forAction;
          }
          else {
            // store back cached positions
            yy_currentPos  = yy_currentPos_l;
            yy_markedPos   = yy_markedPos_l;
            boolean eof = yy_refill();
            // get translated positions and possibly new buffer
            yy_currentPos_l  = yy_currentPos;
            yy_markedPos_l   = yy_markedPos;
            yy_buffer_l      = yy_buffer;
            yy_endRead_l     = yy_endRead;
            if (eof) {
              yy_input = YYEOF;
              break yy_forAction;
            }
            else {
              yy_input = yy_buffer_l[yy_currentPos_l++];
            }
          }
          int yy_next = yytrans_l[ yy_rowMap_l[yy_state] + yycmap_l[yy_input] ];
          if (yy_next == -1) break yy_forAction;
          yy_state = yy_next;

          int yy_attributes = yy_attr_l[yy_state];
          if ( (yy_attributes & 1) == 1 ) {
            yy_action = yy_state; 
            yy_markedPos_l = yy_currentPos_l; 
            if ( (yy_attributes & 8) == 8 ) break yy_forAction;
          }

        }
      }

      // store back cached position
      yy_markedPos = yy_markedPos_l;

      switch (yy_action) {

        case 31: 
        case 32: 
          { 
    nextState = MID_VALUE;
    lastToken = PropertiesToken.CONTINUE_LINE;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 34: break;
        case 22: 
          { 
    nextState = MID_NAME;
    lastToken = PropertiesToken.WHITE_SPACE;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 35: break;
        case 19: 
          { 
    lastToken = PropertiesToken.WHITE_SPACE;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    return (t);
 }
        case 36: break;
        case 11: 
        case 16: 
        case 17: 
        case 20: 
        case 21: 
        case 23: 
        case 24: 
          { 
    System.err.println("Unmatched input.");
    String state = "";    
	String text = yytext();
    switch (nextState){
        case YYINITIAL: state = "YYINITIAL"; break;
        case LINE_END: state = "LINE_END"; break;
        case WHITE_SPACE: state = "WHITE_SPACE"; break;
        case NAME: state = "NAME"; break;
        case SEPARATOR: state = "SEPARATOR"; break;
        case VALUE: state = "VALUE"; break;
        case MID_NAME: state = "MID_NAME"; break;
    }
    System.err.println("State: " + state);
    System.err.println("Text: " + text);
	yy_ScanError(YY_NO_MATCH);
 }
        case 37: break;
        case 14: 
          { 
    nextState = SEPARATOR;
    lastToken = PropertiesToken.SEPARATOR;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 38: break;
        case 15: 
          {  
    nextState = LINE_END;
    lastToken = PropertiesToken.COMMENT;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 39: break;
        case 29: 
        case 30: 
          { 
    nextState = MID_NAME_NEW_LINE;
    lastToken = PropertiesToken.CONTINUE_LINE;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 40: break;
        case 12: 
        case 13: 
          { 
    nextState = YYINITIAL;
    lastToken = PropertiesToken.END_LINE_WHITE_SPACE;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 41: break;
        case 3: 
        case 18: 
          { 
    nextState = VALUE;
    lastToken = PropertiesToken.VALUE;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 42: break;
        case 6: 
          { 
    nextState = VALUE;
    lastToken = PropertiesToken.VALUE;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 43: break;
        case 0: 
        case 2: 
        case 4: 
        case 5: 
        case 9: 
          { 
    nextState = NAME;
    lastToken = PropertiesToken.NAME;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 44: break;
        case 10: 
          { 
    nextState = WHITE_SPACE;    
    lastToken = PropertiesToken.WHITE_SPACE;
    String text = yytext();
    PropertiesToken t = new PropertiesToken(lastToken,text);
    yybegin(nextState);
    return (t);
 }
        case 45: break;
        case 8: 
          { 
	return null;
 }
        case 46: break;
        default: 
          if (yy_input == YYEOF && yy_startRead == yy_currentPos) {
            yy_atEOF = true;
            switch (yy_lexical_state) {
            case SEPARATOR:
              { 
    nextState = DONE;
    lastToken = PropertiesToken.END_LINE_WHITE_SPACE;
    PropertiesToken t = new PropertiesToken(lastToken,"");
    yybegin(nextState);
    return (t);
 }
            case 34: break;
            case MID_NAME:
              { 
    nextState = DONE;
    lastToken = PropertiesToken.END_LINE_WHITE_SPACE;
    PropertiesToken t = new PropertiesToken(lastToken,"");
    yybegin(nextState);
    return (t);
 }
            case 35: break;
            case MID_NAME_NEW_LINE:
              { 
    nextState = DONE;
    lastToken = PropertiesToken.END_LINE_WHITE_SPACE;
    PropertiesToken t = new PropertiesToken(lastToken,"");
    yybegin(nextState);
    return (t);
 }
            case 36: break;
            case VALUE:
              { 
    nextState = DONE;
    lastToken = PropertiesToken.END_LINE_WHITE_SPACE;
    PropertiesToken t = new PropertiesToken(lastToken,"");
    yybegin(nextState);
    return (t);
 }
            case 37: break;
            case YYINITIAL:
              { 
    nextState = DONE;
    lastToken = PropertiesToken.END_LINE_WHITE_SPACE;
    PropertiesToken t = new PropertiesToken(lastToken,"");
    yybegin(nextState);
    return (t);
 }
            case 38: break;
            case LINE_END:
              { 
    nextState = DONE;
    lastToken = PropertiesToken.END_LINE_WHITE_SPACE;
    PropertiesToken t = new PropertiesToken(lastToken,"");
    yybegin(nextState);
    return (t);
 }
            case 39: break;
            case NAME:
              { 
    nextState = DONE;
    lastToken = PropertiesToken.END_LINE_WHITE_SPACE;
    PropertiesToken t = new PropertiesToken(lastToken,"");
    yybegin(nextState);
    return (t);
 }
            case 40: break;
            default:
            return null;
            }
          } 
          else {
            yy_ScanError(YY_NO_MATCH);
          }
      }
    }
  }


}
