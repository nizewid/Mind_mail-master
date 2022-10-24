package es.mindfm.template.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
@ToString
@Getter
@Setter
public class EventInfo {

    private String owner;
    private String name;
    private String to;
    private Date date;
    private String initTime;
    private String endTime;
    private String address;
    private String urlEvent;
    private String eventId;

    public String getDateSpa() throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy", new Locale("es", "ES"));
        Date dateFromJson = this.date;
        String spanishDate = format.format(dateFromJson);
        return spanishDate;


    }
}

