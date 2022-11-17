package Helper;

import Entity.NguoiDung;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthHelper {

    //duy trì user đăng nhập vào hệ thống
    public static NguoiDung user = null;

    //xóa user khi đăng xuất
    public static void clear() {
        AuthHelper.user = null;
    }

    //kiểm tra đăng nhập hay chưa
    public static boolean isLogin() {
        return AuthHelper.user != null;
    }

    //check quản lý
    public static boolean isManager() {
        if (user.getIdVT() == 1) {
            return AuthHelper.isLogin();
        }
        return false;
    }

    //check thu ngân
    public static boolean isThuNgan() {
        if (user.getIdVT() == 2) {
            return AuthHelper.isLogin();
        }
        return false;
    }

    //check pha chế
    public static boolean isPhaChe() {
        if (user.getIdVT() == 3) {
            return AuthHelper.isLogin();
        }
        return false;
    }
    
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            return convertByteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static String convertByteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
