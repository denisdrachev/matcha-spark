package matcha.converter;

import lombok.SneakyThrows;
import matcha.model.SearchModel;
import matcha.properties.StringConstants;
import matcha.user.model.UserEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.everit.json.schema.ValidationException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    @SneakyThrows
    public static byte[] getPrepearPassword(String password, byte[] salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
//        String s = Arrays.toString(hash);
//
//        String[] byteValues = s.substring(1, s.length() - 1).split(",");
//        byte[] bytes = new byte[byteValues.length];
//        for (int i=0, len=bytes.length; i<len; i++) {
//            bytes[i] = Byte.parseByte(byteValues[i].trim());
//        }
//        String str = new String(bytes);
        return hash;
    }

    public static boolean checkPassword(String inputPassword, byte[] salt, byte[] currentPassword) {
        byte[] prepearPassword = getPrepearPassword(inputPassword, salt);
        return Arrays.equals(currentPassword, prepearPassword);
    }


    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static UserEntity initRegistryUser(UserEntity user) {
        user.setActivationCode(UUID.randomUUID().toString());
        user.setSalt(Utils.getSalt());
        user.setPasswordBytes(Utils.getPrepearPassword(user.getPassword(), user.getSalt()));
        return user;
    }

    public static String clearValidateMessage(List<ValidationException> exceptions) {
        String collect = exceptions.stream().map(e -> e.getMessage().split(StringConstants.validationDelimiter)[1]).collect(Collectors.joining(","));
        return collect;
    }

    public static String saveImageBase64ToFile(String encodedString) throws IOException {

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        System.out.println(generatedString);

        String outputFileName = "images\\".concat(generatedString);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        FileUtils.writeByteArrayToFile(new File(outputFileName), decodedBytes);
        return outputFileName;
    }

    public static String prepareOrderBy(SearchModel searchModel) {

        List<String> order = new ArrayList<>();

        if (searchModel.getSortAge() == -1) {
            order.add("p.age DESC");
        } else if (searchModel.getSortAge() == 1) {
            order.add("p.age");
        }
        if (searchModel.getSortLocation() == -1) {
            order.add("l.x DESC, l.y DESC");
        } else if (searchModel.getSortLocation() == 1) {
            order.add("l.x, l.y");
        }
        if (searchModel.getSortRating() == -1) {
            order.add("r.rating DESC");
        } else if (searchModel.getSortRating() == 1) {
            order.add("r.rating");
        }
        if (searchModel.getTags().size() != 0) {
            if (searchModel.getSortTags() == -1) {
                order.add("t.count DESC");
            } else if (searchModel.getSortTags() == 1) {
                order.add("t.count");
            }
        }
        if (order.size() > 0) {
            return " ORDER BY " + order.stream().collect(Collectors.joining(", "));
        }
        return "";
    }
}
