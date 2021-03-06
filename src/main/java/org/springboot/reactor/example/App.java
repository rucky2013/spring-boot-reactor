package org.springboot.reactor.example;

import static reactor.event.selector.Selectors.$;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App implements CommandLineRunner {
	@Bean
	Environment env() {
		return new Environment();
	}
	
	@Bean
    Reactor createReactor(Environment env) {
        return Reactors.reactor()
                .env(env)
                .dispatcher(Environment.THREAD_POOL)
                .get();
    }
	
	@Autowired
	Reactor reactor;
	
	@Autowired
	AppListener appListener;
	
	@Autowired
	AppPublisher appPublisher;
	
    public static void main( String[] args ) {
    	SpringApplication.run(App.class, args);
    }

	public void run(String... args) throws Exception {
		reactor.on($("eventHandler"), appListener);
		appPublisher.publishData();
	}
}
