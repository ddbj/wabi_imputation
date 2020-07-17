package net.ogalab.util.datacell;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.arnx.jsonic.JSON;
import net.ogalab.util.container.ListUtil;
import net.ogalab.util.fundamental.StringUtil;

@Deprecated
public class DataCell {
	
	public static final int  TDF4 = 1;
	public static final int  TDF2 = 2;
	public static final int  JSON4 = 3;
	public static final int  JSON2 = 4;

	String dataSet   = "";
	String ID        = "";
	String predicate = "";
	String value     = "";
	
	int    format = 2;
	
	
	Pattern pJsonDelim = Pattern.compile("^#---");
	
	public boolean isJSON() {
		if (format == JSON2 || format == JSON4)
			return true;
		else
			return false;
	}
	
	/** encode a string data in the TDF format.
	 * 
	 * @param str
	 * @return
	 */
	public String encodeTDF(String str) {
		str = str.replaceAll("\n", "\\\\" + "n");
		
		return str;
	}


	/** decode a TDF format datum to a string data.
	 * 
	 * @param str
	 * @return
	 */
    public String decodeTDF(String str) {
    	StringBuilder escaped = new StringBuilder();
    	escaped.append('\u0005');
    	
        //str = str.replaceAll("\\\\n", escaped +"n"); 
        //str = str.replaceAll("\\\\t", escaped +"t");
        str = str.replaceAll("\\n", "\n");
        //str = str.replaceAll("\\t", "\t");
        //str = str.replaceAll(escaped.toString(), "\\");
        return str;
    }

    public int getFormat() {
    	return this.format;
    }
    
    public void setFormat(int f) {
    	this.format = f;
    }
    
    public void setFormat(String f) {
    	if (f.toLowerCase().equals("tdf4"))
    		this.format = TDF4;
    	else if (f.toLowerCase().equals("tdf2"))
    		this.format = TDF2;
    	else if (f.toLowerCase().equals("json4"))
    		this.format = JSON4;
    	else if (f.toLowerCase().equals("json2"))
    		this.format = JSON2;
    }
    
    public String toString() {
    	String result = null;
    	if (this.format == TDF4)
    		result = TDF4();
    	else if (this.format == TDF2)
    		result = TDF2();
    	else if (this.format == JSON4)
    		result = JSON4();
    	else if (this.format == JSON2)
    		result = JSON2();
    	
    	return result;
    }
    
    
    public String TDF4() {
    	ArrayList<String> result = new ArrayList<String>();
    	result.add(dataSet);
    	result.add(ID);
    	result.add(predicate);
    	result.add(encodeTDF(value));
    	
    	return ListUtil.join("\t", result);
    }
    
    
    public String TDF2() {
    	ArrayList<String> result = new ArrayList<String>();
    	result.add(ID);
    	result.add(encodeTDF(value));
    	
    	return ListUtil.join("\t", result);
    }
  
    
    public void readTDF2(String tdf2) {
    	ArrayList<String> cols = StringUtil.splitByTab(tdf2);
    	ID = cols.get(0);
    	value = decodeTDF(cols.get(1));
    }
    
    
    public void readTDF4(String tdf4) {
    	ArrayList<String> cols = StringUtil.splitByTab(tdf4);
    	dataSet = cols.get(0);
    	ID = cols.get(1);
    	predicate= cols.get(2);
    	value = decodeTDF(cols.get(3));
    }

    


    public String JSON4() {
       	DataCell4 c4 = new DataCell4();
       	c4.setData_set(dataSet);
    	c4.setID(ID);
    	c4.setPredicate(predicate);
    	c4.setValue(value);
    	
    	String result = JSON.encode(c4, true);
    	return result;
    }


    public String JSON2() {
    	DataCell2 c2 = new DataCell2();
    	c2.setID(ID);
    	c2.setValue(value);
    	
    	String result = JSON.encode(c2, true);
    	return result;    	
    }
    
    
    public String getNextJsonString(BufferedReader br) throws IOException {
    	StringBuilder sb = new StringBuilder();
    	String result = null;
    	String line = null;
    	while (true) {
    		line = br.readLine();

    		if (line == null) {
    			if (sb.length() > 0) {
    				result = sb.toString();
    				sb.delete(0, sb.length()-1);
    				break;
    			}
    			else {
    				result = null;
    				break;
    			}
    		}
    		else {
    			Matcher m = pJsonDelim.matcher(line);
    			if (m.find()) {
    				if (sb.length() > 0) {
    					result = sb.toString();
    					sb.delete(0, sb.length()-1);
    					break;
    				}
    				else {
    					continue;
    				}
    			}
    			else {
    				sb.append(line + "\n");
    			}
    		}
	
    	}
    	return result;
    }
    
    
    
    public void readJSON4(String json) throws IOException {
    	DataCell4 c4   = JSON.decode(json, DataCell4.class);
    	this.dataSet   = c4.getData_set();
    	this.ID        = c4.getID();
    	this.predicate = c4.getPredicate();
    	this.value     = c4.getValue();
    }
    
 
    
    public void readJSON2(String json) throws IOException {
    	DataCell2 c2   = JSON.decode(json, DataCell2.class);
    	this.ID        = c2.getID();
    	this.value     = c2.getValue();
    }
    
    
    public String iterateJSON4(BufferedReader br) throws IOException {
    	String json     = getNextJsonString(br);
    	if (json == null) 
    		this.clear();
    	else 
    		readJSON4(json);

    	return json;
    }
    
 
    
    public String iterateJSON2(BufferedReader br) throws IOException {
    	String json     = getNextJsonString(br);
    	if (json == null)
    		this.clear();
    	else
    		readJSON2(json);
    	
    	return json;
    }
 

    
    public void clear() {
        dataSet   = "";
        ID        = "";
        predicate = "";
        value     = "";
    }

    public void setData(String ds, String id, String pred, String val) {
    	dataSet = ds;
    	ID      = id;
    	predicate = pred;
    	value     = val;
    }
    
    /*
    def read_tdf2_file(self, fname):
        f = open(fname)
        line = f.readline()
        while line:
            line = line.rstrip("\n")
            self.clear()
            self.read_tdf2(line)
            yield self
            line = f.readline()

        f.close()



    def read_tdf4_file(self, fname):
        f = open(fname)
        line = f.readline()
        while line:
            line = line.rstrip("\n")
            self.clear()
            self.read_tdf4(line)
            yield self
            line = f.readline()

        f.close()


    def read_json2_file(self, fname):
        buf = []
        f = open(fname)
        line = f.readline()
        while line:
            if line.startswith("#---"):
                cell = "".join(buf)
                self.read_json2(cell)
                buf=[]
                yield self
            else:
                buf.append(line)

            line = f.readline()
        f.close()


    def read_json4_file(self, fname):
        buf = []
        f = open(fname)
        line = f.readline()
        while line:
            if line.startswith("#---"):
                cell = "".join(buf)
                self.read_json4(cell)
                buf=[]
                yield self
            else:
                buf.append(line)

            line = f.readline()

        f.close()

        
    #---

    def read_tdf2_from_stdin(self):
        line = sys.stdin.readline()
        while line:
            line = line.rstrip("\n")
            self.clear()
            self.read_tdf2(line)
            yield self
            line = sys.stdin.readline()



    def read_tdf4_from_stdin(self):
        line = sys.stdin.readline()
        while line:
            line = line.rstrip("\n")
            self.clear()
            self.read_tdf4(line)
            yield self
            line = sys.stdin.readline()

        f.close()


    def read_json2_from_stdin(self):
        buf = []
        line = sys.stdin.readline()
        while line:
            if line.startswith("#---"):
                cell = "".join(buf)
                self.read_json2(cell)
                buf=[]
                yield self
            else:
                buf.append(line)
            line = sys.stdin.readline()

        f.close()


    def read_json4_from_stdin(self, fname):
        buf = []
        f = open(fname)
        line = f.readline()
        while line:
            if line.startswith("#---"):
                cell = "".join(buf)
                self.read_json4(cell)
                buf=[]
                yield self
            else:
                buf.append(line)
            line = sys.stdin.readline()

        f.close()
*/
	
	public String getDataSet() {
		return dataSet;
	}
	
	public void setDataSet(String dataSet) {
		this.dataSet = dataSet;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String id) {
		this.ID = id;
	}
	
	public String getPredicate() {
		return predicate;
	}
	
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
