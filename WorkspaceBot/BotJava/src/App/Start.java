package App;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import CallMatches.Commands;
import Schedulers.Scheduler;

public class Start {

	public static void main(String[] args) {

		ApiContextInitializer.init();

		// Instantiate Telegram Bots API
		TelegramBotsApi botsApi = new TelegramBotsApi();
		System.out.println("Application V1 has been started successfully!");

		// Register our bot
		try {
			Commands command = new Commands();
			botsApi.registerBot(command);

			Scheduler demo = new Scheduler(command);

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}
}
