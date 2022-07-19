package co.fastcampus.vault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.vault.core.VaultKeyValueOperationsSupport.KeyValueBackend;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@RestController
public class VaultApplication  {

	@Value("${spring.cloud.vault.token}")
	private String vaultToken;

	@Autowired
	VaultOperations vaultOperations;

	@RequestMapping("/secrets")
	public String vaultStore() {

		VaultTemplate vaultTemplate = null;
		VaultEndpoint endpoint = null;

		try {

			endpoint = VaultEndpoint.from( new URI("http://vault-internal:8200"));
			vaultTemplate = new VaultTemplate(endpoint, new TokenAuthentication(vaultToken));

		} catch (URISyntaxException e) {

			e.printStackTrace();

		}

		Secrets secrets = new Secrets();
		secrets.username = "vaultid";
		secrets.password = "vaultpw";

		vaultTemplate.write("secret/vaultstore", secrets);
		VaultResponseSupport<Secrets> response = vaultTemplate.read("secret/vaultstore", Secrets.class);
		Secrets data = response.getData();

		return "ID : " + data.username + " , PW : " + data.password;
	}

	public static void main(String[] args) {
		SpringApplication.run(VaultApplication.class, args);
	}

}