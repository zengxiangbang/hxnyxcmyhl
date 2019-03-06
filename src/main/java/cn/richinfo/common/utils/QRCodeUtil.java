package cn.richinfo.common.utils;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public final class QRCodeUtil {

    private static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final String IMAGE_SUFFIX_FORMAT = "jpg";
    private static final String DEFAULT_ENCODEING = "UTF-8";

    public QRCodeUtil() {
    }

    /**
     * @param matrix
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

    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * @param text   二维码内容
     * @param width  二维码宽度
     * @param height 二维码高度
     * @throws Exception
     */
    public static BufferedImage getQRCode(String text, int width, int height) throws Exception {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, DEFAULT_ENCODEING);//内容所使用编码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage image = QRCodeUtil.toBufferedImage(bitMatrix);

        return image;

    }

    /**
     * 二维码的生成
     * 将内容contents生成长宽均为width的图片，图片路径由imgPath指定
     */
    public static File encodeQRCode(String contents, int width, String imgPath) {
        return encodeQRCode(contents, width, width, imgPath);
    }

    /**
     * 二维码的生成
     * 将内容contents生成长为width，宽为width的图片，图片路径由imgPath指定
     */
    public static File encodeQRCode(String contents, int width, int height, String imgPath) {
        try {
            Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.CHARACTER_SET, DEFAULT_ENCODEING);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);

            File imageFile = new File(imgPath);
            writeToFile(bitMatrix, IMAGE_SUFFIX_FORMAT, imageFile);

            return imageFile;

        } catch (Exception e) {
            logger.error("create QR code error:{}", e.getMessage());
            return null;
        }
    }

    /**
     * 1、二维码的生成
     * 将Zxing-core.jar 包加入到classpath下。
     * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，可以将该类拷贝到源码中，
     * 这里我将该类的源码贴上，可以直接使用。直接可以生成二维码的代码
     * <p>
     * 注意：
     * 在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的
     * static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";修改为UTF-8，否则中文编译后解析不了
     */
    public static void encodeQRCode(String content, String path, String imgName, int width, int height) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();//Zxing是Google提供的关于条码
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, DEFAULT_ENCODEING);
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);//这里是照片的大小
            File file = new File(path, imgName);
            writeToFile(bitMatrix, IMAGE_SUFFIX_FORMAT, file);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 二维码解析
     * 和生成一样，我们需要一个辅助类（ BufferedImageLuminanceSource），
     * 同样该类Google也提供了，但是需要自行创建BufferedImageLuminanceSource类
     */
    public static String decodeQRCode(String imgPath) {
        try {
            File file = new File(imgPath);
            BufferedImage image;
            image = ImageIO.read(file);
            if (image == null) {
                throw new IOException("Could not decode image");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
            hints.put(DecodeHintType.CHARACTER_SET, DEFAULT_ENCODEING);
            //解码设置编码方式为：utf-8，
            Result result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.error(ex.getMessage());
            return null;
        }
    }

    public static String decodeQRCode(InputStream inputStream) {
        try {
            BufferedImage image;
            image = ImageIO.read(inputStream);
            if (image == null) {
                throw new IOException("Could not decode image");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
            hints.put(DecodeHintType.CHARACTER_SET, DEFAULT_ENCODEING);
            //解码设置编码方式为：utf-8，
            Result result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.error(ex.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {

        encodeQRCode("test.............", 300, "d://test.png");
        System.out.println("decodeQRCode = [" + decodeQRCode("d://test.png") + "]");
    }

}
