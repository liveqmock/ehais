package org.ehais.Junit;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeJunit {
	/**
	 * 生成包含字符串信息的二维码图片
	 * 
	 * @param outputStream
	 *            文件输出流路径
	 * @param content
	 *            二维码携带信息
	 * @param qrCodeSize
	 *            二维码图片大小
	 * @param imageFormat
	 *            二维码的格式
	 * @throws WriterException
	 * @throws IOException
	 */
	public static boolean createQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat)
			throws WriterException, IOException {
		// 设置二维码纠错级别ＭＡＰ
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // 矫错级别
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		// 创建比特矩阵(位矩阵)的QR码编码的字符串
		BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
		// 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth - 200, matrixWidth - 200, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// 使用比特矩阵画并保存图像
		graphics.setColor(Color.BLACK);
		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i - 100, j - 100, 1, 1);
				}
			}
		}
		return ImageIO.write(image, imageFormat, outputStream);
	}

	/**
	 * 读二维码并输出携带的信息
	 */
	public static void readQrCode(InputStream inputStream) throws IOException {
		// 从输入流中获取字符串信息
		// BufferedImage image = ImageIO.read(inputStream);
		// //将图像转换为二进制位图源
		// LuminanceSource source = new BufferedImageLuminanceSource(image);
		// BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		// QRCodeReader reader = new QRCodeReader();
		// Result result = null ;
		// try {
		// result = reader.decode(bitmap);
		// } catch (ReaderException e) {
		// e.printStackTrace();
		// }
		// System.out.println(result.getText());
	}

	/**
	 * 测试代码
	 * @throws Exception 
	 */
/*	public static void main(String[] args) throws Exception {

		createQrCode(new FileOutputStream(new File("d:\\qrcode_union.jpg")),
				// "http://w.ehais.com/api.php/Api/DiningApi/wxgo?dining=tyler&tableno=001",
				"http://w.ehais.com/api.php/Api/DiningUnionApi/wxgo", 900, "JPEG");
		// readQrCode(new FileInputStream(new File("d:\\qrcode.jpg")));
		
	}*/

	//////////////////////////////////////////////////////

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * 把生成的二维码存入到图片中
	 * 
	 * @param matrix
	 *            zxing包下的二维码类
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	private static int WIDTH = 128;
	private static int HEIGHT = 128;

	/**
	 * 生成二维码并写入文件
	 * 
	 * @param content
	 *            扫描二维码的内容
	 * @param format
	 *            图片格式 jpg
	 * @param file
	 *            文件
	 * @throws Exception
	 */
	public static void writeToFile(String content, String format, File file) throws Exception {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		@SuppressWarnings("rawtypes")
		Map hints = new HashMap();
		// 设置UTF-8， 防止中文乱码
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 设置二维码四周白色区域的大小
		hints.put(EncodeHintType.MARGIN, 1);
		// 设置二维码的容错性
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 画二维码
		BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
		BufferedImage image = toBufferedImage(bitMatrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	/**
	 * 给二维码图片加上文字
	 * 
	 * @param pressText
	 *            文字
	 * @param qrFile
	 *            二维码文件
	 * @param fontStyle
	 * @param color
	 * @param fontSize
	 */
	public static void pressText(String pressText, File qrFile, int fontStyle, Color color, int fontSize)
			throws Exception {
		pressText = new String(pressText.getBytes(), "utf-8");
		Image src = ImageIO.read(qrFile);
		int imageW = src.getWidth(null);
		int imageH = src.getHeight(null);
		BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		g.drawImage(src, 0, 0, imageW, imageH, null);
		// 设置画笔的颜色
		g.setColor(color);
		// 设置字体
		Font font = new Font("宋体", fontStyle, fontSize);
		FontMetrics metrics = g.getFontMetrics(font);
		// 文字在图片中的坐标 这里设置在中间
		int startX = (WIDTH - metrics.stringWidth(pressText)) / 2;
		int startY = HEIGHT;
		g.setFont(font);
		g.drawString(pressText, startX, startY);
		g.dispose();
		FileOutputStream out = new FileOutputStream(qrFile);
		ImageIO.write(image, "JPEG", out);
		out.close();
		System.out.println("image press success");
	}

	public static void main(String[] args) throws Exception {
		File qrcFile = new File("D:/","index.jpg");
		QRCodeJunit.writeToFile("http://q.ehais.com/hlj/index.html", "jpg", qrcFile);
//		QRCodeJunit.pressText("华乐街广东国际大厦党员服务中心\r\n广州易海司团队", qrcFile, 2, Color.RED, 10);
//		QRCodeJunit.pressText("华乐街广东国际大厦党员服务中心\r\n广州易海司团队", qrcFile, 2, Color.RED, 10);
		 
		 
	}
	
}
