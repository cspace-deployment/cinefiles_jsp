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

import edu.berkeley.mip.cinefiles.entity.DocDetail;

public class DocImageServlet extends CinefilesServlet {
	public static final String THUMBNAIL_DIR = "t";

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
			try {
				if (isAccessible(imageFilePath, req)) {
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
				else {
					resp.sendError(403, "Document restricted");
				}
			}
			catch(NumberFormatException e) {
				// Invalid doc id
			}
		}
		
		if (!resp.isCommitted()) {
			resp.sendError(404);
		}
	}
	
	
	/**
	 * Checks document access restrictions.
	 * 
	 * @param  imageFilePath The path to the image
	 * @param  req           The request
	 * @return true if the image is accessible to the request,
	 *         false otherwise.
	 */
	protected boolean isAccessible(Path imageFilePath, HttpServletRequest req) {
		if (isThumbnail(imageFilePath)) {
			// All thumbnails are accessible, regardless of document restriction level.
			return true;
		}
		
		int userAccess = getUserAccess(req);
		
		if (userAccess == 0) {
			// User has complete access.
			return true;
		}
		
		// FIXME: DocDetail provides a lot of extra information. May need a lighter weight
		// query that just retrieves the document access code.
		
		DocDetail docDetail = new DocDetail(dataSource, dataBase, extractDocId(imageFilePath));
		
		return (docDetail.getAccessCode() >= userAccess);
	}
	
	protected boolean isThumbnail(Path imageFilePath) {
		return imageFilePath.getParent().endsWith(THUMBNAIL_DIR);
	}
	
	protected Integer extractDocId(Path imageFilePath) {
		String fileName = imageFilePath.getFileName().toString();
		int dotIndex = fileName.indexOf('.');
		String idString = fileName.substring(0, dotIndex);
		
		return Integer.parseInt(idString);
	}
}
