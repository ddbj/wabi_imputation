
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

public class PlainTextView extends AbstractView {

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
			String message = (String)model.get("message");
			res.setContentLength( message.getBytes().length );
			out.print(message);
		} catch (IOException e) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.print("Not found.");
		}
		out.flush();
		out.close();
	}
	

}
