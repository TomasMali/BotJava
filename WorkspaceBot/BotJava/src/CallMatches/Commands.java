package CallMatches;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ConnectionDB.Queries;

public class Commands extends TelegramLongPollingBot {

	public boolean partito = false;

	@Override
	public String getBotToken() {

		return "676793933:AAFSqroVLFsRsYU1nk12-gmVWrYprDN2q-I";
	}

	public String getBotUsername() {
		return "TomasMali";
		// tomasmalibot
		// 502596920:AAGXj1omTxPldCElns1Wiw965LqslMSKBHw
		// TomasSubitoBot
		// 657809545:AAEA4xHiTKLndDuRJc9G5XYrwt-ul2WFqH0
		// tomasweather
		// 601333146:AAHJ4Fa1wDt5x5Tsm2bB7CQE1qhYAEXxyBM
		// siandocstatus
		// 645382473:AAG1Vtkoky27VLINnIWRvaxQxqig-xsbKa4
		// Test2TomasBot
		// 676793933:AAFSqroVLFsRsYU1nk12-gmVWrYprDN2q-I

	}

	@Override
	public void onUpdateReceived(Update update) {

		if (update.hasMessage())
			hasMessage(update);
		else if (update.hasCallbackQuery())
			HasCallbackQuery(update);

	}

	/**
	 * Controlla tutti i messaggi con testo che arrivano e fa il parsing
	 * 
	 * @param update
	 */
	public void hasMessage(Update update) {

		// Search if the user exists first

		if (update.getMessage().hasText()) {

			switch (update.getMessage().getText()) {

			case "/start":
				try {
					execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText(
							"Tu hai clickato start"));
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				break;
			case "ciao":

				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(update.getMessage().getChatId()).setText("Here is your keyboard");
				// Create ReplyKeyboardMarkup object
				ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
				// Create the keyboard (list of keyboard rows)
				List<KeyboardRow> keyboard = new ArrayList<>();
				// Create a keyboard row
				KeyboardRow row = new KeyboardRow();
				// Set each button, you can also use KeyboardButton objects if you need something else than text
				row.add("\ud83d\udcce Row 1 Button 1");
				row.add("\u231a\ufe0f Row 1 Button 2");
				row.add("Row 1 Button 3");
				// Add the first row to the keyboard
				keyboard.add(row);
				// Create another keyboard row
				row = new KeyboardRow();
				// Set each button for the second line
				row.add("Row 2 Button 1");
				row.add("Row 2 Button 2");
				row.add("Row 2 Button 3");
				// Add the second row to the keyboard
				keyboard.add(row);
				// Set the keyboard to the markup
				keyboardMarkup.setKeyboard(keyboard);
				// Add it to the message
				message.setReplyMarkup(keyboardMarkup);
				try {
					execute(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

				break;
			case "\ud83d\udcce Row 1 Button 1":

				try {

					SendDocument sendDocumentRequest = new SendDocument();
					sendDocumentRequest.setChatId(update.getMessage().getChatId());
					sendDocumentRequest.setNewDocument(new File(
							"/Users/tomas/Downloads/MN-2020-01-13Ore14.00-Analisi-260120-1405-3.pdf"));
					sendDocumentRequest.setCaption("Doc");
					sendDocument(sendDocumentRequest);

				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			default:
				try {
					execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText(
							"Registratiti clickando '/start' prima di usare il bot!"));
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				break;

			}
		}

	}

	public void HasCallbackQuery(Update update) {

		if (update.getCallbackQuery().getData().equals("\\ud83d\\udcce Row 1 Button 1"))
			try {
				execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText("Tu hai clickatpo boh: "));
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}

		// keyboard per abilitarsi nei links
		CommandsMatches.createAbilitazione(this, update, Queries.getLinkId(update.getCallbackQuery().getData()));

	}

}
