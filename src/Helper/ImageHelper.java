package Helper;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.*;
import javax.swing.ImageIcon;

public class ImageHelper {

    //icon app
    public static Image getAppIcon(){
        URL url = ImageHelper.class.getResource("/Icons/LogoApp.png");
        return new ImageIcon(url).getImage();
    }
    
    //Đưa file vào
    public static void save(File src){
        File dst = new File("Logos", src.getName());
        //Tạo thư mục Logos nếu chưa tồn tại
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }
        //Copy file vào thư mục Logos
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from,to,StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    //hiển thị
    public static ImageIcon read(String fileName){
        File path = new File("Logos",fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
}
