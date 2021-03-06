package pl.pw.mini.Schoolify.modules;

import javax.persistence.Embeddable;

@Embeddable
public class Contact {

	public String website;
	public String phoneNumber;
	public String email;

	public Contact() {
	}

	public Contact(String website, String phoneNumber, String email) {
		this.website = website;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return "Contact [website=" + website + ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
