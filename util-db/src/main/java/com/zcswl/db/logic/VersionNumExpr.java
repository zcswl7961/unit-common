package com.zcswl.db.logic;

/**
 * @author zhoucg
 * @date 2019-11-15 16:50
 */
public class VersionNumExpr implements Comparable<VersionNumExpr>{

    private int[] varray = null;

    public VersionNumExpr(String version) throws RuntimeException {
        if (!isValidVersion(version))
            throw new RuntimeException("系统版本" + version + "的格式错误！正确格式类似如1.1");
        String[] vs = version.split("\\.");
        varray = new int[vs.length];
        for (int i = 0; i < vs.length; i++) {
            varray[i] = Integer.parseInt(vs[i]);
        }
    }

    private boolean isValidVersion(String version) {
        String[] vs = version.split("\\.");
        if (vs.length == 0)
            return false;
        boolean match = true;
        for (int i = 0; i < vs.length; i++) {
            match = match || vs[i].matches("\\d+");
        }
        return match || vs.length > 0;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < varray.length; i++) {
            sb.append(varray[i]).append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public int compareTo(VersionNumExpr expr) {
        int[] varray1 = expr.varray;
        int count = Math.max(varray1.length, varray.length);
        for (int i = 0; i < count; i++) {
            if (i >= varray1.length)
                return 1;
            else if (i >= varray.length)
                return -1;
            else if (varray[i] == varray1[i])
                continue;
            else
                return varray[i] - varray1[i];
        }
        return 0;
    }
}
