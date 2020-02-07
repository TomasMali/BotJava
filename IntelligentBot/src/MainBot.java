import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

 class BotStarter extends TelegramLongPollingBot {


	    @Override
	    public String getBotUsername() {
	        // TODO
	        return "Tomas Home";
	    }

	    @Override
	    public String getBotToken() {
	        // TODO
	        return "1048183703:AAGeeJA0IHd5bEF0k8k5Qq6kgKFtOhAOBmo";
	    }

	    @Override
	    public void onUpdateReceived(Update update) {
	    	if (update.hasMessage() && update.getMessage().hasText()) {
	    		// Set variables
	    		String message_text = update.getMessage().getText();
	    		long chat_id = update.getMessage().getChatId();
	    		
	    		SendMessage message = new SendMessage() // Create a message object object
	    				.setChatId(chat_id)
	    				.setText("Hello World " + message_text);
	    		try {
	    			execute(message); // Sending our message object to user
	    		} catch (TelegramApiException e) {
	    			e.printStackTrace();
	    		}
	    	}
	    }

}







public class MainBot {

	public static void main(String[] args) {
		System.out.println("Ciao");
		
		  // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new BotStarter());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		
	}

}