package de.web.ngthi.palaver;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.web.ngthi.palaver.mvp.model.LocalUser;
import de.web.ngthi.palaver.mvp.model.Message;
import de.web.ngthi.palaver.mvp.model.User;

public class MockupData {

    private static LocalUser localUser;

    public static LocalUser getLocalUser() {
        if(localUser == null)
            initData();
        return localUser;
    }

    private static void initData() {
        localUser = new LocalUser("Peter", "Password");

        List<User> friends = new LinkedList<>();
        friends.add(new User("Anna"));
        friends.add(new User("Anton"));
        friends.add(new User("Otto"));
        friends.add(new User("Lisa"));
        friends.add(new User("Ray"));

        for(User f : friends)
            localUser.addFriend(f);

        DateTime date = DateTime.now();
        String text = MockupData.largeText;
        String[] textFragments = text.split("\\r\\n|[\\r\\n]|\\.");
        Random r = new Random();
        for(int i = 0; i < textFragments.length; i++) {
            long offset = r.nextInt(1000000);
            int user = r.nextInt();
            date = date.minus(offset);
            int friendIndex = r.nextInt(friends.size());
            User friend = localUser.getSortedFriendList().get(friendIndex);
            Message m = new Message(user % 2 == 0 ? localUser : friend, user % 2 == 0 ? friend : localUser, textFragments[i].replaceAll("\\t\\n", ""), date, Message.Status.SENT);
            localUser.addMessage(friend.getUsername(), m);
        }
    }


    private static final String largeText =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus et interdum leo. Cras euismod volutpat orci, eleifend accumsan velit laoreet eget. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla facilisi. Phasellus molestie, diam sagittis facilisis molestie, urna metus malesuada metus, eget tincidunt tellus arcu sed augue. Sed eleifend mauris ac venenatis varius. Donec commodo, lacus in dignissim blandit, lacus quam rutrum velit, nec venenatis neque mauris quis augue. Quisque tincidunt vitae erat vel interdum. Nullam nulla turpis, consequat ut arcu ullamcorper, venenatis hendrerit ante. Aliquam et lacus et enim consequat rutrum at eu elit. Nulla eu orci est. Proin aliquam aliquet ultrices. Pellentesque ante nibh, bibendum ac magna vitae, vulputate maximus felis. Aenean tempor pretium diam, quis tincidunt nunc dignissim et. Nulla dapibus vestibulum gravida.\n" +

                    "Duis egestas dolor sit amet ligula congue, non pretium enim fermentum. Curabitur tincidunt, nulla eget iaculis tempor, mi arcu tempor justo, ut iaculis est augue ac tortor. Donec at dui imperdiet, tempor quam a, fermentum dui. Vestibulum a commodo arcu, eu gravida massa. Nulla facilisi. Aliquam condimentum sit amet lorem sed aliquam. In venenatis auctor faucibus. Morbi pretium nibh at luctus interdum. Pellentesque et molestie orci, et porta erat.\n" +

                    "Sed sit amet ex ligula. Nullam ornare urna in mi tempus, eu finibus diam tristique. Etiam sem mauris, porttitor quis fringilla sit amet, euismod at elit. Aliquam facilisis neque nisi, a volutpat dui dapibus non. Cras sit amet nulla cursus, pharetra lacus non, placerat erat. Integer eget ante metus. Nam diam odio, suscipit ut semper vitae, consectetur in eros. Nullam non gravida mi. Ut posuere suscipit posuere. Sed fringilla ex at scelerisque tincidunt. Quisque finibus dignissim dolor, ac feugiat dui molestie rutrum. Nulla sed malesuada justo. Phasellus auctor tincidunt eros, id mollis arcu consectetur quis. Mauris blandit sem eget efficitur laoreet. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Proin finibus odio vel ex tincidunt vulputate.\n" +

                    "Aliquam vitae eros dignissim, aliquam nisl vitae, gravida nibh. Aenean dignissim porta interdum. Proin tincidunt interdum mi eget malesuada. Donec orci enim, mattis eget malesuada in, egestas vel augue. Ut quis mauris in odio congue hendrerit ut nec quam. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus turpis lectus, accumsan nec aliquet ultrices, consequat sit amet metus. Quisque non odio risus. Donec a turpis neque.\n" +

                    "Morbi mollis sit amet sem non suscipit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam ac dui eu tellus luctus fermentum id non metus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Mauris ultricies lectus eget nulla tincidunt, sed fringilla ex vehicula. Vestibulum aliquam congue nisi, quis malesuada diam vulputate at. Nulla dui ante, ultricies aliquam rutrum id, aliquet porta enim. Etiam fermentum felis orci, ut dignissim nunc elementum sit amet. Pellentesque vitae tempus lacus. Proin sed metus id turpis viverra sodales. Nam imperdiet velit sed leo scelerisque, ut varius est pellentesque. Ut pharetra a elit quis scelerisque.\n" +

                    "Nunc scelerisque eros id bibendum imperdiet. Nullam convallis massa et ipsum volutpat malesuada. Nam nisl dui, finibus eu enim non, pretium tincidunt diam. Sed erat eros, porta eu elit ac, tincidunt tempus est. Maecenas vitae ex ut dolor maximus molestie id ut turpis. Aliquam porttitor, ipsum nec laoreet dapibus, urna mi fringilla ante, eu sagittis leo tortor a dolor. Donec ultrices cursus neque sodales aliquet. Nam purus tortor, vehicula non ultrices sed, ullamcorper at urna. Nunc sit amet risus congue, interdum risus non, lacinia mauris. Quisque varius, sem non elementum placerat, diam tortor placerat nulla, eu venenatis ante arcu quis sapien. Suspendisse ut sapien volutpat, faucibus mauris consectetur, porttitor tortor. Proin condimentum eros at nibh semper scelerisque. Sed hendrerit erat vitae velit lobortis venenatis.\n" +

                    "Ut finibus eu sapien in porta. Maecenas urna lorem, tincidunt ac tellus nec, commodo cursus ligula. Proin at eros urna. Curabitur bibendum interdum odio sit amet viverra. Mauris vel malesuada quam, in consequat magna. Cras bibendum nibh a lorem semper euismod. Etiam tempus orci nec erat facilisis, vehicula suscipit ante pellentesque. Fusce eget ipsum tristique, lacinia felis quis, ornare purus. Aenean porttitor justo ultrices leo bibendum egestas. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;\n" +

                    "Morbi massa risus, facilisis nec leo nec, cursus pellentesque est. Cras aliquet at nibh nec dictum. Nullam hendrerit lectus quis accumsan pretium. Suspendisse mi metus, feugiat in ex in, finibus imperdiet tortor. Integer sagittis mi vel quam congue, vitae ultrices erat accumsan. Fusce ut ligula a erat iaculis ultricies. Duis et nunc in lorem varius pretium. Fusce consequat, tellus id vehicula hendrerit, dui arcu eleifend nibh, ac aliquam lorem lectus malesuada risus. Donec eleifend aliquam facilisis. Curabitur vitae est ultrices, fermentum magna quis, vehicula mauris. Nullam in pulvinar magna, ac gravida neque. Pellentesque a lectus eleifend odio maximus porta ut at diam. Vestibulum fermentum mi non tellus porttitor, tincidunt hendrerit neque tristique. Aenean ut odio eget nisi iaculis tincidunt. Quisque non convallis sem.\n" +

                    "Fusce sit amet odio sit amet nunc iaculis rutrum. Etiam risus massa, aliquam eu ipsum at, eleifend mollis libero. In hac habitasse platea dictumst. Phasellus eleifend maximus auctor. Etiam ultrices ipsum sed nisl tincidunt, in vestibulum lectus maximus. Donec eget erat pharetra, consectetur orci vitae, efficitur augue. Nullam viverra porta accumsan. Cras condimentum diam et dui luctus convallis nec ut nulla. Vivamus fringilla finibus ligula, vitae condimentum dui tincidunt vel. Pellentesque in gravida eros, et rhoncus nunc. Aliquam interdum interdum nisl non ultrices. Phasellus ut elementum magna. Pellentesque venenatis ultrices tellus a consequat. Proin ultricies molestie pellentesque. Sed venenatis lacus at ex viverra faucibus. Nam quam lectus, pharetra eu orci quis, sagittis pellentesque ligula.\n" +

                    "Integer leo felis, maximus interdum justo et, sagittis maximus nibh. Donec accumsan ex ut ipsum eleifend, eu egestas mauris imperdiet. Aliquam nec nibh nec magna consectetur varius vitae vitae nisl. Sed id velit quis odio facilisis blandit. Sed sit amet euismod purus, porta eleifend lacus. Duis pulvinar imperdiet commodo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce a nisi tempor, mattis est eu, pulvinar metus. Ut arcu dui, finibus eu mattis ut, tempor id urna. Suspendisse tempus ipsum mauris, non mattis nunc interdum non.";
}
