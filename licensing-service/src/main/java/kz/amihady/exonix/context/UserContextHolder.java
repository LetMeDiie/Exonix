package kz.amihady.exonix.context;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;


@Slf4j
public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();

    public static UserContext getUserContext(){
        var context =
                userContext.get();

        if(context == null) {
            context = createEmptyContext();
            setContext(context);
        }

        return userContext.get();
    }

    public static void setContext(UserContext context) {
        Assert.notNull(context,"Only non-null UserContext instances are permitted");
        userContext.set(context);
    }

    public static  UserContext createEmptyContext(){
        log.info("Creating new user context");
        return new UserContext();
    }

}
