package afn.xloop.booktracker_cnsd23;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.AMQP.Queue;

@EnableRabbit
@Configuration
public class RabbitConfig {

    @Bean
    public Queue helloQueue(){
        return new Queue();
    }
}
