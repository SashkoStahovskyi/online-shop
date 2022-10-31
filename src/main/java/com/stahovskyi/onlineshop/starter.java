package com.stahovskyi.onlineshop;

import com.stahovskyi.onlineshop.dao.jdbc.ConnectionFactory;
import com.stahovskyi.onlineshop.dao.jdbc.JdbcProductDao;
import com.stahovskyi.onlineshop.dao.jdbc.JdbcUserDao;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.service.UserService;
import com.stahovskyi.onlineshop.web.security.SecurityFilter;
import com.stahovskyi.onlineshop.web.servlet.*;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.EnumSet;
import java.util.Properties;

public class starter {

    public static void main(String[] args) throws Exception {

        // todo in resources create dir with application properties
        // properties or config
        // PropertiesReader propertiesReader = new PropertiesReader(/*"application properties"*/); // here will be some config for db
        // Properties properties = propertiesReader.getLocalProperties();

       /* PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.getConnection("postgres","0713");*/

        Properties properties = new Properties();
        properties.setProperty("URI", "jdbc:postgresql://localhost:5432/postgres");
        properties.setProperty("USERNAME", "postgres");
        properties.setProperty("PASSWORD", "0713");
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);

        // ---------------  dao  -------------
        JdbcProductDao jdbcProductDao = new JdbcProductDao(connectionFactory);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(connectionFactory);

        // ---------------- service  ---------------
        ProductService productService = new ProductService(jdbcProductDao);
        UserService userService = new UserService(jdbcUserDao);
        SecurityService securityService = new SecurityService();

        // ---------------- servlet --------------
        ViewAllServlet viewAllProductsServlet = new ViewAllServlet(productService);
        AddServlet addProductServlet = new AddServlet(productService);
        DeleteServlet deleteServlet = new DeleteServlet(productService);
        EditeServlet updateServlet = new EditeServlet(productService);
        SearchServlet searchServlet = new SearchServlet(productService);
        LoginServlet loginServlet = new LoginServlet(userService);

        //---------------- filter --------------------
        SecurityFilter securityFilter = new SecurityFilter(securityService);

        //--------------- web server config -----------
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(viewAllProductsServlet), "/products");
        context.addServlet(new ServletHolder(addProductServlet), "/products/add");
        context.addServlet(new ServletHolder(searchServlet), "/products/search");
        context.addServlet(new ServletHolder(deleteServlet), "/products/delete");
        context.addServlet(new ServletHolder(updateServlet), "/products/edit");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addFilter(new FilterHolder(securityFilter), "/*", EnumSet.of(DispatcherType.FORWARD));

        Server server = new Server(3002);
        server.setHandler(context);
        server.start();
    }
}
