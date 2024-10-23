package pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.book;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.notification.NotificationService;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final NotificationService notificationService;

    public BookService(BookRepository bookRepository, NotificationService notificationService) {
        this.bookRepository = bookRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public BookEntity createBook(BookDto bookDto, String client) {
        BookEntity book = bookRepository.save(new BookEntity(bookDto.title(), bookDto.author()));
        notificationService.saveNotification(client, book.getId());
        return book;
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(e -> new BookDto(e.getTitle(), e.getAuthor()))
                .toList();
    }

    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(e -> new BookDto(e.getTitle(), e.getAuthor()));
    }
}
