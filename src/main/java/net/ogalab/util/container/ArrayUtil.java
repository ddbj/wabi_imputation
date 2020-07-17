package net.ogalab.util.container;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import net.ogalab.functor.Functor;
import net.ogalab.util.fundamental.StringUtil;
import net.ogalab.util.fundamental.Type;
import net.ogalab.util.os.FileIO;
import net.ogalab.util.os.FileUtil;

public class ArrayUtil {
    
    
    public static String[] apply(String[] array, Functor<String> f) {
        ArrayList<String> ret = new ArrayList<String>();
        for (String t : array) {
            ret.add((String) f.func(t));
        }
        return ListUtil.toStringArray(ret);
    }

    public static int[] to_int_array(double[] d) {
        int[] result = new int[d.length];
        for (int i = 0; i < d.length; i++) {
            result[i] = Type.to_int(d[i]);
        }
        return result;
    }

    public static String[] cdr(String[] a) {
        String[] ret = new String[a.length - 1];
        for (int i = 1; i < a.length; i++) {
            ret[i - 1] = a[i];
        }
        return ret;
    }

    public static ArrayList<Double> toDoubleList(double[] a) {
        ArrayList<Double> ret = new ArrayList<Double>();
        for (int i = 0; i < a.length; i++) {
            ret.add(a[i]);
        }
        return ret;
    }

    public static ArrayList<Integer> toIntegerList(double[] a) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < a.length; i++) {
            ret.add(Type.toInteger(a[i]));
        }
        return ret;
    }

    public static ArrayList<String> toStringList(double[] a) {
        ArrayList<String> ret = new ArrayList<String>();
        for (int i = 0; i < a.length; i++) {
            ret.add(Type.toString(a[i]));
        }
        return ret;
    }

    public static ArrayList<String> toStringList(String[] s) {
        ArrayList<String> ret = new ArrayList<String>();
        for (int i = 0; i < s.length; i++) {
            ret.add(s[i]);
        }
        return ret;
    }

    public static <T> ArrayList<T> toList(T[] a) {
        ArrayList<T> ret = new ArrayList<T>();
        for (int i = 0; i < a.length; i++) {
            ret.add(a[i]);
        }
        return ret;
    }

    public static <T> String[] toStringArray(T[] a) {
        String[] result = new String[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = Type.toString(a[i]);
        }
        return result;
    }

    public static double[] copy(double[] a) {
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            ret[i] = a[i];
        }
        return ret;
    }

    public static int[] copy(int[] a) {
        int[] ret = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            ret[i] = a[i];
        }
        return ret;
    }

    public static String[] copy(String[] a) {
        String[] ret = new String[a.length];
        for (int i = 0; i < a.length; i++) {
            ret[i] = a[i];
        }
        return ret;
    }

    public static double[] subseq(double[] a, int start, int length) {
        double[] ret = new double[length];
        for (int i = 0; i < length; i++) {
            ret[i] = a[start + i];
        }
        return ret;
    }

    public static double[] multiply(double[] data, double m) {
        double[] ret = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            ret[i] = data[i] * m;
        }
        return ret;
    }

    public static double[] reverse(double[] a) {
        int len = a.length;
        double[] ret = new double[len];
        for (int i = 0; i < len; i++) {
            ret[i] = a[len - 1 - i];
        }
        return ret;
    }

    public static double[] sort(double[] a) {
        double[] ret = copy(a);
        java.util.Arrays.sort(ret);
        return ret;
    }

    public static String[] sort(String[] a) {
        String[] ret = copy(a);
        java.util.Arrays.sort(ret);
        return ret;
    }

    public static double[] sequence(double val, int num) {
        double[] ret = new double[num];
        for (int i = 0; i < num; i++) {
            ret[i] = val;
        }

        return ret;
    }

    public static int[] repeat(int val, int n) {
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            ret[i] = val;
        }

        return ret;
    }

    public static double[] repeat(double val, int n) {
        double[] ret = new double[n];
        for (int i = 0; i < n; i++) {
            ret[i] = val;
        }

        return ret;
    }

    public static void print(double[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void print(String[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void print(File[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void save(int[] a, String path) throws IOException {
        System.err.println("ArrayFunc.save: " + path);
        ArrayList<String> base = FileUtil.parsePathString(path);
        FileUtil.mkdirs(base.get(1));

        PrintWriter fout = FileIO.getPrintWriter(path);
        for (int i = 0; i < a.length; i++) {
            fout.println(a[i]);
        }

        fout.close();
    }

    public static void save(double[] a, String path) throws IOException {
        PrintWriter fout = FileIO.getPrintWriter(path);
        for (int i = 0; i < a.length; i++) {
            fout.println(a[i]);
        }

        fout.close();
    }

    public static void save(String[] a, String path) throws IOException {
        PrintWriter fout = FileIO.getPrintWriter(path);
        for (int i = 0; i < a.length; i++) {
            fout.println(a[i]);
        }

        fout.close();
    }

    public static double[] read(double[] a, String path) throws IOException {
        BufferedReader fin = FileIO.getBufferedReader(path);
        ArrayList<Double> data = new ArrayList<Double>();
        String line = null;
        while ((line = fin.readLine()) != null) {
            line = StringUtil.trim(line);
            data.add(Type.toDouble(line));
        }
        fin.close();
        a = ListUtil.to_double_array(data);
        return a;
    }

    public static double[] read(double[] a, String path, int lineNum) throws IOException {
        BufferedReader fin = FileIO.getBufferedReader(path);
        ArrayList<Double> data = new ArrayList<Double>();
        String line = null;

        int counter = 0;
        while ((line = fin.readLine()) != null) {
            line = StringUtil.trim(line);
            data.add(Type.toDouble(line));
            if (++counter >= lineNum) {
                break;
            }
        }
        fin.close();
        a = ListUtil.to_double_array(data);
        return a;

    }

    public static double[] read(double[] a, BufferedReader fin) throws IOException {
        ArrayList<Double> data = new ArrayList<Double>();
        String line = null;
        while ((line = fin.readLine()) != null) {
            line = StringUtil.trim(line);
            data.add(Type.toDouble(line));
        }
        a = ListUtil.to_double_array(data);
        return a;
    }

    public static double[] read(double[] a, BufferedReader fin, int lineNum) throws IOException {

        ArrayList<Double> data = new ArrayList<Double>();
        String line = null;

        int counter = 0;
        while ((line = fin.readLine()) != null) {
            line = StringUtil.trim(line);
            data.add(Type.toDouble(line));
            if (++counter >= lineNum) {
                break;
            }
        }

        a = ListUtil.to_double_array(data);
        return a;

    }

    public static String join(String delim, String[] list) {
        if (list == null || list.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(list[0]);

        for (int i = 1; i < list.length; i++) {
            sb.append(delim);
            sb.append(Type.toString(list[i]));
        }

        return sb.toString();
    }

    public static String join(String delim, int[] list) {
        if (list == null || list.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(list[0]);

        for (int i = 1; i < list.length; i++) {
            sb.append(delim);
            sb.append(list[i]);
        }

        return sb.toString();
    }

    public static String join(String delim, double[] list) {
        if (list == null || list.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(list[0]);

        for (int i = 1; i < list.length; i++) {
            sb.append(delim);
            sb.append(list[i]);
        }

        return sb.toString();
    }

	//--------------------------------------------------------------------
    public static float[] to_float_array(double a[]) {
        float result[] = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (float) a[i];
        }
        return result;
    }

}
