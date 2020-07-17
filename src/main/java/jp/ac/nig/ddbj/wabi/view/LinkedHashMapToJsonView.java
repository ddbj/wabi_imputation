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
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

import org.springframework.web.servlet.view.AbstractView;

import jp.ac.nig.ddbj.wabi.util.StringUtil;

public class LinkedHashMapToJsonView extends AbstractView {

	@Override
	protected void renderMergedOutputModel( Map<String, Object> model, HttpServletRequest req, HttpServletResponse res ) throws Exception {

		String contentType = "application/json; charset=utf-8";
		res.setContentType( contentType );

		String characterEncoding = "UTF-8";
		if ( characterEncoding != null )
			res.setCharacterEncoding( characterEncoding );

		ServletOutputStream out = res.getOutputStream();
		try {
//			String jsonStr = JSON.encode(encodeURI(model.get("linked-hash-map")), true);
			String jsonStr = JSON.encode(model.get("linked-hash-map"), true);
			res.setContentLength( jsonStr.getBytes().length );
			out.print(jsonStr);
		} catch (IOException e) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.print("Not found.");
		}
		out.flush();
		out.close();
	}

//	private static Object encodeURI(Object src) throws UnsupportedEncodingException {
//		if (null==src) return null;
//		if (src instanceof Map) {
//			Map map = (Map)src;
//			Map<String, Object> dst = new LinkedHashMap<String, Object>();
//			for (Object key : map.keySet()) {
//				dst.put(String.valueOf(key), encodeURI(map.get(key)));
//			}
//			return dst;
//		} else if (src instanceof Iterable) {
//			List<Object> dst = new ArrayList<Object>();
//			Iterable iterable = (Iterable)src;
//			for (Object item : iterable) {
//				dst.add(encodeURI(item));
//			}
//			return dst;
//		} else {
//			return StringUtil.encodeURI(String.valueOf(src));
//		}
//	}

}
