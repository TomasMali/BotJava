package ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.Abilitazione;
import DAO.Links;
import DAO.User;
import DAO.UserAdmin;

public class Queries {

	/**
	 * ritorna una lista di tutti gli utenti registrati
	 * 
	 * @return
	 */
	public synchronized static List<User> getUsers() {
		List<User> users = new ArrayList<>();
		try (final Connection c = PostgreSQLJDBC.getConnectionDb();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");) {

			while (rs.next()) {
				users.add(new User(rs.getLong("userid"), rs.getString("nome"), rs.getString("cognome"), rs.getString(
						"inserimento")));
			}
			// rs.close();
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return users;
	}

	/**
	 * Dato userId controlla se l'utente è stato registrato
	 * 
	 * @param userId
	 * @return
	 */
	public synchronized static boolean userIdExsist(Long userId) {
		boolean trovato = false;
		try (final Connection c = PostgreSQLJDBC.getConnectionDb();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM public.users where users.userid= " + userId);) {
			if (rs.next())
				trovato = true;
			// rs.close();
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return trovato;
	}

	/**
	 * ritorna una lista di Abilitazione che contengono tutti gli utenti abilitati che fanno parte nel progetto x con
	 * descrizione y
	 * 
	 * @param progetto
	 * @param descrizione
	 * @return
	 */
	public synchronized static List<Abilitazione> GetUserIdWithProjectAndDescription(String progetto,
			String descrizione) {
		List<Abilitazione> usersId = new ArrayList<>();
		try (final Connection c = PostgreSQLJDBC.getConnectionDb();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(
						" select abilitazione.userid,abilitazione.link,abilitazione.started  from abilitazione join links on abilitazione.link=links.id\n"
								+ "and links.descrizione= '" + descrizione + "' and links.progetto= '" + progetto
								+ "' ");) {

			while (rs.next()) {
				usersId.add(new Abilitazione(rs.getLong(1), rs.getLong(2), rs.getBoolean(3)));
			}
			// rs.close();
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Inseration done successfully");

		return usersId;
	}

	/**
	 * Inserisce uno nuovo user
	 * 
	 * @param user
	 */
	public synchronized static void InsertUser(User user) {
		String sql = "INSERT INTO public.users(userid, nome, cognome, inserimento) VALUES( " + user.getUserID() + ", '"
				+ user.getNome() + "', '" + user.getCognome() + "' ,'" + user.getInserimento() + "')";
		try (final Connection c = PostgreSQLJDBC.getConnectionDb(); Statement stmt = c.createStatement();) {
			stmt.executeUpdate(sql);
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
	}

	/**
	 * Inserisce un abilitazione
	 * 
	 * @param abilitazione
	 */
	public synchronized static void InsertAbilitazione(Abilitazione abilitazione) {
		final Connection c = PostgreSQLJDBC.getConnectionDb();

		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO public.abilitazione\n" + "(userid, link)\n" + "VALUES( " + abilitazione
					.getUserid() + ", " + abilitazione.getLink() + ")";
			stmt.executeUpdate(sql);
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Record Abilitazione inserted successfully");
	}

	/**
	 * Controlla se il link dato è stato inserito (esiste già)
	 * 
	 * @param abilitazione
	 * @return
	 */
	public synchronized static boolean CheckIfExsistLink(Abilitazione abilitazione) {
		boolean exists = false;
		try (final Connection c = PostgreSQLJDBC.getConnectionDb();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(" SELECT userid\n" + "FROM public.abilitazione\n" + "WHERE userid= "
						+ abilitazione.getUserid() + " AND link= " + abilitazione.getLink());) {

			if (rs.next())
				exists = true;
			// rs.close();
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return exists;
	}

	/**
	 * Ritorna tutti i links disponibili
	 * 
	 * @return
	 */
	public synchronized static List<Links> getAllLinks() {
		List<Links> links = new ArrayList<>();
		try (final Connection c = PostgreSQLJDBC.getConnectionDb();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT id, descrizione, link, ultimamodifica, progetto FROM public.links");) {

			while (rs.next())
				links.add(new Links(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			// rs.close();
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return links;
	}

	public synchronized static List<Links> getAllMyAvalableLinks(Long idTelegram) {

		List<Links> links = new ArrayList<>();

		try (final Connection c = PostgreSQLJDBC.getConnectionDb();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(
						"select * from links where links.id NOT IN (SELECT links.id from links  join public.abilitazione on links.id = abilitazione.link and abilitazione.userid ="
								+ idTelegram + ")");) {
			while (rs.next())
				links.add(new Links(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			// rs.close();
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return links;

	}

	/**
	 * Ritorna un singolo Link data la sua descrizine
	 * 
	 * @param descrizione
	 * @return
	 */
	public synchronized static Links getSingleLink(String descrizione) {

		try (final Connection c = PostgreSQLJDBC.getConnectionDb();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT *\n" + "		FROM public.links\n"
						+ "		where descrizione = '" + descrizione + "'");) {

			while (rs.next()) {
				return new Links(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
			// rs.close();
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("SELECT SingleLink done successfully");
		return null;
	}

	/**
	 * Aggiorna il timestamp del link in questione
	 * 
	 * @param link
	 */
	public synchronized static void updateLinkTimestamp(Links link) {
		final Connection c = PostgreSQLJDBC.getConnectionDb();
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "UPDATE public.links\n" + "SET descrizione= '" + link.getDescrizione() + "' , link= '" + link
					.getLink() + "' , ultimamodifica= '" + link.getUltimamodifica() + "', progetto= '" + link
							.getProgetto() + "'" + "  WHERE id=" + link.getId();
			stmt.executeUpdate(sql);
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Update Timestam Link done successfully");
	}

	/**
	 * Ritorna il link data la descrizione
	 * 
	 * @param descrizioneLink
	 * @return
	 */
	public synchronized static Long getLinkId(String descrizioneLink) {
		Long res = null;
		try (final Connection c = PostgreSQLJDBC.getConnectionDb();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT id FROM public.links where links.descrizione= '"
						+ descrizioneLink + "'");) {

			while (rs.next())
				res = rs.getLong(1);
			// rs.close();
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return res;
	}

	/**
	 * Cancella la tabela UserAdmin
	 * 
	 * @param userAdmin
	 */
	public synchronized static void deleteTableUserAdmin() {
		final Connection c = PostgreSQLJDBC.getConnectionDb();
		Statement stmt = null;
		try {

			stmt = c.createStatement();
			String sql = "DELETE FROM public.adminuser";
			stmt.executeUpdate(sql);
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Deleted table useradmin done successfully");
	}

	/**
	 * Registra l'utente admin nella tabella userAdmin per tutti i Link nella lista
	 * 
	 * @param adminId
	 */
	public synchronized static void registerAdmin(Long adminId) {

		StringBuilder sql = new StringBuilder();
		final Connection c = PostgreSQLJDBC.getConnectionDb();
		List<Links> links = getAllLinks();
		Statement stmt = null;

		try {
			stmt = c.createStatement();
			sql.append("INSERT INTO public.adminuser (idtelegram, link) VALUES ");

			for (Links link : links) {
				sql.append("(");
				sql.append(adminId + ", '" + link.getLink() + "'");
				sql.append("),");
			}
			sql.setLength(sql.length() - 1);

			if (!links.isEmpty())
				stmt.executeUpdate(sql.toString());
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		if (!links.isEmpty())
			System.out.println("Ures Admin registred successfully");
		else
			System.out.println(
					"Nessun utente è stato aggiunto poiche non esistono link da associargli oppure il link esisteva già");
	}

	/**
	 * Ritorna un singolo UserAdmin dato il link come stringa
	 * 
	 * @param id
	 * @param linkId
	 * @return
	 */
	public synchronized static UserAdmin getUserAdminWithLink(String link) {

		try (final Connection c = PostgreSQLJDBC.getConnectionDb();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(" SELECT *\n" + "		FROM public.adminuser where  link= '" + link
						+ "'");) {
			while (rs.next()) {
				return new UserAdmin(rs.getLong(1), rs.getString(2), rs.getBoolean(3));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("SELECT UserAdmin done successfully");
		return null;
	}

	/**
	 * Mette il campo isFirstTime a true
	 * 
	 * @param userAdmin
	 */
	public synchronized static void setFirstTimeUserAdminToTrue(UserAdmin userAdmin) {
		String sql = "UPDATE public.adminuser\n" + "SET firsttime=true\n" + "WHERE idtelegram= " + userAdmin.getUserID()
				+ " AND link= '" + userAdmin.getLink() + "'";

		try (final Connection c = PostgreSQLJDBC.getConnectionDb(); Statement stmt = c.createStatement();) {
			stmt.executeUpdate(sql);
			// stmt.close();
			// c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Update firstTime done successfully");
	}

}
