package xite;

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
}
