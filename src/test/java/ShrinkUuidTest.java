import org.junit.jupiter.api.RepeatedTest;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Can a UTF-8 UUID be shrunk to less than 30 char using base64?
 */
public class ShrinkUuidTest {

    public static String shorten(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        // Use UrlEncoder to avoid '/' and '+' which can be problematic in URLs
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bb.array());
    }

    public static UUID expand(String shortStr) {
        byte[] bytes = Base64.getUrlDecoder().decode(shortStr);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }

    @RepeatedTest(1000)
    void shrinkAUuidTest() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        String uuidB64 = shorten(uuid);

        System.out.println("uuid: " + uuidAsString + ", b64: " + uuidB64 + ", length: " + uuidB64.length());
        assertThat(uuidAsString.length()).isEqualTo(36);
        assertThat(uuidB64.length()).isEqualTo(22);

        UUID expandedUuid = expand(uuidB64);
        assertThat(expandedUuid.toString()).isEqualTo(uuidAsString);
    }


}
