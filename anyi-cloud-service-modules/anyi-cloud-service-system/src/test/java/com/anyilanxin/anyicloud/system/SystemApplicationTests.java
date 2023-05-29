

package com.anyilanxin.anyicloud.system;

import com.anyilanxin.anyicloud.oauth2common.utils.PasswordCheck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SystemApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() {
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        PasswordCheck.PasswordInfo passwordInfo = passwordCheck.getPasswordInfo("111");
        System.out.println(passwordInfo.getEncodedPassword());
        System.out.println(passwordInfo.getSalt());
    }
}
