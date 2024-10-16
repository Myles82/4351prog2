package Parse;
import ErrorMsg.ErrorMsg;


class Yylex implements Lexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final char YYEOF = '\uFFFF';

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 1;
	private final int YYINITIAL = 0;
	private final int COMMENT = 2;
	private final int yy_state_dtrans[] = {
		0,
		89,
		120
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private char yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_start () {
		++yychar;
		++yy_buffer_start;
	}
	private void yy_pushback () {
		--yy_buffer_end;
	}
	private void yy_mark_start () {
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
private int [][] unpackFromString(int size1, int size2, String st)
    {
      int colonIndex = -1;
      String lengthString;
      int sequenceLength = 0;
      int sequenceInteger = 0;
      int commaIndex;
      String workString;
      int res[][] = new int[size1][size2];
      for (int i= 0; i < size1; i++)
	for (int j= 0; j < size2; j++)
	  {
	    if (sequenceLength == 0) 
	      {	
		commaIndex = st.indexOf(',');
		if (commaIndex == -1)
		  workString = st;
		else
		  workString = st.substring(0, commaIndex);
		st = st.substring(commaIndex+1);
		colonIndex = workString.indexOf(':');
		if (colonIndex == -1)
		  {
		    res[i][j] = Integer.parseInt(workString);
		  }
		else 
		  {
		    lengthString = workString.substring(colonIndex+1);  
		    sequenceLength = Integer.parseInt(lengthString);
		    workString = workString.substring(0,colonIndex);
		    sequenceInteger = Integer.parseInt(workString);
		    res[i][j] = sequenceInteger;
		    sequenceLength--;
		  }
	      }
	    else 
	      {
		res[i][j] = sequenceInteger;
		sequenceLength--;
	      }
	  }
      return res;
    }
	private int yy_acpt[] = {
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR
	};
	private int yy_cmap[] = {
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 1, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		2, 3, 4, 5, 0, 6, 7, 8,
		9, 10, 11, 12, 13, 14, 15, 16,
		17, 18, 18, 18, 18, 18, 18, 19,
		18, 18, 20, 21, 22, 23, 24, 25,
		0, 26, 27, 26, 26, 26, 26, 26,
		26, 26, 26, 26, 26, 26, 26, 26,
		26, 26, 26, 26, 26, 26, 26, 26,
		26, 26, 26, 28, 29, 30, 31, 26,
		0, 32, 33, 34, 35, 36, 37, 38,
		39, 40, 26, 41, 42, 43, 44, 45,
		46, 26, 47, 48, 49, 50, 51, 52,
		53, 54, 55, 56, 57, 58, 59, 0
		
	};
	private int yy_rmap[] = {
		0, 1, 1, 1, 1, 2, 3, 4,
		1, 1, 1, 5, 6, 1, 7, 1,
		8, 1, 1, 9, 10, 11, 1, 12,
		1, 1, 13, 1, 14, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 15, 1, 16, 1, 1, 1,
		17, 1, 1, 1, 12, 12, 1, 1,
		1, 1, 1, 1, 12, 12, 12, 12,
		12, 12, 12, 12, 12, 12, 12, 12,
		12, 12, 12, 12, 12, 12, 12, 12,
		12, 12, 12, 12, 12, 12, 12, 12,
		12, 18, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 19, 20, 21, 22, 23,
		24, 25, 26, 27, 28, 29, 30, 31,
		32, 33, 34, 35, 36, 37, 38, 39,
		40, 41, 42, 43, 44, 45, 46, 47,
		48, 49, 50, 51, 52, 53, 54, 55,
		56, 57, 58, 59, 60, 61, 62, 63,
		64, 65, 66, 67, 68, 69, 70, 71,
		72, 73, 74, 75, 76, 77, 78, 79,
		80, 81, 82, 83, 84, 85, 86, 87,
		88, 89, 90, 91, 92, 93, 94, 95,
		96, 97, 98, 99, 100, 101, 102, 103,
		104, 105, 106, 107, 108, 109, 110, 111,
		112, 113, 114, 115, 116, 117, 118, 119,
		120, 121, 122, 123, 124, 125, 126, 127,
		128, 129, 130, 131, 132, 133 
	};
	private int yy_nxt[][] = unpackFromString(134,60,
"1,2,3,108,4,5,6,7,8,9,10,11,12,13,14,15,16,107:3,17,18,19,20,21,22,23:2,24,114,25,26,177,200,178,109,179,148,180,23,115,23:6,215,201,221,202,150,203,23:3,27,28,29,30,-1:65,32,-1:77,33,-1:43,34,-1:15,35,-1:59,36,-1:48,37,-1:10,38,-1:50,39,-1:8,40,-1:47,41,-1:4,42,-1:6,43,-1:58,44,45,-1:59,46,-1:59,47,48,-1:52,23:3,-1:6,23:2,-1:4,23:24,-1:27,51,-1:59,54,-1:33,55,-1:2,42,56,42:58,-1:23,57,-1:59,58,-1:36,90,-1,90:2,91,90:3,92,90:6,93,90:13,111,90:20,112,90:9,-1:17,107:3,-1:63,31,-1:53,23:3,-1:6,23:2,-1:4,23:4,216,23:8,52,23:10,-1:21,59,-1:43,94,-1:2,95,-1:3,96,-1:20,97,-1:3,98,-1:3,99,-1:6,100,-1:2,101,-1,102,-1:27,116,-1:58,106,-1:72,110,-1:3,49,-1:10,50,-1:32,23:3,-1:6,23:2,-1:4,23:5,53,23:6,119,23:11,-1:21,149,-1:59,23:3,-1:6,23:2,-1:4,23:15,60,23:8,-1:23,103,-1:7,104,-1:49,23:3,-1:6,23:2,-1:4,23:17,61,23:6,-1:4,105,2,105:9,113,105:48,-1:17,23:3,-1:6,23:2,-1:4,23:15,62,23:8,-1:21,23:3,-1:6,23:2,-1:4,23:13,63,23:10,-1:21,23:3,-1:6,23:2,-1:4,23:4,64,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:15,65,23:8,-1:21,23:3,-1:6,23:2,-1:4,23:4,66,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:11,67,23:12,-1:21,23:3,-1:6,23:2,-1:4,23:13,68,23:10,-1:21,23:3,-1:6,23:2,-1:4,23:3,69,23:20,-1:21,23:3,-1:6,23:2,-1:4,23:9,70,23:14,-1:21,23:3,-1:6,23:2,-1:4,23:17,71,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:17,72,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:17,73,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:12,74,23:11,-1:21,23:3,-1:6,23:2,-1:4,23:4,75,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:12,76,23:11,-1:21,23:3,-1:6,23:2,-1:4,23:12,77,23:11,-1:21,23:3,-1:6,23:2,-1:4,23:3,78,23:20,-1:21,23:3,-1:6,23:2,-1:4,23:5,79,23:18,-1:21,23:3,-1:6,23:2,-1:4,23:2,80,23:21,-1:21,23:3,-1:6,23:2,-1:4,23:17,81,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:7,82,23:16,-1:21,23:3,-1:6,23:2,-1:4,23:17,83,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:5,84,23:18,-1:21,23:3,-1:6,23:2,-1:4,23:4,85,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:15,86,23:8,-1:21,23:3,-1:6,23:2,-1:4,23:3,87,23:20,-1:21,23:3,-1:6,23:2,-1:4,23:4,88,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:10,183,23:2,117,23:10,-1:21,118,-1:59,23:3,-1:6,23:2,-1:4,121,23:12,157,23:10,-1:21,23:3,-1:6,23:2,-1:4,23:17,122,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:16,123,23:7,-1:21,23:3,-1:6,23:2,-1:4,124,23:23,-1:21,23:3,-1:6,23:2,-1:4,23:16,125,23:7,-1:21,23:3,-1:6,23:2,-1:4,23:18,126,23:5,-1:21,23:3,-1:6,23:2,-1:4,23:17,127,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:8,128,23,220,23:13,-1:21,23:3,-1:6,23:2,-1:4,129,23:23,-1:21,23:3,-1:6,23:2,-1:4,23:16,130,211,23:6,-1:21,23:3,-1:6,23:2,-1:4,131,23:23,-1:21,23:3,-1:6,23:2,-1:4,23:15,132,23:8,-1:21,23:3,-1:6,23:2,-1:4,23:13,133,23:10,-1:21,23:3,-1:6,23:2,-1:4,23:10,134,23:13,-1:21,23:3,-1:6,23:2,-1:4,23:15,135,23:8,-1:21,23:3,-1:6,23:2,-1:4,23:15,136,23:8,-1:21,23:3,-1:6,23:2,-1:4,23:4,137,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:13,138,23:10,-1:21,23:3,-1:6,23:2,-1:4,23:8,139,23:15,-1:21,23:3,-1:6,23:2,-1:4,23:2,140,23:21,-1:21,23:3,-1:6,23:2,-1:4,23:2,141,23:21,-1:21,23:3,-1:6,23:2,-1:4,23:10,142,23:13,-1:21,23:3,-1:6,23:2,-1:4,23:4,143,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:18,144,23:5,-1:21,23:3,-1:6,23:2,-1:4,23:4,145,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:4,146,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:10,147,23:13,-1:21,23:3,-1:6,23:2,-1:4,23:18,151,23:5,-1:21,23:3,-1:6,23:2,-1:4,152,23:6,153,23:5,182,23:10,-1:21,23:3,-1:6,23:2,-1:4,23:10,154,23,155,23:8,204,23:2,-1:21,23:3,-1:6,23:2,-1:4,23:13,156,23:10,-1:21,23:3,-1:6,23:2,-1:4,23:4,158,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:12,159,23:11,-1:21,23:3,-1:6,23:2,-1:4,23:13,160,23:10,-1:21,23:3,-1:6,23:2,-1:4,23:13,161,23:10,-1:21,23:3,-1:6,23:2,-1:4,23:8,162,23:7,219,23:7,-1:21,23:3,-1:6,23:2,-1:4,23:8,163,23:15,-1:21,23:3,-1:6,23:2,-1:4,23:4,164,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:18,165,23:5,-1:21,23:3,-1:6,23:2,-1:4,23:12,166,23:11,-1:21,23:3,-1:6,23:2,-1:4,23:4,167,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:17,168,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:18,169,23:5,-1:21,23:3,-1:6,23:2,-1:4,23:17,170,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:18,171,23:5,-1:21,23:3,-1:6,23:2,-1:4,23:3,172,23:20,-1:21,23:3,-1:6,23:2,-1:4,23:12,173,23:11,-1:21,23:3,-1:6,23:2,-1:4,23:17,174,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:12,175,23:11,-1:21,23:3,-1:6,23:2,-1:4,23:8,176,23:15,-1:21,23:3,-1:6,23:2,-1:4,23:15,181,23:8,-1:21,23:3,-1:6,23:2,-1:4,23:7,184,206,23:8,207,23:2,208,23:3,-1:21,23:3,-1:6,23:2,-1:4,23:12,185,23:11,-1:21,23:3,-1:6,23:2,-1:4,23:7,186,23:16,-1:21,23:3,-1:6,23:2,-1:4,23:17,187,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:6,218,23:10,188,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:6,189,23:16,190,-1:21,23:3,-1:6,23:2,-1:4,191,23:14,192,23:8,-1:21,23:3,-1:6,23:2,-1:4,23:8,193,23:15,-1:21,23:3,-1:6,23:2,-1:4,194,23:23,-1:21,23:3,-1:6,23:2,-1:4,23:4,195,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:8,196,23:15,-1:21,23:3,-1:6,23:2,-1:4,23:16,197,23:7,-1:21,23:3,-1:6,23:2,-1:4,23:6,198,23:17,-1:21,23:3,-1:6,23:2,-1:4,23:17,199,23:6,-1:21,23:3,-1:6,23:2,-1:4,23:4,205,23:19,-1:21,23:3,-1:6,23:2,-1:4,23:5,209,23:18,-1:21,23:3,-1:6,23:2,-1:4,23:14,210,23:9,-1:21,23:3,-1:6,23:2,-1:4,23:8,212,23:15,-1:21,23:3,-1:6,23:2,-1:4,23:8,213,23:15,-1:21,23:3,-1:6,23:2,-1:4,214,23:23,-1:21,23:3,-1:6,23:2,-1:4,23:22,217,23,-1:4");
	public java_cup.runtime.Symbol nextToken ()
		throws java.io.IOException {
		char yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			if (YYEOF != yy_lookahead) {
				yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YYEOF == yy_lookahead && true == yy_initial) {

	{
	 return tok(sym.EOF, null);
        }
				}
				else if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_to_mark();
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_pushback();
					}
					if (0 != (YY_START & yy_anchor)) {
						yy_move_start();
					}
					switch (yy_last_accept_state) {
					case 0:
						{return tok(sym.INT_LITERAL,yytext());}
					case -2:
						break;
					case 1:
						{ err("Illegal character: " + yytext()); }
					case -3:
						break;
					case 2:
						{newline();}
					case -4:
						break;
					case 3:
						{}
					case -5:
						break;
					case 4:
						{yybegin(STRING); s = ""; }
					case -6:
						break;
					case 5:
						{return tok(sym.POUND);}
					case -7:
						break;
					case 6:
						{return tok(sym.PERCENT);}
					case -8:
						break;
					case 7:
						{return tok(sym.AND);}
					case -9:
						break;
					case 8:
						{yybegin(STRING); s = ""; }
					case -10:
						break;
					case 9:
						{return tok(sym.LPAREN);}
					case -11:
						break;
					case 10:
						{return tok(sym.RPAREN);}
					case -12:
						break;
					case 11:
						{return tok(sym.STAR);}
					case -13:
						break;
					case 12:
						{return tok(sym.PLUS);}
					case -14:
						break;
					case 13:
						{return tok(sym.COMMA);}
					case -15:
						break;
					case 14:
						{return tok(sym.MINUS);}
					case -16:
						break;
					case 15:
						{return tok(sym.DOT);}
					case -17:
						break;
					case 16:
						{return tok(sym.DIVIDE);}
					case -18:
						break;
					case 17:
						{return tok(sym.COLON);}
					case -19:
						break;
					case 18:
						{return tok(sym.SEMICOLON);}
					case -20:
						break;
					case 19:
						{return tok(sym.LT);}
					case -21:
						break;
					case 20:
						{return tok(sym.ASSIGN);}
					case -22:
						break;
					case 21:
						{return tok(sym.GT);}
					case -23:
						break;
					case 22:
						{return tok(sym.QUESTION_MARK);}
					case -24:
						break;
					case 23:
						{return tok(sym.ID,yytext());}
					case -25:
						break;
					case 24:
						{return tok(sym.LBRACKET);}
					case -26:
						break;
					case 25:
						{return tok(sym.RBRACKET);}
					case -27:
						break;
					case 26:
						{return tok(sym.CARROT);}
					case -28:
						break;
					case 27:
						{return tok(sym.LBRACE);}
					case -29:
						break;
					case 28:
						{return tok(sym.B_OR);}
					case -30:
						break;
					case 29:
						{return tok(sym.RBRACE);}
					case -31:
						break;
					case 30:
						{return tok(sym.TILDA);}
					case -32:
						break;
					case 31:
						{return tok(sym.NOT_EQUAL);}
					case -33:
						break;
					case 32:
						{return tok(sym.POUND_POUND);}
					case -34:
						break;
					case 33:
						{return tok(sym.PERCENT_EQUAL);}
					case -35:
						break;
					case 34:
						{return tok(sym.AND_AND);}
					case -36:
						break;
					case 35:
						{return tok(sym.AND_EQUAL);}
					case -37:
						break;
					case 36:
						{return tok(sym.STAR_EQUAL);}
					case -38:
						break;
					case 37:
						{return tok(sym.PLUS_PLUS);}
					case -39:
						break;
					case 38:
						{return tok(sym.PLUS_EQUAL);}
					case -40:
						break;
					case 39:
						{return tok(sym.MINUS_MINUS);}
					case -41:
						break;
					case 40:
						{return tok(sym.MINUS_EQUAL);}
					case -42:
						break;
					case 41:
						{yybegin(COMMENT); }
					case -43:
						break;
					case 43:
						{return tok(sym.SLASH_EQUAL);}
					case -44:
						break;
					case 44:
						{return tok(sym.DOUBLE_LEFT_ARROW);}
					case -45:
						break;
					case 45:
						{return tok(sym.LE);}
					case -46:
						break;
					case 46:
						{return tok(sym.EQUAL_EQUAL);}
					case -47:
						break;
					case 47:
						{return tok(sym.GE);}
					case -48:
						break;
					case 48:
						{return tok(sym.DOUBLE_RIGHT_ARROW);}
					case -49:
						break;
					case 49:
						{return tok(sym.BACKSPACE);}
					case -50:
						break;
					case 50:
						{return tok(sym.BACKSPACE);}
					case -51:
						break;
					case 51:
						{return tok(sym.CARROT_EQUAL);}
					case -52:
						break;
					case 52:
						{return tok(sym.DO);}
					case -53:
						break;
					case 53:
						{return tok(sym.IF);}
					case -54:
						break;
					case 54:
						{return tok(sym.OR_EQUAL);}
					case -55:
						break;
					case 55:
						{return tok(sym.OR);}
					case -56:
						break;
					case 56:
						{return tok(sym.COMMENT);}
					case -57:
						break;
					case 57:
						{return tok(sym.DOUBLE_LEFT_ARROW_EQUAL);}
					case -58:
						break;
					case 58:
						{return tok(sym.DOUBLE_RIGHT_ARROW_EQUAL);}
					case -59:
						break;
					case 59:
						{return tok(sym.EOF);}
					case -60:
						break;
					case 60:
						{return tok(sym.FOR);}
					case -61:
						break;
					case 61:
						{return tok(sym.INT);}
					case -62:
						break;
					case 62:
						{return tok(sym.VAR);}
					case -63:
						break;
					case 63:
						{return tok(sym.AUTO);}
					case -64:
						break;
					case 64:
						{ return tok(sym.CASE); }
					case -65:
						break;
					case 65:
						{ return tok(sym.CHAR); }
					case -66:
						break;
					case 66:
						{return tok(sym.ELSE);}
					case -67:
						break;
					case 67:
						{ return tok(sym.ENUM); }
					case -68:
						break;
					case 68:
						{ return tok(sym.GOTO); }
					case -69:
						break;
					case 69:
						{ return tok(sym.VOID); }
					case -70:
						break;
					case 70:
						{return tok(sym.BREAK);}
					case -71:
						break;
					case 71:
						{ return tok(sym.CONST); }
					case -72:
						break;
					case 72:
						{ return tok(sym.FLOAT); }
					case -73:
						break;
					case 73:
						{ return tok(sym.SHORT); }
					case -74:
						break;
					case 74:
						{ return tok(sym.UNION); }
					case -75:
						break;
					case 75:
						{return tok(sym.WHILE);}
					case -76:
						break;
					case 76:
						{ return tok(sym.EXTERN); }
					case -77:
						break;
					case 77:
						{ return tok(sym.RETURN); }
					case -78:
						break;
					case 78:
						{ return tok(sym.SIGNED); }
					case -79:
						break;
					case 79:
						{ return tok(sym.SIZEOF); }
					case -80:
						break;
					case 80:
						{ return tok(sym.STATIC); }
					case -81:
						break;
					case 81:
						{return tok(sym.STRUCT);}
					case -82:
						break;
					case 82:
						{return tok(sym.SWITCH);}
					case -83:
						break;
					case 83:
						{ return tok(sym.DEFAULT); }
					case -84:
						break;
					case 84:
						{ return tok(sym.TYPEDEF); }
					case -85:
						break;
					case 85:
						{ return tok(sym.CONTINUE); }
					case -86:
						break;
					case 86:
						{ return tok(sym.REGISTER); }
					case -87:
						break;
					case 87:
						{ return tok(sym.UNSIGNED); }
					case -88:
						break;
					case 88:
						{ return tok(sym.VOLATILE); }
					case -89:
						break;
					case 89:
						{ s += '?'; }
					case -90:
						break;
					case 90:
						{ s += yytext(); }
					case -91:
						break;
					case 91:
						{yybegin(YYINITIAL); return tok(sym.STRING,s); }
					case -92:
						break;
					case 92:
						{yybegin(YYINITIAL); return tok(sym.STRING,s); }
					case -93:
						break;
					case 93:
						{ err("Illegal character: " + yytext()); }
					case -94:
						break;
					case 94:
						{newline(); err("Illegal character: " + yytext()); }
					case -95:
						break;
					case 95:
						{ s += '\"'; }
					case -96:
						break;
					case 96:
						{ s += '\''; }
					case -97:
						break;
					case 97:
						{ s += '\\'; }
					case -98:
						break;
					case 98:
						{ s += '\b'; }
					case -99:
						break;
					case 99:
						{ s += '\f'; }
					case -100:
						break;
					case 100:
						{ s += '\n'; newline(); }
					case -101:
						break;
					case 101:
						{ s += '\r'; }
					case -102:
						break;
					case 102:
						{ s += '\t'; }
					case -103:
						break;
					case 103:
						{ s += '\u0007'; }
					case -104:
						break;
					case 104:
						{ s += '\u000B'; }
					case -105:
						break;
					case 105:
						{}
					case -106:
						break;
					case 106:
						{yybegin(YYINITIAL); return tok(sym.COMMENT); }
					case -107:
						break;
					case 107:
						{return tok(sym.INT_LITERAL,yytext());}
					case -108:
						break;
					case 108:
						{ err("Illegal character: " + yytext()); }
					case -109:
						break;
					case 109:
						{return tok(sym.ID,yytext());}
					case -110:
						break;
					case 111:
						{ s += '?'; }
					case -111:
						break;
					case 112:
						{ s += yytext(); }
					case -112:
						break;
					case 113:
						{}
					case -113:
						break;
					case 114:
						{ err("Illegal character: " + yytext()); }
					case -114:
						break;
					case 115:
						{return tok(sym.ID,yytext());}
					case -115:
						break;
					case 117:
						{return tok(sym.ID,yytext());}
					case -116:
						break;
					case 119:
						{return tok(sym.ID,yytext());}
					case -117:
						break;
					case 121:
						{return tok(sym.ID,yytext());}
					case -118:
						break;
					case 122:
						{return tok(sym.ID,yytext());}
					case -119:
						break;
					case 123:
						{return tok(sym.ID,yytext());}
					case -120:
						break;
					case 124:
						{return tok(sym.ID,yytext());}
					case -121:
						break;
					case 125:
						{return tok(sym.ID,yytext());}
					case -122:
						break;
					case 126:
						{return tok(sym.ID,yytext());}
					case -123:
						break;
					case 127:
						{return tok(sym.ID,yytext());}
					case -124:
						break;
					case 128:
						{return tok(sym.ID,yytext());}
					case -125:
						break;
					case 129:
						{return tok(sym.ID,yytext());}
					case -126:
						break;
					case 130:
						{return tok(sym.ID,yytext());}
					case -127:
						break;
					case 131:
						{return tok(sym.ID,yytext());}
					case -128:
						break;
					case 132:
						{return tok(sym.ID,yytext());}
					case -129:
						break;
					case 133:
						{return tok(sym.ID,yytext());}
					case -130:
						break;
					case 134:
						{return tok(sym.ID,yytext());}
					case -131:
						break;
					case 135:
						{return tok(sym.ID,yytext());}
					case -132:
						break;
					case 136:
						{return tok(sym.ID,yytext());}
					case -133:
						break;
					case 137:
						{return tok(sym.ID,yytext());}
					case -134:
						break;
					case 138:
						{return tok(sym.ID,yytext());}
					case -135:
						break;
					case 139:
						{return tok(sym.ID,yytext());}
					case -136:
						break;
					case 140:
						{return tok(sym.ID,yytext());}
					case -137:
						break;
					case 141:
						{return tok(sym.ID,yytext());}
					case -138:
						break;
					case 142:
						{return tok(sym.ID,yytext());}
					case -139:
						break;
					case 143:
						{return tok(sym.ID,yytext());}
					case -140:
						break;
					case 144:
						{return tok(sym.ID,yytext());}
					case -141:
						break;
					case 145:
						{return tok(sym.ID,yytext());}
					case -142:
						break;
					case 146:
						{return tok(sym.ID,yytext());}
					case -143:
						break;
					case 147:
						{return tok(sym.ID,yytext());}
					case -144:
						break;
					case 148:
						{return tok(sym.ID,yytext());}
					case -145:
						break;
					case 150:
						{return tok(sym.ID,yytext());}
					case -146:
						break;
					case 151:
						{return tok(sym.ID,yytext());}
					case -147:
						break;
					case 152:
						{return tok(sym.ID,yytext());}
					case -148:
						break;
					case 153:
						{return tok(sym.ID,yytext());}
					case -149:
						break;
					case 154:
						{return tok(sym.ID,yytext());}
					case -150:
						break;
					case 155:
						{return tok(sym.ID,yytext());}
					case -151:
						break;
					case 156:
						{return tok(sym.ID,yytext());}
					case -152:
						break;
					case 157:
						{return tok(sym.ID,yytext());}
					case -153:
						break;
					case 158:
						{return tok(sym.ID,yytext());}
					case -154:
						break;
					case 159:
						{return tok(sym.ID,yytext());}
					case -155:
						break;
					case 160:
						{return tok(sym.ID,yytext());}
					case -156:
						break;
					case 161:
						{return tok(sym.ID,yytext());}
					case -157:
						break;
					case 162:
						{return tok(sym.ID,yytext());}
					case -158:
						break;
					case 163:
						{return tok(sym.ID,yytext());}
					case -159:
						break;
					case 164:
						{return tok(sym.ID,yytext());}
					case -160:
						break;
					case 165:
						{return tok(sym.ID,yytext());}
					case -161:
						break;
					case 166:
						{return tok(sym.ID,yytext());}
					case -162:
						break;
					case 167:
						{return tok(sym.ID,yytext());}
					case -163:
						break;
					case 168:
						{return tok(sym.ID,yytext());}
					case -164:
						break;
					case 169:
						{return tok(sym.ID,yytext());}
					case -165:
						break;
					case 170:
						{return tok(sym.ID,yytext());}
					case -166:
						break;
					case 171:
						{return tok(sym.ID,yytext());}
					case -167:
						break;
					case 172:
						{return tok(sym.ID,yytext());}
					case -168:
						break;
					case 173:
						{return tok(sym.ID,yytext());}
					case -169:
						break;
					case 174:
						{return tok(sym.ID,yytext());}
					case -170:
						break;
					case 175:
						{return tok(sym.ID,yytext());}
					case -171:
						break;
					case 176:
						{return tok(sym.ID,yytext());}
					case -172:
						break;
					case 177:
						{return tok(sym.ID,yytext());}
					case -173:
						break;
					case 178:
						{return tok(sym.ID,yytext());}
					case -174:
						break;
					case 179:
						{return tok(sym.ID,yytext());}
					case -175:
						break;
					case 180:
						{return tok(sym.ID,yytext());}
					case -176:
						break;
					case 181:
						{return tok(sym.ID,yytext());}
					case -177:
						break;
					case 182:
						{return tok(sym.ID,yytext());}
					case -178:
						break;
					case 183:
						{return tok(sym.ID,yytext());}
					case -179:
						break;
					case 184:
						{return tok(sym.ID,yytext());}
					case -180:
						break;
					case 185:
						{return tok(sym.ID,yytext());}
					case -181:
						break;
					case 186:
						{return tok(sym.ID,yytext());}
					case -182:
						break;
					case 187:
						{return tok(sym.ID,yytext());}
					case -183:
						break;
					case 188:
						{return tok(sym.ID,yytext());}
					case -184:
						break;
					case 189:
						{return tok(sym.ID,yytext());}
					case -185:
						break;
					case 190:
						{return tok(sym.ID,yytext());}
					case -186:
						break;
					case 191:
						{return tok(sym.ID,yytext());}
					case -187:
						break;
					case 192:
						{return tok(sym.ID,yytext());}
					case -188:
						break;
					case 193:
						{return tok(sym.ID,yytext());}
					case -189:
						break;
					case 194:
						{return tok(sym.ID,yytext());}
					case -190:
						break;
					case 195:
						{return tok(sym.ID,yytext());}
					case -191:
						break;
					case 196:
						{return tok(sym.ID,yytext());}
					case -192:
						break;
					case 197:
						{return tok(sym.ID,yytext());}
					case -193:
						break;
					case 198:
						{return tok(sym.ID,yytext());}
					case -194:
						break;
					case 199:
						{return tok(sym.ID,yytext());}
					case -195:
						break;
					case 200:
						{return tok(sym.ID,yytext());}
					case -196:
						break;
					case 201:
						{return tok(sym.ID,yytext());}
					case -197:
						break;
					case 202:
						{return tok(sym.ID,yytext());}
					case -198:
						break;
					case 203:
						{return tok(sym.ID,yytext());}
					case -199:
						break;
					case 204:
						{return tok(sym.ID,yytext());}
					case -200:
						break;
					case 205:
						{return tok(sym.ID,yytext());}
					case -201:
						break;
					case 206:
						{return tok(sym.ID,yytext());}
					case -202:
						break;
					case 207:
						{return tok(sym.ID,yytext());}
					case -203:
						break;
					case 208:
						{return tok(sym.ID,yytext());}
					case -204:
						break;
					case 209:
						{return tok(sym.ID,yytext());}
					case -205:
						break;
					case 210:
						{return tok(sym.ID,yytext());}
					case -206:
						break;
					case 211:
						{return tok(sym.ID,yytext());}
					case -207:
						break;
					case 212:
						{return tok(sym.ID,yytext());}
					case -208:
						break;
					case 213:
						{return tok(sym.ID,yytext());}
					case -209:
						break;
					case 214:
						{return tok(sym.ID,yytext());}
					case -210:
						break;
					case 215:
						{return tok(sym.ID,yytext());}
					case -211:
						break;
					case 216:
						{return tok(sym.ID,yytext());}
					case -212:
						break;
					case 217:
						{return tok(sym.ID,yytext());}
					case -213:
						break;
					case 218:
						{return tok(sym.ID,yytext());}
					case -214:
						break;
					case 219:
						{return tok(sym.ID,yytext());}
					case -215:
						break;
					case 220:
						{return tok(sym.ID,yytext());}
					case -216:
						break;
					case 221:
						{return tok(sym.ID,yytext());}
					case -217:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
					}
				}
			}
		}
	}
}
