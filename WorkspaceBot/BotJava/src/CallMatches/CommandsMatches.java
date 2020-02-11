package CallMatches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ConnectionDB.Queries;

public class CommandsMatches {
	public static Long mio = (long) 145645559;

	/**
	 * Cattura tutti i messaggi che non hanno mappato un CASE nella classe SianConnet
	 * 
	 * @param sc
	 * @param update
	 */
	public static void AllOtherMessages(Commands sc, Update update) {
		try {
			sc.execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText(
					"Clicka /Start per monitorare un sito"));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo che registra uno nuovo user, nel caso quest'ultimo esistesse già, viene mandato un messaggio di notifica
	 * all'utente
	 * 
	 * @param sc
	 * @param update
	 */
	public static void UserRegistration(Commands sc, Update update) {
		if (Queries.userIdExsist(update.getMessage().getChatId())) {
			try {
				sc.execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText(
						"Utente già esistente"));
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} else {

			try {
				Long chat_id = update.getMessage().getChatId();
				sc.execute(new SendMessage().setChatId(chat_id).setText("Utente registrato correttamente!"));
				createInlineKeyboardLinks(sc, update);

			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Mostra la lista dei siti da controllare
	 * 
	 * @param sc
	 * @param update
	 */
	public static void createInlineKeyboardLinks(Commands sc, Update update) {
		Long chat_id = update.getMessage().getChatId();
		List<String> links = Arrays.asList("Ciao", "Uno", "Due", "Ciao tre dfvs");
		if (!links.isEmpty()) {
			InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
			List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
			SendMessage message2 = new SendMessage().setChatId(chat_id).setText(
					"Adesso puoi scegliere i siti da controllare");
			for (String link : links) {
				List<InlineKeyboardButton> rowInline = new ArrayList<>();
				rowInline.add(new InlineKeyboardButton().setText(link));
				rowsInline.add(rowInline);

			}

			markupInline.setKeyboard(rowsInline);
			message2.setReplyMarkup(markupInline);
			try {
				sc.execute(message2);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} else {
			try {
				sc.execute(new SendMessage().setChatId(chat_id).setText("Non esistono più siti da mostrare!"));
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Metodo che notifica solo l'amministratore che un sito è stato modificato con errore 404
	 * 
	 * @param sc
	 * @param descriptionLink
	 */
	public static void createInlineKeyboardForUserSendingMessage(Commands sc, String descriptionLink) {
		Long chat_id = mio;
		List<String> links = Arrays.asList("Ciao", "Uno", "Due", "Ciao tre dfvs");

		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		SendMessage message2 = new SendMessage().setChatId(chat_id).setText("***Controlla il sito: '" + descriptionLink
				+ "'  per aggiornamenti poi decidi se mandare notifiche***");

		List<InlineKeyboardButton> rowInline = new ArrayList<>();
		rowInline.add(new InlineKeyboardButton().setText(links.get(0)));
		rowsInline.add(rowInline);

		markupInline.setKeyboard(rowsInline);
		message2.setReplyMarkup(markupInline);
		try {
			sc.execute(message2);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo che notifica che sei abilitato correttamente per il link scelto
	 * 
	 * @param sc
	 * @param update
	 * @param idLink
	 */
	public static void createAbilitazione(Commands sc, Update update, Long idLink) {
		update.getCallbackQuery().getMessage().getChatId();
		long message_id = update.getCallbackQuery().getMessage().getMessageId();
		long chat_id = update.getCallbackQuery().getMessage().getChatId();

	}

}
