package net.ogalab.util.fundamental;

import java.util.ArrayList;
import java.util.Comparator;

public class VersionStringComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		
		int result = 0;
		
		ArrayList<String> o1Components = StringUtil.splitByChar(o1, '.');
		ArrayList<String> o2Components = StringUtil.splitByChar(o2, '.');
		
		for (int i=0; i<o1Components.size(); i++) {
			if (i < o2Components.size() ) {
				String c1 = o1Components.get(i);
				String c2 = o2Components.get(i);

				boolean b1 = StringUtil.isInteger(c1);
				boolean b2 = StringUtil.isInteger(c2);

				if (b1 && b2) {
					Integer i1 = Type.toInteger(c1);
					Integer i2 = Type.toInteger(c2);
					result = (i1>i2 ? 1 : (i1==i2 ? 0 : -1));
					if (result != 0)
						break;
				}
				else if (b1 && !b2) {
					result = 1;
				}
				else if (!b1 && b2) {
					result = -1;
				}
				else { // !b1 && !b2
					result = o1.compareTo(o2);
					if (result != 0)
						break;
				}
			}
			else {
				result = -1;
			}
		}
		
		return result;
	}

}
