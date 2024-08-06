package school_lessons.DES;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;
public class DESEncryption {

    // DES 키 생성
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");// DES 알고리즘용 키 생성기를 가져옵니다.
        keyGenerator.init(56); // 키 생성기를 초기화합니다. DES의 키 길이는 56비트입니다.
        // 나머지 8비트는 패리트 비트다. 이 비트는 키의 유효성을 검증하기 때문에 암호화에 직접적으로 관려하지 않는다.
        return keyGenerator.generateKey(); // 비밀 키를 생성하여 반환합니다.
    }

    // 평문을 DES로 암호화
    // 평문 data를 byte배열로 바꾼뒤 string으로 encode하게 됩니다.
    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES"); // DES 알고리즘을 사용하기 위한 Cipher 객체를 생성합니다.
        cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 암호화 모드로 초기화
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)); // 평문을 바이트 배열로 반환
        return Base64.getEncoder().encodeToString(encryptedBytes); // 암호화된 바이트 배열 Base64로 인코딩하여 평문으로 반환
    }

    // 암호화된 평문을 DES로 복호화

    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES"); // DES 암호화 알고리즘 사용하기 위해 Cipher 객체 생성
        cipher.init(Cipher.DECRYPT_MODE, secretKey); // 복호화로 초기화
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText)); // Base64로 인코딩된 암호문 디코딩한 후 북호화
        return new String(decryptedBytes, StandardCharsets.UTF_8); // 복호화된 바이트 배열을 문자열로 변환하여 반환
    }

    public static void main(String[] args) {
        try {
            // 비밀 키 생성
            SecretKey secretKey = generateKey();

            // 사용자로부터 평문 입력 받기
            Scanner scanner = new Scanner(System.in);
            System.out.print("암호화할 평문을 입력하세요: ");
            String plainText = scanner.nextLine();

            // 암호화
            String encryptedText = encrypt(plainText, secretKey);
            System.out.println("암호화된 텍스트: " + encryptedText);

            // 복호화
            String decryptedText = decrypt(encryptedText, secretKey);
            System.out.println("복호화된 텍스트: " + decryptedText);

        } catch (Exception e) {
            System.err.println("오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
