package ai.push.logic;

import java.util.Random;

public class HashUtils {
	
	public static long[][] ZOBRIST_KEYS;
	public static final long[] SOME_RANDOM_LONGS = {
		0xdeadbeef,
		0xe1fb1511,
		0x5eed5eed
	};
	private long x;
	private int width;
	
	public HashUtils(int width) {
		this.width = width;
		ZOBRIST_KEYS = new long[2][width*width];
		x = SOME_RANDOM_LONGS[new Random().nextInt(2)];
		for (int i = 0; i < width * width; ++i) {
			x ^= (x << 21);
			x ^= (x >>> 35);
			x ^= (x << 4);
			ZOBRIST_KEYS[0][i] = x;
			x ^= (x << 21);
			x ^= (x >>> 35);
			x ^= (x << 4);
			ZOBRIST_KEYS[1][i] = x;
		}
	}	
	
	public boolean checkUnique() {
		long a, b;
		boolean found = false;
		for (int i = 0; i < width*width; ++i) {	
			a = ZOBRIST_KEYS[0][i];
			b = ZOBRIST_KEYS[0][i];
			for (int j = i+1; j < width*width; ++j) {
				if (ZOBRIST_KEYS[0][j] == a || ZOBRIST_KEYS[1][j] == b) {
					found = true;
					return false;
				}
			}
		}
		if (!found)
			return true;
		return false;		
	}
	
	/*
	public static final byte[][] CHAR_ID_8X8 = new byte[][] {
		{ 0,  0,  0,  0,  0,  1,  1,  1},
		{ 1,  1,  2,  2,  2,  2,  2,  3},
		{ 3,  3,  3,  3,  4,  4,  4,  4},
		{ 4,  5,  5,  5,  5,  5,  6,  6},
		{ 6,  6,  6,  7,  7,  7,  7,  7},
		{ 8,  8,  8,  8,  8,  9,  9,  9},
		{ 9,  9, 10, 10, 10, 10, 10, 11},
		{11, 11, 11, 11, 12, 12, 12, 12}		
	};
	
	public static final byte[][] BIT_MASK_8X8 = new byte[][] {
		{ 0,  3,  6,  9, 12,  0,  3,  6},
		{ 9, 12,  0,  3,  6,  9, 12,  0},
		{ 3,  6,  9, 12,  0,  3,  6,  9},
		{12,  0,  3,  6,  9, 12,  0,  3},
		{ 6,  9, 12,  0,  3,  6,  9, 12},
		{ 0,  3,  6,  9, 12,  0,  3,  6},
		{ 9, 12,  0,  3,  6,  9, 12,  0},
		{ 3,  6,  9, 12,  0,  3,  6,  9}		
	};
	
	static public void setTabVal(char[] tab,byte row,byte col, byte val)
	{
		boolean cont[]=new boolean[] {false, false, false};
		cont[val]=true;
		//for(int i=0;i<3;i++)
		//{
//			if(cont[i])
//			{
//				tab[CHAR_ID_8X8[row][col]]|=(1 << BIT_MASK_8X8[row][col]+i);
//			}
//			else
//			{
//				tab[CHAR_ID_8X8[row][col]]&=~(1 << BIT_MASK_8X8[row][col]+i);
//			}
		//}
		
		if(cont[0])
		{
			tab[CHAR_ID_8X8[row][col]]|=(1 << BIT_MASK_8X8[row][col]);
		}
		else
		{
			tab[CHAR_ID_8X8[row][col]]&=~(1 << BIT_MASK_8X8[row][col]);
		}
		
		if(cont[1])
		{
			tab[CHAR_ID_8X8[row][col]]|=(1 << BIT_MASK_8X8[row][col]+1);
		}
		else
		{
			tab[CHAR_ID_8X8[row][col]]&=~(1 << BIT_MASK_8X8[row][col]+1);
		}
		
		if(cont[2])
		{
			tab[CHAR_ID_8X8[row][col]]|=(1 << BIT_MASK_8X8[row][col]+2);
		}
		else
		{
			tab[CHAR_ID_8X8[row][col]]&=~(1 << BIT_MASK_8X8[row][col]+2);
		}
	}
	*/
}

/*





                       =NMMMMMMMMMMMMMMMMD,....                                 
                 .. ,IMMMMMMMMMMMMMMMMMMMMMMMMMN...                             
                :NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN$+,...                       
            ..:8MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN..                      
        .  .ZNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM...                    
       ...NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM~..                   
       ..MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM:.                   
     ...=MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM....                
    . .OMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM~. .               
   ..:DMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM... .             
  ..MMMMMMMMMMMMMMMMMMMMMMMMMMMMDMMMMMMMMMMMMMMMMMMMMMMMMMMMMMI. .              
. IMMMMMMMMMMMMMMMMMMMMMMM7, .......MMMMMMMMMMMMMMMMMMMMMMMMMMMN..              
.+MMMMMMMMMMMMMMMMMMMM+.. . ..     ...IDMMMMMMMMMMMMMMMMMMMMMMMM...             
.+MMMMMMMMMMMMMMMMMMI...              ...MMMMMMMMMMMMMMMMMMMMMMM=.              
.OMMMMMMMMMMMMMMZ~..                  . ,MMMMMMMMMMMMMMMMMMMMMMMM..             
.:MMMMMMMMI.                            :MMMMMMMMMMMMMMMMMMMMMMMM..             
.+MMMMMMM .                            .:MMMMMMMMMMMMMMMMMMMMMMMM?..            
.. DMMMM..         . ..                ..MMMMMMMMMMMMMMMMMMMMMMMMM..            
  .,NMM+.        ...:ID...              ..,MMMMMMMMMMMMMMMMMMMMMMM.             
  ..7MM.. ..:N. ..=DI. .  .               ..$MMMMMMMMMMMMMMMMMNMMM...           
 ...MMI...M?..88$...:8DN7...                ..MMMMMMMMMMMMM,.:~=:,:. .          
   .8M,.N,...,..~.,MMMMMMMNI,,,,...           .MMMMMMMMMMM..:.. .I$,.           
   .NM..:.8MMN.,.,MMMMMMMMMMMMMMMM$...        .,MMMMMMMMN.=I..   .=O.           
   .:M..:MMMMD,........=MMMMMMMMMMMM?+...      .MMMMMMM7......   .O$..          
   . .~OMMMM?.Z,8..     ....... .ZMD,.,...     .NMMMMM..  ..... ...8.           
    ..MMMMM=...Z:       ............8: ...      8MMMM,.  ..O:D..D=.Z..          
   ..NMMMM.........     .,DZD$....D?.~DZ.       NMMM7..  .:=....:=.D..          
   .MMMMMM:7D+..O. .N...O:MMMMMMI.+,+7=.        .....    ..  . .~=.D..          
 ...MMMM~.. ...N,Z. N,...M,.....I.....                  ..8. .  D:.D.           
  ..ZMM...,OMMMMO7.......O....NI...                     ...O...,=I.D.           
   ..7,..MMM?=~~8~...   ........                          .D.8.....D..          
     ,~,8M~.....++...                                   . .D,..  .D..           
     ..D:M..?N.=,D.         .                                . ..?..            
       =,:. ..D.~+.     . .... ..                           ....$,.             
      .,=. ..I..N..    ...:.=DO,..                        ...,$MN.              
     ..D....Z...,..    ... .+.  D~..                     ..7 .OMO..             
     .+:. ..,..D.         ..8. ...M.      . .             .Z. MM:.              
     .N.. .$. ZN .  ..$88M=N...  ..,~.. ..+: .  ...       .O .MM ..             
      D. ..N. $...  8:.. .. ..     ..D.....8..            .O.=MM..              
    ..D....D..=8M? .....  ......  ...:.+. .N. .          ..N.IMM..              
     ..D. ..Z.     . .....~ONNNND$....O.~..I.           ..:I.OM8..              
     . .,..,..  ....ID7:..=DMMMMMMMMM.O.=..+.             .=.MM$                
       .8.... ..:D:...7MMM7O=.,.=.Z~MO?,= .+.             .=.MM..               
       ..7. $..$~MMMMN,7.+.,..M:MNMMM8+:+..$.  .          .= N...               
         .N..D.~:MM$.7.=..7MMMMMMMMMM=..+.8...           . =.D..                
        ...N..8:.MMM$7MMMMMMMMMON8M.D...?.?.              .~.O..                
          ..N...=+.8MMMMMMM$$.+N+ .O....~,....             ~.7...               
            .:...N?=,NMO$,$M$,....8......8..              .M.+...               
              .N..7.,.... ....,$=... . D~~ .             .Z..,.                 
              ..D.,7.=MNMD7+,...    ..I:M..            ..D.  ., .               
                .I.=...            ..7.....          ..:,.   .7? .              
                 .D.I..            .N..8..         ...8..   ..$Z...             
                  .ZD ..           .. ....         ...=.     .....              
                 . D...                            ..Z..      ..O.   .          
                 ..D.              ..=.              ..        .7...            
                  .7. .          ...O:..                        .M+7..          
                   .Z...         ..N..                          .$..: .         
                   ..Z,..... ...ZZ...                            .N.:O..        
                      87O8:=D8..                                 .MZ..N,..      
                   . .~,...D...                                  .,M....?8...  .
                    ,8..D....N ..                                ....    .:N..  
                ....?.   O..... .                                . ...    . D.. 
                  .D.. ..:..  ..                                  .,.       .D. 
                 .8.. . ..~7..                                   ..Z.       ..  
                 ..  .   ... ..                                  ..N.           
                                                                  ~=..          

*/