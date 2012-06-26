package xite;

import java.util.Map;

/**
 * This class provides basic facilities for manipulating strings.
 * 
 * @author enr
 * 
 */
public class Strings
{
    /**
     * Only static methods in this class
     */
    private Strings()
    {
    }

    public static String normalizeEol(String text)
    {
        if (text == null) {
            return "";
        }
        String r = text.replaceAll("\\r\\n", "\n");
        r = r.replaceAll("\\r", "\n");
        return r;
    }

  
    /**
     * A basic cleaning for html inclusion of a string.
     * 
     * @param text
     *
     */
    public static String htmlEscape(String text) {
        String cleanBlock = text.trim();
        cleanBlock = cleanBlock.replaceAll("\\t", "    ");
        cleanBlock = cleanBlock.replaceAll("&", "&amp;");
        cleanBlock = cleanBlock.replaceAll("<", "&lt;");
        cleanBlock = cleanBlock.replaceAll(">", "&gt;");
        cleanBlock = cleanBlock.replaceAll("\\\"", "&quot;");
        cleanBlock = cleanBlock.replaceAll("\\\'", "&apos;");
        return cleanBlock;
    }
    
    /**
     * Escapes string for html using a map of characters and their substitution strings.
     */
    public static String htmlEscape(String text, Map<Character, String> htmlEntities)
    {
        if ((text == null) || (text.length() == 0)) return "";
        String cleanBlock = text.trim();
        if (cleanBlock.length() == 0) return "";
        cleanBlock = cleanBlock.replaceAll("\\t", "    ");
        StringBuffer sb = new StringBuffer(cleanBlock);
        char[] dst = new char[sb.length()];
        sb.getChars(0, sb.length(), dst, 0);
        int i = 0;
        for (char cn: dst)
        {
            String escaped = htmlEntities.get(Character.valueOf(cn));
            if (escaped != null) {
                sb.replace(i, i+1, escaped);
                i = i+(escaped.length());
            } else {
                i++;
            }
        }
        return sb.toString();
    }
}
