/*
 * Base64 encoding and decoding.
 * Copyright (C) 2001-2002 Stephen Ostermiller
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
import gnu.getopt.*;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Locale;

/**
 * Implements Base64 encoding and decoding as defined by RFC 2045: "Multipurpose Internet
 * Mail Extensions (MIME) Part One: Format of Internet Message Bodies" page 23.
 * More information about this class is available from <a href=
 * "http://ostermiller.org/utils/Base64.html">ostermiller.org</a>.
 *
 * <blockquote>
 * <p>The Base64 Content-Transfer-Encoding is designed to represent
 * arbitrary sequences of octets in a form that need not be humanly
 * readable.  The encoding and decoding algorithms are simple, but the
 * encoded data are consistently only about 33 percent larger than the
 * unencoded data.  This encoding is virtually identical to the one used
 * in Privacy Enhanced Mail (PEM) applications, as defined in RFC 1421.</p>
 *
 * <p>A 65-character subset of US-ASCII is used, enabling 6 bits to be
 * represented per printable character. (The extra 65th character, "=",
 * is used to signify a special processing function.)</p>
 *
 * <p>NOTE:  This subset has the important property that it is represented
 * identically in all versions of ISO 646, including US-ASCII, and all
 * characters in the subset are also represented identically in all
 * versions of EBCDIC. Other popular encodings, such as the encoding
 * used by the uuencode utility, Macintosh binhex 4.0 [RFC-1741], and
 * the base85 encoding specified as part of Level 2 PostScript, do no
 * share these properties, and thus do not fulfill the portability
 * requirements a binary transport encoding for mail must meet.</p>
 *
 * <p>The encoding process represents 24-bit groups of input bits as output
 * strings of 4 encoded characters.  Proceeding from left to right, a
 * 24-bit input group is formed by concatenating 3 8bit input groups.
 * These 24 bits are then treated as 4 concatenated 6-bit groups, each
 * of which is translated into a single digit in the base64 alphabet.
 * When encoding a bit stream via the base64 encoding, the bit stream
 * must be presumed to be ordered with the most-significant-bit first.
 * That is, the first bit in the stream will be the high-order bit in
 * the first 8bit byte, and the eighth bit will be the low-order bit in
 * the first 8bit byte, and so on.</p>
 *
 * <p>Each 6-bit group is used as an index into an array of 64 printable
 * characters.  The character referenced by the index is placed in the
 * output string.  These characters, identified in Table 1, below, are
 * selected so as to be universally representable, and the set excludes
 * characters with particular significance to SMTP (e.g., ".", CR, LF)
 * and to the multipart boundary delimiters defined in RFC 2046 (e.g.,
 * "-").</p>
 * <pre>
 *                  Table 1: The Base64 Alphabet
 *
 *   Value Encoding  Value Encoding  Value Encoding  Value Encoding
 *       0 A            17 R            34 i            51 z
 *       1 B            18 S            35 j            52 0
 *       2 C            19 T            36 k            53 1
 *       3 D            20 U            37 l            54 2
 *       4 E            21 V            38 m            55 3
 *       5 F            22 W            39 n            56 4
 *       6 G            23 X            40 o            57 5
 *       7 H            24 Y            41 p            58 6
 *       8 I            25 Z            42 q            59 7
 *       9 J            26 a            43 r            60 8
 *      10 K            27 b            44 s            61 9
 *      11 L            28 c            45 t            62 +
 *      12 M            29 d            46 u            63 /
 *      13 N            30 e            47 v
 *      14 O            31 f            48 w         (pad) =
 *      15 P            32 g            49 x
 *      16 Q            33 h            50 y
 * </pre>
 * <p>The encoded output stream must be represented in lines of no more
 * than 76 characters each.  All line breaks or other characters no
 * found in Table 1 must be ignored by decoding software.  In base64
 * data, characters other than those in Table 1, line breaks, and other
 * white space probably indicate a transmission error, about which a
 * warning message or even a message rejection might be appropriate
 * under some circumstances.</p>
 *
 * <p>Special processing is performed if fewer than 24 bits are available
 * at the end of the data being encoded.  A full encoding quantum is
 * always completed at the end of a body.  When fewer than 24 input bits
 * are available in an input group, zero bits are added (on the right)
 * to form an integral number of 6-bit groups.  Padding at the end of
 * the data is performed using the "=" character.  Since all base64
 * input is an integral number of octets, only the following cases can
 * arise: (1) the final quantum of encoding input is an integral
 * multiple of 24 bits; here, the final unit of encoded output will be
 * an integral multiple of 4 characters with no "=" padding, (2) the
 * final quantum of encoding input is exactly 8 bits; here, the final
 * unit of encoded output will be two characters followed by two "="
 * padding characters, or (3) the final quantum of encoding input is
 * exactly 16 bits; here, the final unit of encoded output will be three
 * characters followed by one "=" padding character.</p>
 *
 * <p>Because it is used only for padding at the end of the data, the
 * occurrence of any "=" characters may be taken as evidence that the
 * end of the data has been reached (without truncation in transit).  No
 * such assurance is possible, however, when the number of octets
 * transmitted was a multiple of three and no "=" characters are
 * present.</p>
 *
 * <p>Any characters outside of the base64 alphabet are to be ignored in
 * base64-encoded data.</p>
 *
 * <p>Care must be taken to use the proper octets for line breaks if base64
 * encoding is applied directly to text material that has not been
 * converted to canonical form.  In particular, text line breaks must be
 * converted into CRLF sequences prior to base64 encoding.  The
 * important thing to note is that this may be done directly by the
 * encoder rather than in a prior canonicalization step in some
 * implementations.</p>
 *
 * <p>NOTE: There is no need to worry about quoting potential boundary
 * delimiters within base64-encoded bodies within multipart entities
 * because no hyphen characters are used in the base64 encoding.</p>
 * </blockquote>
 */
public class Base64 {

	/**
	 * Symbol that represents the end of an input stream
	 */
	private static final int END_OF_INPUT = -1;

	/**
	 * A character that is not a valid base 64 character.
	 */
	private static final int NON_BASE_64 = -1;

	/**
	 * A character that is not a valid base 64 character.
	 */
	private static final int NON_BASE_64_WHITESPACE = -2;

	/**
	 * A character that is not a valid base 64 character.
	 */
	private static final int NON_BASE_64_PADDING = -3;

	/**
	 * This class need not be instantiated, all methods are static.
	 */
	private Base64(){
	}

	/**
	 * Table of the sixty-four characters that are used as
	 * the Base64 alphabet: [A-Za-z0-9+/]
	 */
	protected static final byte[] base64Chars = {
		'A','B','C','D','E','F','G','H',
		'I','J','K','L','M','N','O','P',
		'Q','R','S','T','U','V','W','X',
		'Y','Z','a','b','c','d','e','f',
		'g','h','i','j','k','l','m','n',
		'o','p','q','r','s','t','u','v',
		'w','x','y','z','0','1','2','3',
		'4','5','6','7','8','9','+','/',
	};

	/**
	 * Reverse lookup table for the Base64 alphabet.
	 * reversebase64Chars[byte] gives n for the nth Base64
	 * character or negative if a character is not a Base64 character.
	 */
	protected static final byte[] reverseBase64Chars = new byte[0x100];
	static {
		// Fill in NON_BASE_64 for all characters to start with
		for (int i=0; i<reverseBase64Chars.length; i++){
			reverseBase64Chars[i] = NON_BASE_64;
		}
		// For characters that are base64Chars, adjust
		// the reverse lookup table.
		for (byte i=0; i < base64Chars.length; i++){
			reverseBase64Chars[base64Chars[i]] = i;
		}
		reverseBase64Chars[' '] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\n'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\r'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\t'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\f'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['='] = NON_BASE_64_PADDING;
	}

	/**
	 * Version number of this program
	 */
	public static final String version = "1.1";

	/**
	 * Locale specific strings displayed to the user.
	 */
	protected static ResourceBundle labels = ResourceBundle.getBundle("com.Ostermiller.util.Base64",  Locale.getDefault());

	private static final int ACTION_GUESS = 0;
	private static final int ACTION_ENCODE = 1;
	private static final int ACTION_DECODE = 2;

	/**
	 * Converts the line ending on files, or standard input.
	 * Run with --help argument for more information.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args){
		// create the command line options that we are looking for
		LongOpt[] longopts = {
			new LongOpt(labels.getString("help.option"), LongOpt.NO_ARGUMENT, null, 1),
			new LongOpt(labels.getString("version.option"), LongOpt.NO_ARGUMENT, null, 2),
			new LongOpt(labels.getString("about.option"), LongOpt.NO_ARGUMENT, null, 3),
			new LongOpt(labels.getString("encode.option"), LongOpt.NO_ARGUMENT, null, 'e'),
			new LongOpt(labels.getString("lines.option"), LongOpt.NO_ARGUMENT, null, 'l'),
			new LongOpt(labels.getString("nolines.option"), LongOpt.NO_ARGUMENT, null, 6),
			new LongOpt(labels.getString("decode.option"), LongOpt.NO_ARGUMENT, null, 'd'),
			new LongOpt(labels.getString("decodeall.option"), LongOpt.NO_ARGUMENT, null, 'a'),
			new LongOpt(labels.getString("decodegood.option"), LongOpt.NO_ARGUMENT, null, 5),
			new LongOpt(labels.getString("guess.option"), LongOpt.NO_ARGUMENT, null, 'g'),
			new LongOpt(labels.getString("ext.option"), LongOpt.OPTIONAL_ARGUMENT, null, 'x'),
			new LongOpt(labels.getString("force.option"), LongOpt.NO_ARGUMENT, null, 'f'),
			new LongOpt(labels.getString("quiet.option"), LongOpt.NO_ARGUMENT, null, 'q'),
			new LongOpt(labels.getString("reallyquiet.option"), LongOpt.NO_ARGUMENT, null, 'Q'),
			new LongOpt(labels.getString("verbose.option"), LongOpt.NO_ARGUMENT, null, 'v'),
			new LongOpt(labels.getString("noforce.option"), LongOpt.NO_ARGUMENT, null, 4),
		};
		String oneLetterOptions = "eldagx::fqQvV";
		Getopt opts = new Getopt(labels.getString("base64"), args, oneLetterOptions, longopts);
		int action = ACTION_GUESS;
		String extension = "base64";
		boolean force = false;
		boolean printMessages = true;
		boolean printErrors = true;
		boolean forceDecode = false;
		boolean lineBreaks = true;
		int c;
		while ((c = opts.getopt()) != -1){
			switch(c){
					case 1:{
					// print out the help message
					String[] helpFlags = new String[]{
						"--" + labels.getString("help.option"),
						"--" + labels.getString("version.option"),
						"--" + labels.getString("about.option"),
						"-g --" + labels.getString("guess.option"),
						"-e --" + labels.getString("encode.option"),
						"-l --" + labels.getString("lines.option"),
						"--" + labels.getString("nolines.option"),
						"-d --" + labels.getString("decode.option"),
						"-a --" + labels.getString("decodeall.option"),
						"--" + labels.getString("decodegood.option"),
						"-x --" + labels.getString("ext.option") + " <" + labels.getString("ext.option") + ">",
						"-f --" + labels.getString("force.option"),
						"--" + labels.getString("noforce.option"),
						"-v --" + labels.getString("verbose.option"),
						"-q --" + labels.getString("quiet.option"),
						"-Q --" + labels.getString("reallyquiet.option"),
					};
					int maxLength = 0;
					for (int i=0; i<helpFlags.length; i++){
						maxLength = Math.max(maxLength, helpFlags[i].length());
					}
					maxLength += 2;
					System.out.println(
						labels.getString("base64") + " [-" + StringHelper.replace(oneLetterOptions, ":", "") + "] <" + labels.getString("files") + ">\n" +
						labels.getString("purpose.message") + "\n" +
						"  " + labels.getString("stdin.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[0] ,maxLength, ' ') + labels.getString("help.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[1] ,maxLength, ' ') + labels.getString("version.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[2] ,maxLength, ' ') + labels.getString("about.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[3] ,maxLength, ' ') + labels.getString("g.message") + " (" + labels.getString("default") + ")\n" +
						"  " + StringHelper.postpad(helpFlags[4] ,maxLength, ' ') + labels.getString("e.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[5] ,maxLength, ' ') + labels.getString("l.message") + " (" + labels.getString("default") + ")\n" +
						"  " + StringHelper.postpad(helpFlags[6] ,maxLength, ' ') + labels.getString("nolines.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[7] ,maxLength, ' ') + labels.getString("d.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[8] ,maxLength, ' ') + labels.getString("a.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[9] ,maxLength, ' ') + labels.getString("decodegood.message") + " (" + labels.getString("default") + ")\n" +
						"  " + StringHelper.postpad(helpFlags[10] ,maxLength, ' ') + labels.getString("x.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[11] ,maxLength, ' ') + labels.getString("f.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[12] ,maxLength, ' ') + labels.getString("noforce.message") + " (" + labels.getString("default") + ")\n" +
						"  " + StringHelper.postpad(helpFlags[13] ,maxLength, ' ') + labels.getString("v.message") + " (" + labels.getString("default") + ")\n" +
						"  " + StringHelper.postpad(helpFlags[14] ,maxLength, ' ') + labels.getString("q.message") + "\n" +
						"  " + StringHelper.postpad(helpFlags[15] ,maxLength, ' ') + labels.getString("Q.message") + "\n"
					);
					System.exit(0);
				} break;
				case 2:{
					// print out the version message
					System.out.println(MessageFormat.format(labels.getString("version"), new String[] {version}));
					System.exit(0);
				} break;
				case 3:{
					System.out.println(
						labels.getString("base64") + " -- " + labels.getString("purpose.message") + "\n" +
						MessageFormat.format(labels.getString("copyright"), new String[] {"2001-2002", "Stephen Ostermiller (http://ostermiller.org/contact.pl?regarding=Java+Utilities)"}) + "\n\n" +
						labels.getString("license")
					);
					System.exit(0);
				} break;
				case 'd':{
					action = ACTION_DECODE;
				} break;
				case 'a':{
					forceDecode = true;
				} break;
				case 5:{
					forceDecode = false;
				} break;
				case 'e':{
					action = ACTION_ENCODE;
				} break;
				case 'l':{
					lineBreaks = true;
				} break;
				case 6:{
					lineBreaks = false;
				} break;
				case 'g':{
					action = ACTION_GUESS;
				} break;
				case 'x':{
					extension = opts.getOptarg();
					if (extension == null) extension = "";
				} break;
				case 'f':{
					force = true;
				} break;
				case 4:{
					force = false;
				} break;
				case 'v':{
					printMessages = true;
					printErrors = true;
				} break;
				case 'q':{
					printMessages = false;
					printErrors = true;
				} break;
				case 'Q':{
					printMessages = false;
					printErrors = false;
				} break;
				default:{
					System.exit(1);
				}
			}
		}

		int exitCond = 0;
		boolean done = false;
		for (int i=opts.getOptind(); i<args.length; i++){
			done = true;
			File source = new File(args[i]);
			if (!source.exists()){
				if(printErrors){
					System.err.println(MessageFormat.format(labels.getString("doesnotexist"), new String[] {args[i]}));
				}
				exitCond = 1;
			} else if (!source.canRead()){
				if(printErrors){
					System.err.println(MessageFormat.format(labels.getString("cantread"), new String[] {args[i]}));
				}
				exitCond = 1;
			} else {
				try {
					int fileAction = action;
					if (fileAction == ACTION_GUESS){
						if (isBase64(source)){
							fileAction = ACTION_DECODE;
						} else {
							fileAction = ACTION_ENCODE;
						}
					}
					String outName = args[i];
					if (extension.length() > 0){
						if (fileAction == ACTION_ENCODE){
							outName = args[i] + "." + extension;
						} else {
							if (args[i].endsWith("." + extension)){
								outName = args[i].substring(0, args[i].length() - (extension.length() + 1));
							}
						}
					}
					File outFile = new File(outName);
					if (!force && outFile.exists()){
						if(printErrors){
							System.err.println(MessageFormat.format(labels.getString("overwrite"), new String[] {outName}));
						}
						exitCond = 1;
					} else if (!(outFile.exists() || outFile.createNewFile()) || !outFile.canWrite()){
						if(printErrors){
							System.err.println(MessageFormat.format(labels.getString("cantwrite"), new String[] {outName}));
						}
						exitCond = 1;
					} else {
						if (fileAction == ACTION_ENCODE){
							if (printMessages){
								System.out.println(MessageFormat.format(labels.getString("encoding"), new String[] {args[i], outName}));
								encode(source, outFile, lineBreaks);
							}
						} else {
							if (printMessages){
								System.out.println(MessageFormat.format(labels.getString("decoding"), new String[] {args[i], outName}));
								decode(source, outFile, !forceDecode);
							}
						}
					}
				} catch (Base64DecodingException x){
					if(printErrors){
						System.err.println(args[i] + ": " + x.getMessage() + " " + labels.getString("unexpectedcharforce"));
					}
					exitCond = 1;
				} catch (IOException x){
					if(printErrors){
						System.err.println(args[i] + ": " + x.getMessage());
					}
					exitCond = 1;
				}
			}
		}
		if (!done){
			try {
				if (action == ACTION_GUESS){
					System.out.println(labels.getString("cantguess"));
					exitCond = 1;
				} else if (action == ACTION_ENCODE){
					encode(
						new BufferedInputStream(System.in),
						new BufferedOutputStream(System.out),
						lineBreaks
					);
				} else {
					decode(
						new BufferedInputStream(System.in),
						new BufferedOutputStream(System.out),
						!forceDecode
					);
				}
			} catch (Base64DecodingException x){
				if(printErrors){
					System.err.println(x.getMessage() + " " + labels.getString("unexpectedcharforce"));
				}
				exitCond = 1;
			} catch (IOException x){
				if(printErrors){
					System.err.println(x.getMessage());
				}
				exitCond = 1;
			}
		}
		System.exit(exitCond);
	}

	/**
	 * Encode a String in Base64.
	 * The String is converted to and from bytes according to the platform's
	 * default character encoding.
	 * No line breaks or other white space are inserted into the encoded data.
	 *
	 * @param string The data to encode.
	 * @return An encoded String.
	 */
	public static String encode(String string){
		return new String(encode(string.getBytes()));
	}

	/**
	 * Encode a String in Base64.
	 * No line breaks or other white space are inserted into the encoded data.
	 *
	 * @param string The data to encode.
	 * @param enc Character encoding to use when converting to and from bytes.
	 * @throws UnsupportedEncodingException if the character encoding specified is not supported.
	 * @return An encoded String.
	 */
	public static String encode(String string, String enc) throws UnsupportedEncodingException {
		return new String(encode(string.getBytes(enc)), enc);
	}

	/**
	 * Encode bytes in Base64.
	 * No line breaks or other white space are inserted into the encoded data.
	 *
	 * @param bytes The data to encode.
	 * @return Encoded bytes.
	 */
	public static byte[] encode(byte[] bytes){
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		// calculate the length of the resulting output.
		// in general it will be 4/3 the size of the input
		// but the input length must be divisible by three.
		// If it isn't the next largest size that is divisible
		// by three is used.
		int mod;
		int length = bytes.length;
		if ((mod = length % 3) != 0){
			length += 3 - mod;
		}
		length = length * 4 / 3;
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		try {
			encode(in, out, false);
		} catch (IOException x){
			// This can't happen.
			// The input and output streams were constructed
			// on memory structures that don't actually use IO.
		}
		return out.toByteArray();
	}

	/**
	 * Encode this file in Base64.
	 * Line breaks will be inserted every 76 characters.
	 *
	 * @param fIn File to be encoded (will be overwritten).
	 * @param lineBreaks  Whether to insert line breaks every 76 characters in the output.
	 * @throws IOException if an input or output error occurs.
	 */
	public static void encode(File fIn) throws IOException {
		encode(fIn, fIn, true);
	}

	/**
	 * Encode this file in Base64.
	 *
	 * @param fIn File to be encoded (will be overwritten).
	 * @param fOut File to which the results should be written (may be the same as fIn).
	 * @param lineBreaks  Whether to insert line breaks every 76 characters in the output.
	 * @throws IOException if an input or output error occurs.
	 */
	public static void encode(File fIn, boolean lineBreaks) throws IOException {
		encode(fIn, fIn, lineBreaks);
	}

	/**
	 * Encode this file in Base64.
	 * Line breaks will be inserted every 76 characters.
	 *
	 * @param fIn File to be encoded.
	 * @param fOut File to which the results should be written (may be the same as fIn).
	 * @param lineBreaks  Whether to insert line breaks every 76 characters in the output.
	 * @throws IOException if an input or output error occurs.
	 */
	public static void encode(File fIn, File fOut) throws IOException {
		encode(fIn, fOut, true);
	}

	/**
	 * Encode this file in Base64.
	 *
	 * @param fIn File to be encoded.
	 * @param fOut File to which the results should be written (may be the same as fIn).
	 * @param lineBreaks  Whether to insert line breaks every 76 characters in the output.
	 * @throws IOException if an input or output error occurs.
	 */
	public static void encode(File fIn, File fOut, boolean lineBreaks) throws IOException {
		File temp = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(fIn));
			temp = File.createTempFile("Base64", null, null);
			out = new BufferedOutputStream(new FileOutputStream(temp));
			encode(in, out, lineBreaks);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
			FileHelper.move(temp, fOut, true);
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException ignore){
				}
				in = null;
			}
			if (out != null){
				try {
					out.flush();
					out.close();
				} catch (IOException ignore){
				}
				out = null;
			}
		}
	}

	/**
	 * Encode data from the InputStream to the OutputStream in Base64.
	 * Line breaks are inserted every 76 characters in the output.
	 *
	 * @param in Stream from which to read data that needs to be encoded.
	 * @param out Stream to which to write encoded data.
	 * @throws IOException if there is a problem reading or writing.
	 */
	public static void encode(InputStream in, OutputStream out) throws IOException {
		encode(in, out, true);
	}

	/**
	 * Encode data from the InputStream to the OutputStream in Base64.
	 *
	 * @param in Stream from which to read data that needs to be encoded.
	 * @param out Stream to which to write encoded data.
	 * @param lineBreaks Whether to insert line breaks every 76 characters in the output.
	 * @throws IOException if there is a problem reading or writing.
	 */
	public static void encode(InputStream in, OutputStream out, boolean lineBreaks) throws IOException {
		// Base64 encoding converts three bytes of input to
		// four bytes of output
		int[] inBuffer = new int[3];
		int lineCount = 0;

		boolean done = false;
		while (!done && (inBuffer[0] = in.read()) != END_OF_INPUT){
			// Fill the buffer
			inBuffer[1] = in.read();
			inBuffer[2] = in.read();

			// Calculate the out Buffer
			// The first byte of our in buffer will always be valid
			// but we must check to make sure the other two bytes
			// are not END_OF_INPUT before using them.
			// The basic idea is that the three bytes get split into
			// four bytes along these lines:
			//      [AAAAAABB] [BBBBCCCC] [CCDDDDDD]
			// [xxAAAAAA] [xxBBBBBB] [xxCCCCCC] [xxDDDDDD]
			// bytes are considered to be zero when absent.
			// the four bytes are then mapped to common ASCII symbols

			// A's: first six bits of first byte
			out.write(base64Chars[ inBuffer[0] >> 2 ]);
			if (inBuffer[1] != END_OF_INPUT){
				// B's: last two bits of first byte, first four bits of second byte
				out.write(base64Chars [(( inBuffer[0] << 4 ) & 0x30) | (inBuffer[1] >> 4) ]);
				if (inBuffer[2] != END_OF_INPUT){
					// C's: last four bits of second byte, first two bits of third byte
					out.write(base64Chars [((inBuffer[1] << 2) & 0x3c) | (inBuffer[2] >> 6) ]);
					// D's: last six bits of third byte
					out.write(base64Chars [inBuffer[2] & 0x3F]);
				} else {
					// C's: last four bits of second byte
					out.write(base64Chars [((inBuffer[1] << 2) & 0x3c)]);
					// an equals sign for a character that is not a Base64 character
					out.write('=');
					done = true;
				}
			} else {
				// B's: last two bits of first byte
				out.write(base64Chars [(( inBuffer[0] << 4 ) & 0x30)]);
				// an equal signs for characters that is not a Base64 characters
				out.write('=');
				out.write('=');
				done = true;
			}
			lineCount += 4;
			if (lineBreaks && lineCount >= 76){
				out.write('\n');
				lineCount = 0;
			}
		}
		if (lineBreaks && lineCount >= 1){
			out.write('\n');
			lineCount = 0;
		}
		out.flush();
	}

	/**
	 * Decode a Base64 encoded String.
	 * Characters that are not part of the Base64 alphabet are ignored
	 * in the input.
	 * The String is converted to and from bytes according to the platform's
	 * default character encoding.
	 *
	 * @param string The data to decode.
	 * @return A decoded String.
	 */
	public static String decode(String string){
		return new String(decode(string.getBytes()));
	}

	/**
	 * Decode a Base64 encoded String.
	 * Characters that are not part of the Base64 alphabet are ignored
	 * in the input.
	 *
	 * @param string The data to decode.
	 * @param enc Character encoding to use when converting to and from bytes.
	 * @throws UnsupportedEncodingException if the character encoding specified is not supported.
	 * @return A decoded String.
	 */
	public static String decode(String string, String enc) throws UnsupportedEncodingException {
		return new String(decode(string.getBytes(enc)), enc);
	}

	/**
	 * Decode Base64 encoded bytes.
	 * Characters that are not part of the Base64 alphabet are ignored
	 * in the input.
	 *
	 * @param bytes The data to decode.
	 * @return Decoded bytes.
	 */
	public static byte[] decode(byte[] bytes){
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		// calculate the length of the resulting output.
		// in general it will be at most 3/4 the size of the input
		// but the input length must be divisible by four.
		// If it isn't the next largest size that is divisible
		// by four is used.
		int mod;
		int length = bytes.length;
		if ((mod = length % 4) != 0){
			length += 4 - mod;
		}
		length = length * 3 / 4;
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		try {
			decode(in, out, false);
		} catch (IOException x){
			// This can't happen.
			// The input and output streams were constructed
			// on memory structures that don't actually use IO.
		}
		return out.toByteArray();
	}

	/**
	 * Decode Base64 encoded data from one file to the other.
	 * Characters in the Base64 alphabet, white space and equals sign are
	 * expected to be in urlencoded data.  The presence of other characters
	 * could be a sign that the data is corrupted.
	 *
	 * @param fIn File to be decoded (will be overwritten).
	 * @throws IOException if an IO error occurs.
	 * @throws Base64DecodingException if unexpected data is encountered.
	 */
	public static void decode(File fIn) throws IOException {
		decode(fIn, fIn, true);
	}

	/**
	 * Decode Base64 encoded data from one file to the other.
	 * Characters in the Base64 alphabet, white space and equals sign are
	 * expected to be in urlencoded data.  The presence of other characters
	 * could be a sign that the data is corrupted.
	 *
	 * @param fIn File to be decoded (will be overwritten).
	 * @param throwExceptions Whether to throw exceptions when unexpected data is encountered.
	 * @throws IOException if an IO error occurs.
	 * @throws Base64DecodingException if unexpected data is encountered when throwExceptions is specified.
	 */
	public static void decode(File fIn, boolean throwExceptions) throws IOException {
		decode(fIn, fIn, throwExceptions);
	}

	/**
	 * Decode Base64 encoded data from one file to the other.
	 * Characters in the Base64 alphabet, white space and equals sign are
	 * expected to be in urlencoded data.  The presence of other characters
	 * could be a sign that the data is corrupted.
	 *
	 * @param fIn File to be decoded.
	 * @param fOut File to which the results should be written (may be the same as fIn).
	 * @throws IOException if an IO error occurs.
	 * @throws Base64DecodingException if unexpected data is encountered.
	 */
	public static void decode(File fIn, File fOut) throws IOException {
		decode(fIn, fOut, true);
	}

	/**
	 * Decode Base64 encoded data from one file to the other.
	 * Characters in the Base64 alphabet, white space and equals sign are
	 * expected to be in urlencoded data.  The presence of other characters
	 * could be a sign that the data is corrupted.
	 *
	 * @param fIn File to be decoded.
	 * @param fOut File to which the results should be written (may be the same as fIn).
	 * @param throwExceptions Whether to throw exceptions when unexpected data is encountered.
	 * @throws IOException if an IO error occurs.
	 * @throws Base64DecodingException if unexpected data is encountered when throwExceptions is specified.
	 */
	public static void decode(File fIn, File fOut, boolean throwExceptions) throws IOException {
		File temp = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(fIn));
			temp = File.createTempFile("Base64", null, null);
			out = new BufferedOutputStream(new FileOutputStream(temp));
			decode(in, out, throwExceptions);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
			FileHelper.move(temp, fOut, true);
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException ignore){
				}
				in = null;
			}
			if (out != null){
				try {
					out.flush();
					out.close();
				} catch (IOException ignore){
				}
				out = null;
			}
		}
	}

	/**
	 * Reads the next (decoded) Base64 character from the input stream.
	 * Non Base64 characters are skipped.
	 *
	 * @param in Stream from which bytes are read.
	 * @param throwExceptions Throw an exception if an unexpected character is encountered.
	 * @return the next Base64 character from the stream or -1 if there are no more Base64 characters on the stream.
	 * @throws IOException if an IO Error occurs.
	 * @throws Base64DecodingException if unexpected data is encountered when throwExceptions is specified.
	 */
	private static final int readBase64(InputStream in, boolean throwExceptions) throws IOException {
		int read;
		int numPadding = 0;
		do {
			read = in.read();
			if (read == END_OF_INPUT) return END_OF_INPUT;
			read = reverseBase64Chars[(byte)read];
			if (throwExceptions && (read == NON_BASE_64 || (numPadding > 0 && read > NON_BASE_64))){
				throw new Base64DecodingException (
					MessageFormat.format(
						labels.getString("unexpectedchar"),
						new String[] {
							"'" + (char)read + "' (0x" + Integer.toHexString(read) + ")"
						}
					),
					(char)read
				);
			}
			if (read == NON_BASE_64_PADDING){
				numPadding++;
			}
		} while (read <= NON_BASE_64);
		return read;
	}

	/**
	 * Decode Base64 encoded data from the InputStream to the OutputStream.
	 * Characters in the Base64 alphabet, white space and equals sign are
	 * expected to be in urlencoded data.  The presence of other characters
	 * could be a sign that the data is corrupted.
	 *
	 * @param in Stream from which to read data that needs to be decoded.
	 * @param out Stream to which to write decoded data.
	 * @throws IOException if an IO error occurs.
	 * @throws Base64DecodingException if unexpected data is encountered.
	 */
	public static void decode(InputStream in, OutputStream out) throws IOException {
		decode(in, out, true);
	}

	/**
	 * Decode Base64 encoded data from the InputStream to the OutputStream.
	 * Characters in the Base64 alphabet, white space and equals sign are
	 * expected to be in urlencoded data.  The presence of other characters
	 * could be a sign that the data is corrupted.
	 *
	 * @param in Stream from which to read data that needs to be decoded.
	 * @param out Stream to which to write decoded data.
	 * @param throwExceptions Whether to throw exceptions when unexpected data is encountered.
	 * @throws IOException if an IO error occurs.
	 * @throws Base64DecodingException if unexpected data is encountered when throwExceptions is specified.
	 */
	public static void decode(InputStream in, OutputStream out, boolean throwExceptions) throws IOException {
		// Base64 decoding converts four bytes of input to three bytes of output
		int[] inBuffer = new int[4];

		// read bytes unmapping them from their ASCII encoding in the process
		// we must read at least two bytes to be able to output anything
		boolean done = false;
		while (!done && (inBuffer[0] = readBase64(in, throwExceptions)) != END_OF_INPUT
			&& (inBuffer[1] = readBase64(in, throwExceptions)) != END_OF_INPUT){
			// Fill the buffer
			inBuffer[2] = readBase64(in, throwExceptions);
			inBuffer[3] = readBase64(in, throwExceptions);

			// Calculate the output
			// The first two bytes of our in buffer will always be valid
			// but we must check to make sure the other two bytes
			// are not END_OF_INPUT before using them.
			// The basic idea is that the four bytes will get reconstituted
			// into three bytes along these lines:
			// [xxAAAAAA] [xxBBBBBB] [xxCCCCCC] [xxDDDDDD]
			//      [AAAAAABB] [BBBBCCCC] [CCDDDDDD]
			// bytes are considered to be zero when absent.

			// six A and two B
			out.write(inBuffer[0] << 2 | inBuffer[1] >> 4);
			if (inBuffer[2] != END_OF_INPUT){
				// four B and four C
				out.write(inBuffer[1] << 4 | inBuffer[2] >> 2);
				if (inBuffer[3] != END_OF_INPUT){
					// two C and six D
					out.write(inBuffer[2] << 6 | inBuffer[3]);
				} else {
					done = true;
				}
			} else {
				done = true;
			}
		}
		out.flush();
	}

		/**
	 * Determines if the byte array is in base64 format.
	 * <p>
	 * Data will be considered to be in base64 format if it contains
	 * only base64 characters and whitespace with equals sign padding
	 * on the end so that the number of base64 characters is divisible
	 * by four.
	 * <p>
	 * It is possible for data to be in base64 format but for it to not
	 * meet these stringent requirements.  It is also possible for data
	 * to meet these requirements even though decoding it would not make
	 * any sense.  This method should be used as a guide but it is not
	 * authoritative because of the possibility of these false positives
	 * and false negatives.
	 * <p>
	 * Additionally, extra data such as headers or footers may throw
	 * this method off the scent and cause it to return false.
	 *
	 * @param b data that could be in base64 format.
	 */
	public static boolean isBase64(byte[] bytes){
		try {
			return isBase64(new ByteArrayInputStream(bytes));
		} catch (IOException x){
			// This can't happen.
			// The input and output streams were constructed
			// on memory structures that don't actually use IO.
			return false;
		}
	}

		/**
	 * Determines if the String is in base64 format.
	 * The String is converted to and from bytes according to the platform's
	 * default character encoding.
	 * <p>
	 * Data will be considered to be in base64 format if it contains
	 * only base64 characters and whitespace with equals sign padding
	 * on the end so that the number of base64 characters is divisible
	 * by four.
	 * <p>
	 * It is possible for data to be in base64 format but for it to not
	 * meet these stringent requirements.  It is also possible for data
	 * to meet these requirements even though decoding it would not make
	 * any sense.  This method should be used as a guide but it is not
	 * authoritative because of the possibility of these false positives
	 * and false negatives.
	 * <p>
	 * Additionally, extra data such as headers or footers may throw
	 * this method off the scent and cause it to return false.
	 *
	 * @param s String that may be in base64 format.
	 * @return Best guess as to whether the data is in base64 format.
	 */
	public static boolean isBase64(String string){
		return isBase64(string.getBytes());
	}

	/**
	 * Determines if the String is in base64 format.
	 * <p>
	 * Data will be considered to be in base64 format if it contains
	 * only base64 characters and whitespace with equals sign padding
	 * on the end so that the number of base64 characters is divisible
	 * by four.
	 * <p>
	 * It is possible for data to be in base64 format but for it to not
	 * meet these stringent requirements.  It is also possible for data
	 * to meet these requirements even though decoding it would not make
	 * any sense.  This method should be used as a guide but it is not
	 * authoritative because of the possibility of these false positives
	 * and false negatives.
	 * <p>
	 * Additionally, extra data such as headers or footers may throw
	 * this method off the scent and cause it to return false.
	 *
	 * @param s String that may be in base64 format.
	 * @param enc Character encoding to use when converting to bytes.
	 * @return Best guess as to whether the data is in base64 format.
	 * @throws UnsupportedEncodingException if the character encoding specified is not supported.
	 */
	public static boolean isBase64(String string, String enc) throws UnsupportedEncodingException {
		return isBase64(string.getBytes(enc));
	}


	/**
	 * Determines if the File is in base64 format.
	 * <p>
	 * Data will be considered to be in base64 format if it contains
	 * only base64 characters and whitespace with equals sign padding
	 * on the end so that the number of base64 characters is divisible
	 * by four.
	 * <p>
	 * It is possible for data to be in base64 format but for it to not
	 * meet these stringent requirements.  It is also possible for data
	 * to meet these requirements even though decoding it would not make
	 * any sense.  This method should be used as a guide but it is not
	 * authoritative because of the possibility of these false positives
	 * and false negatives.
	 * <p>
	 * Additionally, extra data such as headers or footers may throw
	 * this method off the scent and cause it to return false.
	 *
	 * @param fIn File that may be in base64 format.
	 * @param enc Character encoding to use when converting to bytes.
	 * @return Best guess as to whether the data is in base64 format.
	 * @throws IOException if an IO error occurs.
	 */
	public static boolean isBase64(File fIn) throws IOException {
		return isBase64(new BufferedInputStream(new FileInputStream(fIn)));
	}

	/**
	 * Reads data from the stream and determines if it is
	 * in base64 format.
	 * <p>
	 * Data will be considered to be in base64 format if it contains
	 * only base64 characters and whitespace with equals sign padding
	 * on the end so that the number of base64 characters is divisible
	 * by four.
	 * <p>
	 * It is possible for data to be in base64 format but for it to not
	 * meet these stringent requirements.  It is also possible for data
	 * to meet these requirements even though decoding it would not make
	 * any sense.  This method should be used as a guide but it is not
	 * authoritative because of the possibility of these false positives
	 * and false negatives.
	 * <p>
	 * Additionally, extra data such as headers or footers may throw
	 * this method off the scent and cause it to return false.
	 *
	 * @param in Stream from which to read data to be tested.
	 * @return Best guess as to whether the data is in base64 format.
	 * @throws IOException if an IO error occurs.
	 */
	public static boolean isBase64(InputStream in) throws IOException {
		long numBase64Chars = 0;
		int numPadding = 0;
		int read;

		while ((read = in.read()) != -1){
			read = reverseBase64Chars[read];
			if (read == NON_BASE_64){
				return false;
			} else if (read == NON_BASE_64_WHITESPACE){
			} else if (read == NON_BASE_64_PADDING){
				numPadding++;
				numBase64Chars++;
			} else if (numPadding > 0){
				return false;
			} else {
				numBase64Chars++;
			}
		}
		if (numBase64Chars == 0) return false;
		if (numBase64Chars % 4 != 0) return false;
		return true;
	}
}
