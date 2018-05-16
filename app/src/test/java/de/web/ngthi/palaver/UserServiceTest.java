package de.web.ngthi.palaver;

import junit.framework.TestCase;

import de.web.ngthi.palaver.controller.RestController;
import de.web.ngthi.palaver.model.LocalUser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserServiceTest extends TestCase {

    private RestController controller;
    private LocalUser user;

    final private String validate_wrongPassword = "Passwort nicht korrekt";
    final private String validate_noUser = "Benutzer existiert nicht";


//    @Override
//    public void setUp() {
//        controller = new RestController();
//        LocalDateTime time = LocalDateTime.now();
//        String name = "Test" + time.toString();
//        String password = "Password" + time.toString();
//        user = new LocalUser(name, password);
//    }
//
//    @Test
//    public void testRegisterNewUser() {
//        LocalDateTime time = LocalDateTime.now();
//        String name = "Test" + time.toString();
//        String password = "Password" + time.toString();
//        LocalUser user = new LocalUser(name, password);
//        ServerRequest request = new ServerRequest.Builder()
//                .localUser(user)
//                .build();
//
//
//        ServerReply reply = controller.register(request).;
//        assertThat(reply.getMsgType(), is(equalTo(1)));
//    }
//
//    @Test
//    public void testRegisterExistingUser() {
//        ServerRequest request = new ServerRequest.Builder()
//                .localUser(user)
//                .build();
//
//        ServerReply reply = controller.register(request);
//        assertThat(reply.getMsgType(), is(equalTo(0)));
//    }
}
