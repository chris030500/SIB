package ahorrosbienestar.sib.std.reportesJasper.component;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Desencriptar {
	
	 private final StringEncryptor encryptor;

	 @Autowired
	 public Desencriptar(StringEncryptor encryptor) {
	       this.encryptor = encryptor;
	 }

	 public String encrypt(String text) {
	       return encryptor.encrypt(text);
	 }

	 public String decrypt(String encryptedText) {
	     return encryptor.decrypt(encryptedText);
	 }
}
