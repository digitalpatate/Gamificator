package ch.heigvd.amt.gamificator.api.spec.helpers;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Signature {

    public static String generateSignature(String data, String key){
        String hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key).hmacHex(data);
        return Base64.getEncoder().encodeToString(hmac.getBytes(StandardCharsets.UTF_8));
    }
}
