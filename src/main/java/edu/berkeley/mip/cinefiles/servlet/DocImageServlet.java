package edu.berkeley.mip.cinefiles.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class DocImageServlet extends CinefilesServlet {
	static final long serialVersionUID =  1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo();
		
		if (pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}
		
		Path docImgDirPath = Paths.get(docImgDir);
		Path imageFilePath = docImgDirPath.resolve(pathInfo);
		
		if (imageFilePath.startsWith(docImgDirPath)) {
			File imageFile = new File(imageFilePath.toString());
			
			if (imageFile.canRead() && imageFile.isFile()) {
				resp.setContentType(getServletContext().getMimeType(imageFile.getPath()));
				resp.setContentLength((int) imageFile.length());
	
				DataInputStream in = new DataInputStream(new FileInputStream(imageFile));
				ServletOutputStream out = resp.getOutputStream();
				
				IOUtils.copy(in, out);
				
				in.close();
				out.close();
			}
		}
		
		if (!resp.isCommitted()) {
			resp.sendError(404);
		}
	}
}
