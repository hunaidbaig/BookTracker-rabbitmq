package afn.xloop.booktracker_cnsd23;

import java.util.Collection;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.AMQP.Queue;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository repo;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public BookController(BookRepository repo){
        this.repo = repo;
    }


    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void sendMessage(){
        String msg = "This message is from BookSender";
        this.rabbitTemplate.convertAndSend(msg);
        System.out.println("Sent: '" + msg + "'");
    }

    //localhost:8080/books
    @PostMapping("")
    public void createBook(@RequestBody Book book){
        this.sendMessage();
        this.repo.save(book); 
    }

    @GetMapping("/all")
    public Collection<Book> getAllBooks(){
        return this.repo.getAllBook();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable int id){
        this.repo.deleteBook(id);
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable Integer id, Book book){
        this.repo.updateBook(id, book);
        return book;
    }
}
