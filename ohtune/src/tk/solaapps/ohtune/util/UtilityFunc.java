package tk.solaapps.ohtune.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import javaQuery.j2ee.ImageResize;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class UtilityFunc {
	public static void compressImage(int toWidth, int toHeight, String filePath) throws IOException
	{
		int height = 0;
		int width = 0;
		ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(filePath));
		try {
			final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
			if (readers.hasNext()) {
				ImageReader reader = (ImageReader) readers.next();
				try {
					reader.setInput(in);
					width = reader.getWidth(0);
					height = reader.getHeight(0);
				} finally {
					reader.dispose();
				}
			}
		} finally {
			if (in != null)
				in.close();
		}
		ImageResize ir = new ImageResize();
		if(width / height > 1f)
		{
			if(width > 1024)
			{
				ir.compressImage(filePath, toWidth , height * toWidth / width);
			}
		}
		else
		{
			if(height > 768)
			{
				ir.compressImage(filePath, width * toHeight / height , toHeight);
			}
		}
	}
}
