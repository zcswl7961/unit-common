package com.zcswl.db.util;

/**
 * @author zhoucg
 * @date 2019-11-15 17:40
 */
public class BeanUtil {

    private BeanUtil() {
    }

    /**
     * 字符串转换成实体
     *
     * @param s
     * @param t
     * @return
     * @throws BeanUtilException
     */
    public static Object convert(String s, Class t) throws BeanUtilException {
        try {
            if (s == null) {
                if (t.equals(java.lang.Boolean.class) || t.equals(Boolean.TYPE)) {
                    s = "false";
                } else {
                    return null;
                }
            }
            if (t.equals(java.lang.Boolean.class) || t.equals(Boolean.TYPE)) {
                if (s.equalsIgnoreCase("on") || s.equalsIgnoreCase("true")) {
                    s = "true";
                } else {
                    s = "false";
                }
                return new Boolean(s);
            }
            if (t.equals(java.lang.Byte.class) || t.equals(Byte.TYPE)) {
                return new Byte(s);
            }
            if (t.equals(java.lang.Character.class) || t.equals(Character.TYPE)) {
                return s.length() <= 0 ? null : new Character(s.charAt(0));
            }
            if (t.equals(java.lang.Short.class) || t.equals(Short.TYPE)) {
                return new Short(s);
            }
            if (t.equals(java.lang.Integer.class) || t.equals(Integer.TYPE)) {
                return new Integer(s);
            }
            if (t.equals(java.lang.Float.class) || t.equals(Float.TYPE)) {
                return new Float(s);
            }
            if (t.equals(java.lang.Long.class) || t.equals(Long.TYPE)) {
                return new Long(s);
            }
            if (t.equals(java.lang.Double.class) || t.equals(Double.TYPE)) {
                return new Double(s);
            }
            if (t.equals(java.lang.String.class)) {
                return s;
            }
        } catch (Exception ex) {
            throw new BeanUtilException(ex.getMessage());
        }
        return s;
    }
}
