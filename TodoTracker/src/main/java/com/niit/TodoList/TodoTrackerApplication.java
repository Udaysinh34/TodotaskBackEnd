package com.niit.TodoList;

import com.niit.TodoList.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication

public class TodoTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoTrackerApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean jwtFilter(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new JwtFilter());
		filterRegistrationBean.addUrlPatterns(
				"/todo-app/add-task","/todo-app/get-all-task","/todo-app/get-task","/todo-app/get-user","/todo-app/update-profile-image",
				"/todo-app/update","/todo-app/delete-task/*",
				"/todo-app/add-archived-task","/todo-app/get-archived-task","/todo-app/get-all-archived",
				"/todo-app/delete-archived-task/*","/todo-app/update-archived-task","/todo-app/Unarchived",
				"/todo-app/add-completed-task","/todo-app/get-completed-task","/todo-app/get-all-completed",
				"/todo-app/delete-completed-task/*","/todo-app/update-completed-task");
		return filterRegistrationBean;
	}

//	@Bean
//	public FilterRegistrationBean filterRegistrationBean(){
//		final CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("http://localhost:4200");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", config);
//
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//		return bean;
//	}

}
