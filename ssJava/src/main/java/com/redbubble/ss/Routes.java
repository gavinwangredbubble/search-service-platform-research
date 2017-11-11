package com.redbubble.ss;

import com.redbubble.ss.resource.Products;
import com.redbubble.ss.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/search/v1")
public class Routes {

    public static final Logger logger = LoggerFactory.getLogger(Routes.class);

    public static final String PAGE_PARAM = "page";
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "108";

    @Autowired
    private ProductService productService;

    @RequestMapping(
            value = "/products/{query}",
            method = RequestMethod.GET,
            produces = "application/json")
    public
    @ResponseBody
    Pagination<Products> searchProducts(
            final HttpServletRequest request,
            @PathVariable("query") String query,
            @RequestParam(value = PAGE_PARAM, required = true, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "pageSize", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
    ) {
        String path = request.getRequestURI();
        Products products = productService.search(query, page.intValue(), pageSize.intValue());
        return new Pagination<>(products, path, page.intValue());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public
    @ResponseBody
    ErrorMsg handleDataStoreException(RuntimeException ex) {
        logger.info("Bad Request: " + ex.getStackTrace());
        return new ErrorMsg(ex, "Bad Request.");
    }
}

class ErrorMsg {
    final Exception exception;
    final String errorMessage;

    ErrorMsg(Exception exception, String errorMessage) {
        this.exception = exception;
        this.errorMessage = errorMessage;
    }

    public String getException() {
        return exception.getMessage();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

class Pagination<T> {
    private final T data;
    private final String nextPage;
    private final String previousPage;

    Pagination(T data, String path, int page) {
        this.data = data;
        this.nextPage = path + "/?" + Routes.PAGE_PARAM + "=" + String.valueOf(page + 1);
        this.previousPage = page > 1 ? path + "/?" + Routes.PAGE_PARAM + "=" + String.valueOf(page - 1) : null;
    }

    public T getData() {
        return data;
    }

    public String getNextPage() {
        return nextPage;
    }

    public String getPreviousPage() {
        return previousPage;
    }
}