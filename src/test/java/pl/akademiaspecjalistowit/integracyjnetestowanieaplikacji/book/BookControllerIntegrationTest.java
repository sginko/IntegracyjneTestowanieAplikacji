package pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.notification.NotificationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll();
        notificationRepository.deleteAll();
    }

    @Test
    public void shouldReturnNotFoundWhenBookDoesNotExist() throws Exception {
        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateBook() throws Exception {
        //given
        BookDto book = new BookDto("Spring Boot", "John Doe");
        String clientName = "Client";

        //when then
        mockMvc.perform(post("/books")
                        .header("client", clientName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    @Order(2000)
    @Test
    public void should_create_book_and_get_book() throws Exception {
        //given
        BookDto book = new BookDto("Spring Boot2", "John Doe");
        String clientName = "Client";

        //when then
        mockMvc.perform(post("/books")
                        .header("client", clientName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());

        List<BookEntity> books = bookRepository.findAll();
        Long bookId = books.get(0).getId();
//        mockMvc.perform(get("/books/1"))
        mockMvc.perform(get("/books/" + + bookId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(book)));
    }

    @Test
    public void should_create_book_and_notification() throws Exception {
        //given
        BookDto book = new BookDto("Spring Boot", "John Doe");
        String clientName = "Client";

        //when
        mockMvc.perform(post("/books")
                        .header("client", clientName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }
}
