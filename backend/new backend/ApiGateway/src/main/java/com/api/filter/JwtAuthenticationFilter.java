package com.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import com.api.util.Jwtutil;

import org.springframework.http.HttpMethod;

@Component
public class JwtAuthenticationFilter extends  AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config>  {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Autowired
    private Jwtutil jwtUtil;

    @Autowired
    private RouteValidator validator;
    
   
        public JwtAuthenticationFilter() {
            super(Config.class);
        }

        /**
         * apply method overrides the apply method of AbstractGatewayFilterFactory.
         * It returns a GatewayFilter that performs the authentication and authorization logic.
         * 
         * @param config Configuration object for the filter (not used in this implementation)
         * @return GatewayFilter for handling authentication and authorization
         */
        @Override
        public GatewayFilter apply(Config config) {
        	   return ((exchange, chain) -> {
                   if (validator.isSecured.test(exchange.getRequest())) {
                       if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                           logger.error("Missing Authorization header");
                           throw new RuntimeException("Missing Authorization header");
                       }

                       String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                       HttpMethod method = exchange.getRequest().getMethod();
                      
                       if (authHeader != null && authHeader.startsWith("Bearer ")) {
                           authHeader = authHeader.substring(7);
                       }

                       try {
                           jwtUtil.validateToken(authHeader);
                           String role = jwtUtil.extractRole(authHeader);
                           String path = exchange.getRequest().getURI().getPath();

                           if (!checkRoleAccess(role, path,method)) {
                               logger.error("Unauthorized access: Role '{}' does not have access to path '{}'", role, path);
                               throw new RuntimeException("Unauthorized access");
                           }
                       } catch (Exception e) {
                           logger.error("Invalid access: {}", e.getMessage());
                           throw new RuntimeException("Unauthorized access to the application");
                       }
                   }
                   System.out.println("Authorized");
                   return chain.filter(exchange);
               });
        }
        
        /**
         * checkRoleAccess method checks if a role has access to a specific path based on predefined rules.
         * 
         * @param role Role extracted from the JWT token
         * @param path Requested path from the URI
         * @return true if the role has access to the path, false otherwise
         */
        private boolean checkRoleAccess(String role, String path,HttpMethod method) {
        	 if (role.equalsIgnoreCase("ADMIN")) {
//        		 return path.startsWith("/profiles") || 
//                 		path.startsWith("/products/**")||
//                 		path.startsWith("/carts") ||
//                 		path.startsWith("/payment") ||
//                  		path.startsWith("/orders");
                 return true; // Admin can access everything
             } else if (role.equalsIgnoreCase("USER")) {
                 
                 return path.startsWith("/profiles/register") || path.startsWith("/profiles/login")|| path.startsWith("/profiles/validate")||
                		path.startsWith("/products/all")||path.startsWith("/products/name") ||path.startsWith("/products/categories")|| path.startsWith("/products/category")||path.startsWith("/products/type")||
                		path.startsWith("/products/getProduct")||
                		path.startsWith("/carts/addtocart") || path.startsWith("/carts/total")||path.startsWith("/carts/update")||path.startsWith("/carts/getCartById")||path.startsWith("/carts/delete")||
                		path.startsWith("/payment") ||
                 		path.startsWith("/orders/placeOrder")||path.startsWith("orders/user")||path.startsWith("/orders");
                     
             } 
             return false; // Default: deny access
        }

        /**
         * Config is a static inner class for configuring the AuthenticationFilter.
         * It is currently empty as there are no specific configurations needed for this filter.
         */
        public static class Config {

        }
 }