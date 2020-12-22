package matcha.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Delete;
import matcha.model.SearchModel;
import matcha.properties.StringConstants;
import matcha.user.model.UserEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.everit.json.schema.ValidationException;
import org.sql2o.Sql2o;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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

        LinkedList<String> sortOrderList = searchModel.getSortOrderList();

        for (String s : sortOrderList) {
            if (s.equals("sortAge") && searchModel.getSortAge() != 0) {
                if (searchModel.getSortAge() == -1) {
                    order.add("p.age DESC");
                } else if (searchModel.getSortAge() == 1) {
                    order.add("p.age");
                }
            }
            if (s.equals("sortLocation") && searchModel.getSortLocation() != 0) {
                if (searchModel.getSortLocation() == -1) {
                    order.add("distance DESC");
                } else if (searchModel.getSortLocation() == 1) {
                    order.add("distance ");
                }
            }
            if (s.equals("sortRating") && searchModel.getSortRating() != 0) {
                if (searchModel.getSortRating() == -1) {
                    order.add("r.rating DESC");
                } else if (searchModel.getSortRating() == 1) {
                    order.add("r.rating");
                }
            }
            if (s.equals("sortTags") && searchModel.getSortTags() != 0) {
                if (searchModel.getTags().size() != 0) {
                    if (searchModel.getSortTags() == -1) {
                        order.add("t.count DESC");
                    } else if (searchModel.getSortTags() == 1) {
                        order.add("t.count");
                    }
                }
            }
        }

        if (order.size() > 0) {
            return " ORDER BY " + order.stream().collect(Collectors.joining(", "));
        }
        return "";
    }

    public static void clearAllTables() {
        executeDbQuery(Delete.deleteUsers);
        executeDbQuery(Delete.deleteTagRelations);
        executeDbQuery(Delete.deleteTags);
        executeDbQuery(Delete.deleteConnected);
        executeDbQuery(Delete.deleteEvents);
        executeDbQuery(Delete.deleteChat);
        executeDbQuery(Delete.deleteImageLikeEvents);
        executeDbQuery(Delete.deleteLocations);
        executeDbQuery(Delete.deleteBlacklist);
        executeDbQuery(Delete.deleteRating);
        executeDbQuery(Delete.deleteProfiles);
        executeDbQuery(Delete.deleteImages);
    }

    private static void executeDbQuery(String query) {
        Sql2o sql2o = Sql2oModel.getSql2o();
        try (org.sql2o.Connection conn = sql2o.open()) {
            int result = conn.createQuery(query)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Result: {} : {}", query, result);
        } catch (Exception e) {
            log.warn("Exception. {}: {}", query, e.getMessage());
        }
    }

    public static Double getDistance(Double x1, Double y1, Double x2, Double y2) {
        Double d2r = (Math.PI / 180.0);
        Double dlong = (y2 - y1) * d2r;
        Double dlat = (x2 - x1) * d2r;
        Double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(x1 * d2r) * Math.cos(x2 * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double d = 6367 * c;
        return d;
    }
}
