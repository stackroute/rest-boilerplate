package com.stackroute.soulmateservice;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoulmateServiceApplication {

	@Value("${spring.rabbitmq.host}")
	String host;

	@Value("${spring.rabbitmq.username}")
	String username;

	@Value("${spring.rabbitmq.password}")
	String password;

	public static void main(String[] args) {
		SpringApplication.run(SoulmateServiceApplication.class, args);
	}

	@Bean
	CachingConnectionFactory cachingConnectionFactory(){
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
		cachingConnectionFactory.setUsername(username);
		cachingConnectionFactory.setPassword(password);
		return cachingConnectionFactory;
	}

	@Bean
	public MessageConverter jsonMessageConvertor(){
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConvertor());
		return rabbitTemplate;
	}
}
