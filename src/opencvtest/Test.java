/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencvtest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import static org.opencv.core.CvType.CV_32FC1;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Simon
 */
public class Test {
    Mat src, src_gray = new Mat();
    int tresh = 200;
    int maxTresh = 255;
    
    public Mat  corner(int thresh){
       Mat dst = new Mat();
       Mat dst_norm = new Mat(), dst_norm_scaled = new Mat(); 
       dst = Mat.zeros(src.size(), CV_32FC1);
       
       int blockSize = 2;
       int apertureSize = 3;
       double k = 0.00;
       
        Imgproc.cornerHarris(src_gray, dst, blockSize, apertureSize, k, Core.BORDER_DEFAULT);
        
        Core.normalize(dst, dst_norm, 0, 255, Core.NORM_MINMAX, CV_32FC1);
        Core.convertScaleAbs(dst_norm, dst_norm_scaled);
        
        for(int j = 0; j<dst_norm.rows();j++){
            for(int i=0;i<dst_norm.cols();i++){
                if((int)dst_norm.get(j,i)[0]>tresh){
                    Imgproc.circle(dst_norm_scaled,new Point(i,j),5, new Scalar(152),2,8,0);
                }
            }
        }
        
        
return(dst_norm_scaled);

    }

    public void obraz() {
        src = Imgcodecs.imread("C:\\Users\\Simon\\Desktop\\szachy.jpg", Imgcodecs.IMREAD_COLOR);
        Imgproc.cvtColor(src, src_gray, Imgproc.COLOR_BGR2GRAY);
        
        Imgproc.resize(corner(0), src,  new Size(640, 480));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", src, matOfByte);
        byte[] byteArray = matOfByte.toArray();
    BufferedImage bufImage = null;
    try {
        InputStream in = new ByteArrayInputStream(byteArray);
        bufImage = ImageIO.read(in);
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
        frame.pack();
        frame.setVisible(true);
    } catch (Exception e) {
        e.printStackTrace();
    }
    corner(0);
    }
}
