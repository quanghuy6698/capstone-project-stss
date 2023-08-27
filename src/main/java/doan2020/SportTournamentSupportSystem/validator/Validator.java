package doan2020.SportTournamentSupportSystem.validator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Validator {

	public String formatDateToString(Date date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strDate = formatter.format(date);
			return strDate;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Date formatStringToDate(String date) {
		try {
			SimpleDateFormat formatter;
			try{
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} catch (Exception e) {
				formatter = new SimpleDateFormat("yyyy-MM-dd");
			}
			Date dateExpected = formatter.parse(date);
			return dateExpected;
		} catch (Exception e) {
			return null;
		}
	}
	
	public int convertDobToAge(String dobEntity) {
		int res = -1;
		try {
			LocalDate dob = LocalDate.parse(dobEntity);
			LocalDate curDate = LocalDate.now();
			res = Period.between(dob, curDate).getYears();
		} catch (Exception e) {
			return res;
		}
		
		return res;
	}
}
