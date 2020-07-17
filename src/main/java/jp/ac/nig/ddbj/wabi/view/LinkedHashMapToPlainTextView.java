/**
 * This file is part of WABI : DDBJ WebAPIs for Biology.
 *
 * WABI : DDBJ WebAPIs for Biology is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WABI : DDBJ WebAPIs for Biology is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with WABI : DDBJ WebAPIs for Biology.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.ac.nig.ddbj.wabi.view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ogalab.util.container.MapUtil;

import org.springframework.web.servlet.view.AbstractView;

import jp.ac.nig.ddbj.wabi.util.StringUtil;

public class LinkedHashMapToPlainTextView extends AbstractView {

	@Override
	protected void renderMergedOutputModel( Map<String, Object> model, HttpServletRequest req, HttpServletResponse res ) throws Exception {


		// Check if content type is specified.
		String contentType = "text/plain; charset=utf-8";
		String characterEncoding = "UTF-8";

		// Set content type and character encoding as given/determined.
		res.setContentType( contentType );
		if ( characterEncoding != null )
			res.setCharacterEncoding( characterEncoding );

		
		// Make string to view.
		ServletOutputStream out = res.getOutputStream();
		try {
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)model.get("linked-hash-map");
			String str = modelToString(map);
			res.setContentLength( str.getBytes().length );
			out.print(str);
		} catch (IOException e) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.print("Not found.");
		}
		out.flush();
		out.close();
	}
	
	public String modelToString(Map<String, Object> model) throws UnsupportedEncodingException {
		StringBuffer buf = new StringBuffer();
		Set<String> keySet = model.keySet();
		for (String k : keySet) {
			buf.append(k + ": ");
//			buf.append(StringUtil.encodeURI(String.valueOf(model.get(k)))+"\n");
			buf.append(String.valueOf(model.get(k))+"\n");
		}
		return buf.toString();
	}

}
