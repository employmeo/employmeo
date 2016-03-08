package com.employmeo.util;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;


public class ImageManagementUtil {

	private static String AVATARDIRECTORY = null;
	private static final int MAXHEIGHT = 65;
	private static final int MAXWIDTH = 65;	
	public static final String AVATARPATH="/images/avatars/";
	private static Logger logger = Logger.getLogger("ImageManagementUtil");

	public static void staticInit(String avatarfiledir) {
		ProcessStarter.setGlobalSearchPath(System.getenv("PATH"));
		AVATARDIRECTORY = avatarfiledir;
		logger.info("Initialized MPImageUtil with avatar images to: " + AVATARDIRECTORY);
	}
	
	public static String processImageFromUpload (HttpServletRequest req) {
        String imagePath = null;
		try {
	    	 Collection<Part> parts = req.getParts();
	    	 for (Part part : parts) {
	    	        String partHeader = part.getHeader("content-disposition");
	    	        String name = null;
	    	        for (String cd : partHeader.split(";")) {
	    	            if (cd.trim().startsWith("filename")) {
	    	                name = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	    	            }
	    	        }
	    	        if (name != null) {
	    	        	String timecode = Calendar.getInstance().getTimeInMillis()+"_";
	    	            File original = File.createTempFile(timecode + name, ".tmp");
	    	            File output = new File(AVATARDIRECTORY, timecode+name);
   	            		imagePath = AVATARPATH + timecode+name;

	    	            part.write(original.getAbsolutePath());
	    	            ConvertCmd cmd = new ConvertCmd();
	    	            cmd.setAsyncMode(false);
   	            		IMOperation imOp = new IMOperation();
   	            		imOp.addImage(original.getAbsolutePath());
   	            		imOp.scale(MAXWIDTH,MAXHEIGHT);
   	            		imOp.addImage(output.getAbsolutePath());
   	            		cmd.run(imOp);
   	            		original.delete();
   	     			    // .saveCustomImage(output, imagePath);
	    	        }
	    	 }
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
		return imagePath;
	}
	
	public static void prepCustomImage(String path, HttpServletRequest req) {
		if (path.indexOf("avatar")>-1) {
			File image = new File(req.getServletContext().getRealPath(path));
			if (!image.exists()) {
				//.loadCustomImage(image, path);
			}
		}
		return;
	}
	
}
