package consulting.baxter.holidaybooking;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import consulting.baxter.holidaybooking.jackson.MoneyDeserializer;
import consulting.baxter.holidaybooking.jackson.MoneySerializer;
import org.joda.money.Money;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HolidayBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolidayBookingApplication.class, args);
    }

    @Bean
    Module setupMoneySerialisation() {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(new MoneySerializer());
        simpleModule.addDeserializer(Money.class, new MoneyDeserializer());
        return simpleModule;
    }

//    @Configuration
//    static class OktaOAuth2WebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//
//            http.authorizeRequests()
//                .antMatchers("/", "/index.html", "/sign-in-widget-config").permitAll()
//                .anyRequest().authenticated();
//
//            http.oauth2ResourceServer().jwt();
//        }
//    }

//    @Configuration
//    static class SecurityConfiguration {
//        @Bean
//        public UserDetailsService userDetailsService() {
//            return new InMemoryUserDetailsManager(new User("admin", "{noop}password", Collections.singleton(new SimpleGrantedAuthority("ADMIN"))));
//        }
//    }
}
