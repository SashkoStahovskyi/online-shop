package com.stahovskyi.onlineshop;

import com.stahovskyi.onlineshop.dao.jdbc.JdbcConnectionFactory;
import com.stahovskyi.onlineshop.dao.jdbc.JdbcProductDao;
import com.stahovskyi.onlineshop.dao.jdbc.JdbcUserDao;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.service.UserService;
import com.stahovskyi.onlineshop.web.servlet.filter.AuthorizationFilter;
import com.stahovskyi.onlineshop.web.servlet.filter.SecurityFilter;
import com.stahovskyi.onlineshop.web.servlet.AddServlet;
import com.stahovskyi.onlineshop.web.servlet.CartServlet;
import com.stahovskyi.onlineshop.web.servlet.DeleteCartServlet;
import com.stahovskyi.onlineshop.web.servlet.DeleteServlet;
import com.stahovskyi.onlineshop.web.servlet.EditeServlet;
import com.stahovskyi.onlineshop.web.servlet.LoginServlet;
import com.stahovskyi.onlineshop.web.servlet.LogoutServlet;
import com.stahovskyi.onlineshop.web.servlet.RegisterServlet;
import com.stahovskyi.onlineshop.web.servlet.SearchServlet;
import com.stahovskyi.onlineshop.web.servlet.ViewAllServlet;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.EnumSet;
import java.util.Properties;

import static com.stahovskyi.onlineshop.configuration.PropertiesReader.getLocalProperties;

public class starter {

    public static void main(String[] args) throws Exception {

        //-------------- read properties ----------
        Properties configProperties = getLocalProperties();

        // ------------ configure connection with DB -----
        JdbcConnectionFactory connectionFactory = new JdbcConnectionFactory(configProperties);

        // ---------------  dao  ------------------
        JdbcProductDao jdbcProductDao = new JdbcProductDao(connectionFactory);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(connectionFactory);

        // ---------------- service  ---------------
        ProductService productService = new ProductService(jdbcProductDao);
        UserService userService = new UserService(jdbcUserDao);
        SecurityService securityService = new SecurityService(productService, userService);

        // ---------------- servlet ----------------
        ViewAllServlet viewAllProductsServlet = new ViewAllServlet(productService);
        AddServlet addProductServlet = new AddServlet(productService);
        DeleteServlet deleteServlet = new DeleteServlet(productService);
        EditeServlet updateServlet = new EditeServlet(productService);
        SearchServlet searchServlet = new SearchServlet(productService);
        LoginServlet loginServlet = new LoginServlet(securityService);
        RegisterServlet registerServlet = new RegisterServlet(securityService);
        LogoutServlet logoutServlet = new LogoutServlet(securityService);
        CartServlet cartServlet = new CartServlet(securityService);
        DeleteCartServlet deleteCartServlet = new DeleteCartServlet(securityService);

        //---------------- filter --------------------
        SecurityFilter securityFilter = new SecurityFilter(securityService);
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();

        //--------------- web server config -----------
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(viewAllProductsServlet), "/products");
        context.addServlet(new ServletHolder(addProductServlet), "/products/add");
        context.addServlet(new ServletHolder(searchServlet), "/products/search");
        context.addServlet(new ServletHolder(deleteServlet), "/products/delete");
        context.addServlet(new ServletHolder(updateServlet), "/products/edit");
        context.addServlet(new ServletHolder(cartServlet), "/products/cart");
        context.addServlet(new ServletHolder(deleteCartServlet), "/cart/delete");
        context.addServlet(new ServletHolder(registerServlet), "/registration");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(logoutServlet), "/logout");
        context.addFilter(new FilterHolder(securityFilter), "/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(authorizationFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(3002);
        server.setHandler(context);
        server.start();
    }
}
