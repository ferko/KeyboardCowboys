package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PayLevel implements Serializable{
	
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
	@GeneratedValue
	@Id
	private String plLevel;
	private double plRate;
	public String getPlLevel() {
		return plLevel;
	}
	public void setPlLevel(String plLevel) {
		this.plLevel = plLevel;
	}
	public double getPlRate() {
		return plRate;
	}
	public void setPlRate(double plRate) {
		this.plRate = plRate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
