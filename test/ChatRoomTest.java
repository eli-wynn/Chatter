import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class ChatRoomTest {

    @Test
    public void testBroadcastSendsToOtherClients() {
        ChatRoom room = new ChatRoom("test");

        ClientHandler sender = mock(ClientHandler.class);
        ClientHandler receiver = mock(ClientHandler.class);

        room.addClient(sender);
        room.addClient(receiver);

        room.broadcast("hello", sender);

        verify(receiver).sendMessage("hello");
        verify(sender, never()).sendMessage("hello");
    }
}