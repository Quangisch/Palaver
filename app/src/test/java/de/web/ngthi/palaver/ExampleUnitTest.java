package de.web.ngthi.palaver;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Random;

import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        LocalUser local = new LocalUser("Peter", "Password");
        User f1 = new User("Anna");
        local.addFriend(new User("Anna"));

        DateTime date = new DateTime(2018, 10, 3, 5, 30, 56);

        String[] textFragments = text.split("\\r\\n|[\\r\\n]");
        Random r = new Random();
        for(int i = 0; i < textFragments.length; i++) {
            long offset = r.nextInt(1000000);
            int user = r.nextInt();
            date = date.plus(offset);
            Message m = new Message(user % 2 == 0 ? local : f1, user % 2 == 0 ? f1 : local, textFragments[i].replaceAll("\\t\\n", ""), date);
            System.out.println(m);
            local.addMessage(m);
        }



    }

    private String text = "Material is the metaphor.\n"+

            "A material metaphor is the unifying theory of a rationalized space and a system of motion.\n"+
            "The material is grounded in tactile reality, inspired by the study of paper and ink, yet "+
            "technologically advanced and open to imagination and magic.\n"+
            "Surfaces and edges of the material provide visual cues that are grounded in reality. The "+
            "use of familiar tactile attributes helps users quickly understand affordances. Yet the "+
            "flexibility of the material creates new affordances that supercede those in the physical "+
            "world, without breaking the rules of physics.\n"+
            "The fundamentals of light, surface, and movement are key to conveying how objects move, "+
            "interact, and exist in space and in relation to each other. Realistic lighting shows "+
            "seams, divides space, and indicates moving parts.\n"+

            "Bold, graphic, intentional.\n"+

            "The foundational elements of print based design typography, grids, space, scale, color, "+
            "and use of imagery guide visual treatments. These elements do far more than please the "+
            "eye. They create hierarchy, meaning, and focus. Deliberate color choices, edge to edge "+
            "imagery, large scale typography, and intentional white space create a bold and graphic "+
            "interface that immerse the user in the experience.\n"+
            "An emphasis on user actions makes core functionality immediately apparent and provides "+
            "waypoints for the user.\n"+

            "Motion provides meaning.\n"+

            "Motion respects and reinforces the user as the prime mover. Primary user actions are "+
            "inflection points that initiate motion, transforming the whole design.\n"+
            "All action takes place in a single environment. Objects are presented to the user without "+
            "breaking the continuity of experience even as they transform and reorganize.\n"+
            "Motion is meaningful and appropriate, serving to focus attention and maintain continuity. "+
            "Feedback is subtle yet clear. Transitions are efﬁcient yet coherent.\n"+

            "3D world.\n"+

            "The material environment is a 3D space, which means all objects have x, y, and z "+
            "dimensions. The z-axis is perpendicularly aligned to the plane of the display, with the "+
            "positive z-axis extending towards the viewer. Every sheet of material occupies a single "+
            "position along the z-axis and has a standard 1dp thickness.\n"+
            "On the web, the z-axis is used for layering and not for perspective. The 3D world is "+
            "emulated by manipulating the y-axis.\n"+

            "Light and shadow.\n"+

            "Within the material environment, virtual lights illuminate the scene. Key lights create "+
            "directional shadows, while ambient light creates soft shadows from all angles.\n"+
            "Shadows in the material environment are cast by these two light sources. In Android "+
            "development, shadows occur when light sources are blocked by sheets of material at "+
            "various positions along the z-axis. On the web, shadows are depicted by manipulating the "+
            "y-axis only. The following example shows the card with a height of 6dp.\n"+

            "Resting elevation.\n"+

            "All material objects, regardless of size, have a resting elevation, or default elevation "+
            "that does not change. If an object changes elevation, it should return to its resting "+
            "elevation as soon as possible.\n"+

            "Component elevations.\n"+

            "The resting elevation for a component type is consistent across apps (e.g., FAB elevation "+
            "does not vary from 6dp in one app to 16dp in another app).\n"+
            "Components may have different resting elevations across platforms, depending on the depth "+
            "of the environment (e.g., TV has a greater depth than mobile or desktop).\n"+

            "Responsive elevation and dynamic elevation offsets.\n"+

            "Some component types have responsive elevation, meaning they change elevation in response "+
            "to user input (e.g., normal, focused, and pressed) or system events. These elevation "+
            "changes are consistently implemented using dynamic elevation offsets.\n"+
            "Dynamic elevation offsets are the goal elevation that a component moves towards, relative "+
            "to the component’s resting state. They ensure that elevation changes are consistent "+
            "across actions and component types. For example, all components that lift on press have "+
            "the same elevation change relative to their resting elevation.\n"+
            "Once the input event is completed or cancelled, the component will return to its resting "+
            "elevation.\n"+

            "Avoiding elevation interference.\n"+

            "Components with responsive elevations may encounter other components as they move between "+
            "their resting elevations and dynamic elevation offsets. Because material cannot pass "+
            "through other material, components avoid interfering with one another any number of ways, "+
            "whether on a per component basis or using the entire app layout.\n"+
            "On a component level, components can move or be removed before they cause interference. "+
            "For example, a floating action button (FAB) can disappear or move off screen before a "+
            "user picks up a card, or it can move if a snackbar appears.\n"+
            "On the layout level, design your app layout to minimize opportunities for interference. "+
            "For example, position the FAB to one side of stream of a cards so the FAB won’t interfere "+
            "when a user tries to pick up one of cards.";
}