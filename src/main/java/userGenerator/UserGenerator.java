package userGenerator;

import api.User;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class UserGenerator {

    public User randomUser() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        return new User("TestTest" + uuid, "test" + uuid + "@test.com", "TestPassword" + uuid);
    }

}