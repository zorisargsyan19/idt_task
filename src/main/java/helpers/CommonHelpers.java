package helpers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;

public final class CommonHelpers {

    private static final Random RANDOM = new Random();

    public static String getRandomEmail() {
        return String.format("%s@yopmail.com", getRandomAlphanumericString(6));
    }

    public static String getRandomAlphanumericString(int length) {
        String alphaNumeric = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        StringBuilder sb = new StringBuilder();
        int allowedCharsLength = alphaNumeric.length();
        for (int i = 0; i < length; i++) {
            sb.append(alphaNumeric.charAt(RANDOM.nextInt(allowedCharsLength)));
        }
        return sb.toString();
    }

    /**
     *
     * @param date - String containing date in dd-MM-yyyy format
     * @return - LocalDateTime object
     */
    public static LocalDate convertToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter);
    }

    public static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public static Properties readProperties(String path) {
        Properties properties;
        try {
            properties = new Properties();
            properties.load(CommonHelpers.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error(String.format("couldn't load the %s file", path));
        }
        return properties;
    }

    private CommonHelpers() {}
}
