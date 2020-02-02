package Main;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import javax.security.auth.login.LoginException;

public class Main {
    public static final String BOT_NAME = "SteffenBot";

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = new Config().token;
        builder.setToken(token);
        builder.addEventListener(new DefaultEventListener());
        builder.build();
    }
}
