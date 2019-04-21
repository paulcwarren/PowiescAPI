package pl.powiescdosukcesu.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;

@EnableAuthorizationServer
@PropertySource("classpath:application.properties")
@Configuration
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Value("${oauth.client.id}")
    private String CLIENT_ID;

    @Value("${oauth.client.secret}")
    private String SECRET;

    private final int ACCESS_TOKEN_VALIDITY = 60*60;

    private final int REFRESH_TOKEN_VALIDITY = 24*60*60;

    private final AuthenticationManager authenticationManager;

    private final DataSource dataSource;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServer(AuthenticationManager authenticationManager,
                               DataSource dataSource,
                               AuthenticationEntryPoint authenticationEntryPoint,
                               PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {

        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient(CLIENT_ID)
                .secret(passwordEncoder.encode(SECRET))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY)
                .scopes("read", "write");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
    }


    //TODO change later to jdbc
    @Bean
    public TokenStore tokenStore() {

        return new InMemoryTokenStore();
    }
}
