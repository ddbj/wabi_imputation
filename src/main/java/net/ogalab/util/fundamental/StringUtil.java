package net.ogalab.util.fundamental;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ogalab.util.container.ListUtil;
import net.ogalab.util.exception.RuntimeExceptionUtil;
import org.apache.commons.codec.net.URLCodec;
import org.slf4j.LoggerFactory;

public class StringUtil {

    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(StringUtil.class);
    
    public static void main(String[] args) {
        String testData = "abc\\n\ndef\tgh\\あ";

        System.out.println(testData);
        System.out.println("");

        String one = StringUtil.asOneLine(testData);
        System.out.println(one);
        System.out.println("");

        System.out.println(StringUtil.asMultiLines(one));

    }
    
    
    public static String reverse(String str) {
        return new StringBuffer(str).reverse().toString();
    }

    public static String getMD5(String str) {
        MessageDigestAdapter md5 = null;
        try {
            md5 = new MessageDigestAdapter("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5.digest(str);
    }

    public static String asMultiLines(String str) {
        boolean flg = false;
        StringBuilder sb = new StringBuilder();

        for (int index = 0; index < str.length(); index++) {
            char c = str.charAt(index);

            switch (c) {

                case '\\':
                    if (flg == true) {
                        sb.append("\\");
                        flg = false;
                    } else {
                        flg = true;
                    }
                    break;
                case 'n':
                    if (flg == true) {
                        sb.append("\n");
                        flg = false;
                    } else {
                        sb.append("n");
                    }
                    break;
                case 't':
                    if (flg == true) {
                        sb.append("\t");
                        flg = false;
                    } else {
                        sb.append("t");
                    }
                    break;
                default:
                    if (flg == true) {
                        sb.append("\\" + c);
                        flg = false;
                    } else {
                        sb.append(c);
                    }
                    break;
            }
        }

        return sb.toString();

    }

    public static String asOneLine(String str) {

        StringBuilder sb = new StringBuilder();
        try {

            if (str == null) {
                return "";
            }

            //logger.debug("asOneLine : " + str);
            boolean escaped = false;

            for (int index = 0; index < str.length(); index++) {
                char c = str.charAt(index);

                switch (c) {

                    case '\t':
                        sb.append("\\" + "t");
                        break;
                    case '\r':
                        sb.append(" ");
                        break;
                    case '\n':
                        sb.append("\\" + "n");
                        break;
                    case 'n':
                        if (escaped) {
                            sb.append("\\\\" + "n");
                        } else {
                            sb.append("n");
                        }
                        escaped = false;
                        break;
                    case 't':
                        if (escaped) {
                            sb.append("\\\\" + "t");
                        } else {
                            sb.append("t");
                        }
                        escaped = false;
                        break;
                    case '\\':
                        if (escaped) {
                            sb.append("\\\\");
                            escaped = false;
                        } else {
                            escaped = true;
                        }
                        break;
                    default:
                        if (escaped) {
                            escaped = false;
                            sb.append("\\" + c);
                        } else {
                            sb.append(c);
                        }
                        break;
                }
            }
        } catch (Exception e) {
            logger.debug(str);
            e.printStackTrace();

        }

        return sb.toString();
    }

    public static boolean isInteger(String s) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static String escapeUtfSuppl(String s) {
        CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
        utf8Decoder.onMalformedInput(CodingErrorAction.REPLACE);
        utf8Decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        ByteBuffer bytes = ByteBuffer.wrap(s.getBytes());

        CharBuffer parsed = null;
        try {
            parsed = utf8Decoder.decode(bytes);
        } catch (CharacterCodingException e) {
            // TODO Auto-generated catch block
            System.err.println("error");
            e.printStackTrace();
        }

        return parsed.toString();
    }

    /**
     * Encodes a string into its URL safe form
     */
    public static String toURL(String str) {

        String result = null;
        try {
            URLCodec codec = new URLCodec();
            result = codec.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            RuntimeExceptionUtil.invoke(e);
        }
        return result;
    }

    public static String trim(String str) {
        return str.trim();
    }

    public static String removeNewLines(String origStr) {
        ArrayList<String> lines = StringUtil.splitByNewLine(origStr);
        String ret = ListUtil.join(" ", lines);
        return ret;
    }

    public static String removeTabs(String str) {
        StringBuffer buf = new StringBuffer();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == '\t') {
                buf.append("        ");
            } else {
                buf.append(str.charAt(i));
            }
        }
        return buf.toString();
    }

    public static String removeChar(String str, char ch) {
        StringBuffer buf = new StringBuffer();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) != ch) {
                buf.append(str.charAt(i));
            }
        }
        return buf.toString();
    }

    public static String substring(String str, int start, int length) {
        String result = null;
        if (str.length() > start + length) {
            result = str.substring(start, length);
        } else {
            result = str;
        }
        return result;
    }

    public static ArrayList<String> splitByChar(String str, char ch) {

        ArrayList<String> ret = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                ret.add(sb.toString());
                sb.delete(0, sb.length());

				// When a delimiting character is at the last of a string,
                // the last element of the returned list should be an empty string.
                if (i == str.length() - 1) {
                    sb.append("");
                }
            } else {
                sb.append(str.charAt(i));
            }

        }

        ret.add(sb.toString());

        return ret;
    }

    public static ArrayList<String> splitBySpace(String str) {
        return splitByChar(str, ' ');
    }

    public static ArrayList<String> splitByTab(String str) {
        return splitByChar(str, '\t');
    }

    public static ArrayList<String> splitByComma(String str) {
        return splitByChar(str, ',');
    }

    public static ArrayList<String> splitByNewLine(String str) {
        return splitByChar(str, '\n');
    }

    public static ArrayList<String> splitByRegex(String str, Pattern p) {

        Matcher m = p.matcher(str);

        int start = 0;
        ArrayList<String> ret = new ArrayList<String>();

        while (m.find()) {
            ret.add(str.substring(start, m.start()));
            start = m.end();
        }
        ret.add(str.substring(start, str.length()));

        return ret;
    }

}
